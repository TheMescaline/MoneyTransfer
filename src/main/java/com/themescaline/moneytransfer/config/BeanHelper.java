package com.themescaline.moneytransfer.config;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class BeanHelper {
    private static Injector injector = Guice.createInjector(new AccountModule());

    public static <T> T getBean(Class<T> clazz) {
        return injector.getInstance(clazz);
    }
}
