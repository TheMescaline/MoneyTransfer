package com.themescaline.moneytransfer.config;

import com.themescaline.moneytransfer.exceptions.ExceptionMessage;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.model.ConnectionConfigDTO;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import static com.themescaline.moneytransfer.exceptions.ExceptionMessage.SERVER_CONFIGURATION_ERROR;

/**
 * Hibernate session factory
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@UtilityClass
public class HibernateSessionFactoryHelper {
    private static final String DEFAULT_PROPS = "properties.yml";
    private static final String FALSE = "false";
    private static final String UPDATE = "update";
    private Integer poolSize;

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            //default H2 connection settings, if method init() was not called from Launcher
            init(null);
        }
        return sessionFactory;
    }

    public int getPoolSize() {
        if (poolSize == null) {
            //default H2 connection settings, if method init() was not called from Launcher
            init(null);
        }
        return poolSize;
    }

    public void init(String configFilePath) {
        if (sessionFactory == null) {

            try (InputStream in = configFilePath == null ?
                    HibernateSessionFactoryHelper.class.getClassLoader().getResourceAsStream(DEFAULT_PROPS) :
                    new FileInputStream(configFilePath)) {
                Yaml yaml = new Yaml();
                ConnectionConfigDTO config = yaml.loadAs(in, ConnectionConfigDTO.class);
                Configuration configuration = new Configuration();
                poolSize = config.getPoolSize();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, config.getDriver());
                settings.put(Environment.URL, config.getUrl());
                settings.put(Environment.USER, config.getUser());
                settings.put(Environment.PASS, config.getPassword());
                settings.put(Environment.POOL_SIZE, config.getPoolSize());
                settings.put(Environment.SHOW_SQL, FALSE);
                settings.put(Environment.HBM2DDL_AUTO, UPDATE);

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Account.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(ExceptionMessage.getFormatted(SERVER_CONFIGURATION_ERROR,
                        e.getMessage()), e);
            }
        }
    }
}
