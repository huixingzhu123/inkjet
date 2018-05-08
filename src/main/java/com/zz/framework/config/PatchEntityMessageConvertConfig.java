package com.zz.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * 部分更新消息转换配置类
 *
 * @author yanjunhao
 * @date 2017年11月22日
 */
@Configuration
public class PatchEntityMessageConvertConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PatchEntityMessageConvertConfig.class);

    @Autowired
    @Qualifier(value = "defaultEntityManager")
    private EntityManager entityManager;

    @Bean
    public com.zz.framework.config.PatchEntityMessageConverter customConverter() {
        return new com.zz.framework.config.PatchEntityMessageConverter(entityManager);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.debug("add PatchEntityMessageConverter");
        converters.add(customConverter());
    }
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
