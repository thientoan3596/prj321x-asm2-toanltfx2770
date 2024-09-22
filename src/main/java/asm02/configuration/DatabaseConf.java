package asm02.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
public class DatabaseConf {
    @Autowired
    private Environment env;
    @Bean
    public DataSource dataSource()  {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            System.exit(-1);
            throw new RuntimeException(e);
        }
        dataSource.setJdbcUrl(env.getProperty("DB_URL"));
        dataSource.setUser(env.getProperty("DB_USER"));
        dataSource.setPassword(env.getProperty("DB_PASSWORD"));
        dataSource.setInitialPoolSize(1);
        dataSource.setMinPoolSize(1);
        dataSource.setMaxPoolSize(5);
        dataSource.setMaxIdleTime(5000);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("asm02/entity","asm02/security");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        // TODO: Change to false in PROD
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }
}
