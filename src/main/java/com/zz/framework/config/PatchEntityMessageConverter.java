package com.zz.framework.config;

import com.zz.framework.domain.AuditEntity;
import com.zz.framework.domain.BaseEntity;
import com.zz.framework.support.Constants;
import com.zz.framework.util.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 部分更新消息转换
 *
 * @author yanjunhao
 * @date 2017年11月22日
 */
public class PatchEntityMessageConverter extends AbstractHttpMessageConverter<Object> {
    private static final Logger logger = LoggerFactory.getLogger(PatchEntityMessageConverter.class);
    private EntityManager entityManager;

    /**
     * 自定义content-type
     */
    public PatchEntityMessageConverter(EntityManager entityManager) {
        super(new MediaType("application", "sino-patch", Charset.forName(Constants.DEFAULT_CHARTSET)));
        logger.debug("support content-type[application/sino-patch]");
        this.entityManager = entityManager;
    }

    /**
     * 只支持AuditEntity和BaseEntity的子类
     */
    @Override
    protected boolean supports(Class<?> aClass) {
        return aClass.getSuperclass().isAssignableFrom(AuditEntity.class) || aClass.getSuperclass().isAssignableFrom(BaseEntity.class);
    }

    @Override
    protected Object readInternal(Class<? extends Object> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        long st = System.currentTimeMillis();
        //获取body中的json数据
        String jsonStr = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName(Constants.DEFAULT_CHARTSET));
        ObjectMapper objectMapper = JsonUtils.createObjectMapper();
        //参数Json节点
        JsonNode paramJsonNode = objectMapper.readTree(jsonStr);
        //获取主键内容
        String systemid = paramJsonNode.get(Constants.ENTITY_PK).textValue();
        if (null != systemid && !"".equals(systemid)) {
            Object oldEntity = entityManager.find(aClass, systemid);
            //刷新防止缓存
            entityManager.refresh(oldEntity);
            if (null != oldEntity) {
                //旧实体json字符
                String oldEntityJsonStr = JsonUtils.object2Json(oldEntity);
                logger.debug("patchMap--" + jsonStr);
                logger.debug("oldEntity--" + oldEntityJsonStr);

                //旧实体json节点
                ObjectNode oldEntityJsonNode = (ObjectNode) objectMapper.readTree(oldEntityJsonStr);

                //遍历参数节点，把内容替换到旧实体节点中
                paramJsonNode.fields().forEachRemaining(item -> oldEntityJsonNode.replace(item.getKey(), item.getValue()));

                //旧实体内容已更新
                String newEntityJsonStr = objectMapper.writeValueAsString(oldEntityJsonNode);
                logger.debug("newEntity--" + newEntityJsonStr);
                logger.debug("convert time--"+(System.currentTimeMillis()-st)+"ms");
                return JsonUtils.json2Object(newEntityJsonStr, aClass);
            }
            return null;
        }
        return null;
    }

    @Override
    protected void writeInternal(Object responseEntity, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {

        httpOutputMessage.getBody().write(JsonUtils.object2Json(responseEntity).getBytes());
    }

    @Override
    public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
        super.setSupportedMediaTypes(supportedMediaTypes);
    }
}
