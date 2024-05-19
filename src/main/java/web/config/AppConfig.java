package web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
public class AppConfig {
    private final Environment env;

    @Autowired
    public AppConfig(Environment env) {
        this.env = env;
    }


    // Метод для создания и настройки DataSource, используемого для доступа к базе данных
    @Bean
    public DataSource getDataSource() {
        Logger logger = LoggerFactory.getLogger(AppConfig.class);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // Получение настроек для настройки DataSource из Environment
        String driverClassName = env.getProperty("db.driver");
        String url = env.getProperty("db.url");
        String username = env.getProperty("db.username");
        String password = env.getProperty("db.password");
        // Проверка наличия всех необходимых настроек
        if (Objects.isNull(driverClassName) ||
                Objects.isNull(url) ||
                Objects.isNull(username) ||
                Objects.isNull(password)) {
            String errorMessage = "Database properties are missing or incomplete.";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        // Установка свойств DataSource
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }


    // Метод для настройки свойств Hibernate, таких как диалект, отображение SQL-запросов и т. д.
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.connection.characterEncoding", "utf8");
        properties.put("hibernate.connection.CharSet", "utf8");
        properties.put("hibernate.connection.useUnicode", true);

        return properties;
    }


    // Метод для создания и настройки фабрики EntityManager, которая управляет EntityManager
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        // Установка DataSource и пакетов для сканирования для EntityManagerFactory
        em.setDataSource(getDataSource());
        em.setPackagesToScan("web");
        // Настройка JPA провайдера и свойств Hibernate
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }


    // Метод для создания и настройки менеджера транзакций JPA, который управляет транзакциями в приложении
    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        // Установка EntityManagerFactory в JpaTransactionManager
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

}


//    @Bean
//    public DataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db.driver")));
//        dataSource.setUrl(env.getProperty("db.url"));
//        dataSource.setUsername(env.getProperty("db.username"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        return dataSource;
//    }