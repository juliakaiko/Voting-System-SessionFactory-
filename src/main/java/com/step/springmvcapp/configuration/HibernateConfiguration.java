package com.step.springmvcapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.Properties;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {   
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() { // сессии для работы с сущностями
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.step.springmvcapp");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {  // создание соединения с БД
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); //com.mysql.jdbc.Driver
        dataSource.setUrl("jdbc:mysql://localhost:3306/myDB?createDatabaseIfNotExist=true"); //?createDatabaseIfNotExist=true
        dataSource.setUsername("root");
        dataSource.setPassword("Kaiko1994Ulia");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {// Настроить диспетчер транзакций Hibernate / Bean для @Transactional-
        HibernateTransactionManager transactionManager
          = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
          "hibernate.hbm2ddl.auto", "update"); //spring.jpa.hibernate.ddl create update
        hibernateProperties.setProperty(
          "hibernate.dialect ", "org.hibernate.dialect.MySQL5InnoDBDialect"); //!!! org.hibernate.dialect.H2Dialect 

        return hibernateProperties;
    } 
}
