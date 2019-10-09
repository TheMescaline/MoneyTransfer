package com.themescaline.moneytransfer.util;

import com.themescaline.moneytransfer.config.HibernateSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Slf4j
public class TransactionHelper {
    public <T> T transactionalExecute(TransactionalExecutor<T> transactionalExecutor) {
        T result = null;
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            result = transactionalExecutor.execute(session);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }
}
