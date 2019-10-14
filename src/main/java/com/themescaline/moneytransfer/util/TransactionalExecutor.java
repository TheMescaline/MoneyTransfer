package com.themescaline.moneytransfer.util;

import org.hibernate.Session;

/**
 * Functional interface fo strategy inserting
 *
 * @param <T>
 * @author lex.korovin@gmail.com
 */
public interface TransactionalExecutor<T> {
    T execute(Session session) throws InterruptedException;
}
