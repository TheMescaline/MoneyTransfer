package com.themescaline.moneytransfer.util;

import com.themescaline.moneytransfer.config.HibernateSessionFactory;
import com.themescaline.moneytransfer.exceptions.AppException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.NotFoundException;

@Slf4j
public class TransactionHelper {
    public static <T> T transactionalExecute(TransactionalExecutor<T> transactionalExecutor) {
        T result = null;
        Transaction transaction = null;
//        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
        try  {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            result = transactionalExecutor.execute(session);
            transaction.commit();
            session.close();
        } catch (NotFoundException | AppException e) {
            log.error(e.getMessage());
            rollbackTransaction(transaction);
            throw e;
        } catch (Exception e) {
            rollbackTransaction(transaction);
            log.error(e.getMessage());
        }
        return result;
    }

    private static void rollbackTransaction(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    ;
}
