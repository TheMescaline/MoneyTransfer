package com.themescaline.moneytransfer.util;

import com.themescaline.moneytransfer.config.HibernateSessionFactoryHelper;
import com.themescaline.moneytransfer.exceptions.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.ws.rs.NotFoundException;
import java.util.concurrent.Callable;

/**
 * Transactional strategy executor
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@AllArgsConstructor
public class TransactionHelper<T> implements Callable<T> {
    private final TransactionalExecutor<T> transactionalExecutor;

    @Override
    public T call() throws InterruptedException {
        T result;
        Transaction transaction = null;
        try {
            Session session = HibernateSessionFactoryHelper.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            result = transactionalExecutor.execute(session);
            transaction.commit();
            session.close();
        } catch (NotFoundException | AppException | InterruptedException e) {
            log.error(e.getMessage());
            rollbackTransaction(transaction);
            throw e;
        }
        return result;
    }

    private void rollbackTransaction(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
