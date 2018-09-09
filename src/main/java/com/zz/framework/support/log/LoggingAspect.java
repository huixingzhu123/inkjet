package com.zz.framework.support.log;

import com.zz.dict.util.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zz.framework.domain.AuditEntity;
import com.zz.framework.domain.BaseEntity;
import com.zz.framework.security.SecurityUtil;
import com.zz.framework.support.Constants;
import com.zz.framework.support.log.annotation.Logging;
import com.zz.framework.support.log.entity.ChangeLog;
import com.zz.framework.support.log.entity.LoggingEntity;
import com.zz.framework.support.log.service.LoggingManager;
import com.zz.framework.util.ApplicationContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 日志记录切面
 *
 * @author yanjunhao
 * @date 2017年10月31日
 */
@Aspect
@Component
@PropertySource(value = {"classpath:logging.properties"}, encoding = "utf-8")
public class LoggingAspect {

    private Log logger = LogFactory.getLog(LoggingAspect.class);

    public final static String LOGGING_TYPE_INFO = "0";
    public final static String LOGGING_TYPE_WARN = "1";
    public final static String LOGGING_TYPE_ERROR = "2";

    /**
     * 保存类型
     */
    private final String saveTypeDefault;
    /**
     * 是否记录日志
     */
    private final boolean isLogging;
    /**
     * 匹配正则
     */
    private final String loggingPattern;

    @Value("${log.threadPool.size:10}")
    private int poolSize;
    @Value("${log.maximumPoolSize.size:30}")
    private int maximumPoolSize;
    @Value("${log.keepAliveTime:30}")
    private int keepAliveTime;

    private ThreadPoolExecutor threadPool;

    @Autowired
    public LoggingAspect(
            @Value("${log.saveTypeDefault:loggerManagerDefault}") String saveTypeDefault,
            @Value("${log.isLogging:true}") boolean isLogging,
            @Value("${log.loggingPattern:}") String loggingPattern) {
        this.saveTypeDefault = saveTypeDefault;
        this.isLogging = isLogging;
        this.loggingPattern = loggingPattern;
    }

    @Autowired
    @Qualifier("defaultEntityManager")
    private EntityManager entityManager;


    /**
     * 当bean创建时，构建线程池
     */
    @PostConstruct
    private void init() {
        ThreadFactory threadFactory = new com.zz.framework.support.log.LoggingThreadFactory();
        threadPool = new ThreadPoolExecutor(
                poolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(poolSize),
                threadFactory);
        logger.debug("init()" + threadPool.toString());
    }

    /**
     * 当bean销毁时，回收线程池
     */
    @PreDestroy
    private void destroy() {
        threadPool.shutdown();
        logger.debug("destroy()");
    }

    @Autowired
    private HttpServletRequest httpServletRequest;

    private LoggingManager loggingManager;

    public LoggingManager getLoggingManager() {
        return loggingManager;
    }

    public void setLoggingManager(LoggingManager loggingManager) {
        this.loggingManager = loggingManager;
    }

    /**
     * 初始化日志管理服务类
     *
     * @param beanName 类型
     */
    private void initLoggingManager(String beanName) {
        if (null == beanName || "".equals(beanName)) {
            beanName = saveTypeDefault;
        }
        setLoggingManager((LoggingManager) ApplicationContextUtil.getBean(beanName));
    }

    /**
     * 切面环绕
     *
     * @param joinPoint     JoinPoint
     * @param logAnnotation Logging对象
     * @throws Throwable 异常
     */
    @Around(value = "@annotation(logAnnotation)")
    public Object postHandle(ProceedingJoinPoint joinPoint, Logging logAnnotation) throws Throwable {
        if (!isLogging) {
            logger.debug("isLogging is false");
            return joinPoint.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getDeclaringClass().getName() + "." + methodSignature.getName();

        //正则校验
        if (!"".equals(loggingPattern)) {
            Pattern pattern = Pattern.compile(loggingPattern);
            if (!pattern.matcher(methodName).find()) {
                logger.debug("loggingPattern not match");
                return joinPoint.proceed();
            }
        }
        //参数列表
        List<Object> argsList = Arrays.asList(joinPoint.getArgs());
        logger.debug("params:" + argsList);

        Exception error = null;
        long startTime = 0;
        long endTime = 0;
        String userId = null;
        ChangeLog changeLog = null;
        String remoteAdd = null;
        Object result;

        startTime = System.currentTimeMillis();
        //调用原方法不能放到try catch中，否则异常不会被GlobalExceptionHandler获取
        result = joinPoint.proceed();
        endTime = System.currentTimeMillis();
        try {
            userId = SecurityUtil.getCurrentUserId();
            initLoggingManager(logAnnotation.saveType());

            try {
                Class clazz = logAnnotation.changeObject();
                if (Logging.class != clazz) {
                    //修改操作
                    //获取参数中的entity，只取第一个
                    List newEntityList = argsList.stream().filter(argItem -> argItem.getClass().isAssignableFrom(clazz)).collect(Collectors.toList());
                    if (null != newEntityList && newEntityList.size() != 0) {
                        Object newEntity = newEntityList.get(0);
                        changeLog = checkChange(newEntity, logAnnotation.changeObject());
                    }

                }
            } catch (Exception e) {
                logger.error(e);
                error = e;
            }
            remoteAdd = HttpUtil.getIpAddress(httpServletRequest);
        } catch (Exception e) {
            logger.error(e);
            error = e;
        }
        threadPool.execute(new LogTask(methodName, userId, logAnnotation, endTime, startTime, error, argsList, changeLog, remoteAdd));
        if (null != error) {
            error.printStackTrace();
        }
        return result;
    }

    private class LogTask implements Runnable {
        String methodName;
        String userId;
        String remoteAdd;
        long endTime;
        long startTime;
        Logging logAnnotation;
        Exception error;
        List<Object> params;
        ChangeLog changeLog;

        LogTask(String methodName, String userId,
                Logging logAnnotation,
                long endTime, long startTime, Exception error, List<Object> params, ChangeLog changeLog, String remoteAdd) {
            this.methodName = methodName;
            this.userId = userId;
            this.endTime = endTime;
            this.startTime = startTime;
            this.logAnnotation = logAnnotation;
            this.error = error;
            this.params = params;
            this.changeLog = changeLog;
            this.remoteAdd = remoteAdd;
        }

        @Override
        public void run() {
            LoggingEntity log = prepareLogging(methodName, LOGGING_TYPE_INFO, params,
                    remoteAdd, userId, logAnnotation);
            log.setLasttime(endTime - startTime);
            log.setRequestip(remoteAdd);
            //如果报错则记录错误日志
            if (error != null) {
                log.setType(LOGGING_TYPE_ERROR);
                log.setErrmsg(error.getMessage());
                loggingManager.saveLoggingWithoutChangelog(log);
            }

            loggingManager.saveLoggingWithChangelog(log, changeLog);
        }
    }


    private LoggingEntity prepareLogging(String methodName, String logType,
                                         List<Object> params, String remoteAdd, String userId,
                                         Logging logAnnotation) {
        LoggingEntity log = new LoggingEntity();
        log.setTitle(logAnnotation.title());
        log.setFunctionname(methodName);
        log.setInfo(logAnnotation.info());
        log.setType(logType);
        try {
            String paramsJson;
            //过滤类,filterList包含的类的实例不转为json
            List<Class> filterList = Arrays.asList(BeanPropertyBindingResult.class, HttpServletRequest.class, HttpServletResponse.class);
            logger.debug("参数：" + params);
            List<Object> paramsList = params.stream().filter(param -> !filterList.contains(param.getClass())).collect(Collectors.toList());
            logger.debug("过滤后参数：" + paramsList);
            ObjectMapper objectMapper = new ObjectMapper();
            paramsJson = objectMapper.writeValueAsString(paramsList);
            logger.debug("方法参数转json后的字符串: " + paramsJson);
            log.setParams(paramsJson);
        } catch (Exception e) {
            logger.error(e, e);
        }
        log.setRecordtime(new Date());
        log.setRequestip(remoteAdd);
        log.setAuthuser(userId);
        return log;
    }

    /**
     * 生成修改日志
     *
     * @param newEntity 新的实体
     * @param aClass    实体类名
     * @return 修改日志内容
     */

    private ChangeLog checkChange(Object newEntity, Class<?> aClass) throws Exception {
        //反射获取systemid
        Object oldEntity = null;
        Field field;
        try {
            field = aClass.getDeclaredField(Constants.ENTITY_PK);
        } catch (NoSuchFieldException e) {
            //获取不到主键字段
            if(aClass.getSuperclass() == AuditEntity.class || aClass.getSuperclass() == BaseEntity.class){
                //如果继承审计类或者直接继承基类
                field = BaseEntity.class.getDeclaredField(Constants.ENTITY_PK);
            }else {
                logger.error("["+aClass+"]不是BaseEntity的子类。");
                return null;
            }

        }

        field.setAccessible(true);
        String pk = field.get(newEntity).toString();

        if (!"".equals(pk)) {
            //获取数据库中的oldEntity
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            //from
            Root root = query.from(aClass);
            //systemid
            Path pkName = root.get(Constants.ENTITY_PK);
            //select from class where
            Predicate predicate = pkName.in(pk);
            query.select(root).where(predicate);
            oldEntity = entityManager.createQuery(query).getSingleResult();
        }

        if (null != oldEntity) {
            ObjectMapper objectMapper = new ObjectMapper();
            ChangeLog changelog = new ChangeLog();
            changelog.setEntityname(oldEntity.getClass().getName());
            changelog.setOldentity(objectMapper.writeValueAsString(oldEntity));
            changelog.setNewentity(objectMapper.writeValueAsString(newEntity));
            changelog.setRecordtime(new Date());
            changelog.setRequestip(HttpUtil.getIpAddress(httpServletRequest));
            return changelog;
        }
        return null;

    }
}
