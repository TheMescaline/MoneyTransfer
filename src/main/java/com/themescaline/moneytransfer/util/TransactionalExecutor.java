package com.themescaline.moneytransfer.util;

import org.hibernate.Session;

public interface TransactionalExecutor<T> {
    public T execute(Session session);
}
