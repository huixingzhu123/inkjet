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
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by zengweiqiang on 2018/4/25.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zz"})
@ServletComponentScan
public class ApplicationStart extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationStart.class);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ApplicationStart.class, args);
    }
}
