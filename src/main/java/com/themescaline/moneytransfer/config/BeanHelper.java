package com.themescaline.moneytransfer.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Bean for finding other beans in context
 *
 * @author lex.korovin@gmail.com
 */
public class BeanHelper {
    private static Injector injector = Guice.createInjector(new AccountModule());

    public static <T> T getBean(Class<T> clazz) {
        return injector.getInstance(clazz);
    }
}
