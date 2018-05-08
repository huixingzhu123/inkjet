/**
 * Copyright(C),2017-2018,广州华资软件技术有限公司
 * FileName: ApplicationStart
 * Author: zengweiqiang
 * Date: 2018/4/25  10:12
 * Description:
 * History:
 * <author>     <time>      <version>       <desc>
 * 作者姓名     修改时间        版本号         描述
 */
package com.zz;

import com.zz.framework.util.ApplicationContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by zengweiqiang on 2018/4/25.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zz"})
@PropertySource(value = {"classpath:${spring.profiles.active}/database.properties", "classpath:${spring.profiles.active}/application.properties"},encoding = "utf-8")
public class ApplicationStart {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ApplicationStart.class, args);
        //设置上下文对象
        ApplicationContextUtil.setApplicationContext(applicationContext);
    }
}
