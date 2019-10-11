package com.themescaline.moneytransfer.config;

import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.util.YamlConnectionConfig;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Hibernate session factory
 *
 * @author lex.korovin@gmail.com
 */
public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void init(String configFilePath) {
        if (sessionFactory == null) {
            try (InputStream in = configFilePath == null ? HibernateSessionFactory.class.getClassLoader().getResourceAsStream("properties.yml") : new FileInputStream(configFilePath)) {
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
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(Account.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
