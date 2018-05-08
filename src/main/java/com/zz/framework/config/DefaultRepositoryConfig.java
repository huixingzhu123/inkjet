package com.zz.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * spring data jpa仓库配置类
 * 使用spring data jpa是默认的数据源
 *
 * @author yanjunhao
 * @date 2017年10月12日
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware",dateTimeProviderRef = "springSecurityAuditorAware")
@EnableJpaRepositories(
        entityManagerFactoryRef = "defaultEntityManagerFactory",
        transactionManagerRef = "defaultTransactionManager",
        repositoryBaseClass = com.zz.framework.repository.DefaultRepositoryImpl.class,
        basePackages = {"com.zz.**.repository"}
)
public class DefaultRepositoryConfig {
    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("defaultDatasource")
    private DataSource defaultDatasource;

    @Bean(name = "defaultEntityManager")
    @Primary
    public EntityManager defaultEntityManager(EntityManagerFactoryBuilder builder) {
        return defaultEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Bean(name = "defaultEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(defaultDatasource)
                .properties(getVendorProperties(defaultDatasource))
                .persistenceUnit("defaultPersistenceUnit")
                .build();
    }

    private Map<String, String> getVendorProperties(DataSource dataSource) {
        return jpaProperties.getHibernateProperties(dataSource);
    }

    @Bean(name = "defaultTransactionManager")
    @Primary
    PlatformTransactionManager defaultTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(defaultEntityManagerFactory(builder).getObject());
    }
}
