package com.themescaline.moneytransfer.util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.themescaline.moneytransfer.config.AccountModule;
import lombok.experimental.UtilityClass;

/**
 * Bean for finding other beans in context
 *
 * @author lex.korovin@gmail.com
 */
@UtilityClass
public class BeanHelper {
    private Injector injector = Guice.createInjector(new AccountModule());

    public <T> T getBean(Class<T> clazz) {
        return injector.getInstance(clazz);
    }
}
