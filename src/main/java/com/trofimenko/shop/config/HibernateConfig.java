package com.trofimenko.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@ComponentScan(value = "com.trofimenko.shop.entities")
@EnableTransactionManagement
public class HibernateConfig {

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/shop");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean(name = "emf")
    public LocalContainerEntityManagerFactoryBean emf() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("com.trofimenko.shop.entities");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        jpaProperties.put("hibernate.enable_lazy_load_no_trans",true);
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.format_sql",true);
        jpaProperties.put("hibernate.connection.charSet","UTF-8");
        emf.setJpaProperties(jpaProperties);
        return emf;
    }

}
