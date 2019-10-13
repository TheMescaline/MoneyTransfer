package com.themescaline.moneytransfer.config;

import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.util.ExceptionMessagesTemplates;
import com.themescaline.moneytransfer.util.YamlConnectionConfig;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * Hibernate session factory
 *
 * @author lex.korovin@gmail.com
 */
@UtilityClass
public class HibernateSessionFactoryHelper {
    private static final String DEFAULT_PROPS = "properties.yml";

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void init(String configFilePath) {
        if (sessionFactory == null) {

            try (InputStream in = configFilePath == null ?
                    HibernateSessionFactoryHelper.class.getClassLoader().getResourceAsStream(DEFAULT_PROPS) :
                    new FileInputStream(configFilePath)) {
                Yaml yaml = new Yaml();
                YamlConnectionConfig config = yaml.loadAs(in, YamlConnectionConfig.class);
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, config.getDriver());
                settings.put(Environment.URL, config.getUrl());
                settings.put(Environment.USER, config.getUser());
                settings.put(Environment.PASS, config.getPassword());
                settings.put(Environment.POOL_SIZE, config.getPoolSize());
                settings.put(Environment.SHOW_SQL, "false");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Account.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new RuntimeException(MessageFormat.format(ExceptionMessagesTemplates.SERVER_CONFIGURATION_ERROR.getMessageTemplate(),
                        e.getMessage()), e);
            }
        }
    }
}
