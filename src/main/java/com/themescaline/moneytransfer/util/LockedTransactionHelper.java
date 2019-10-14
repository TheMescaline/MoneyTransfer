package com.themescaline.moneytransfer.util;

import com.themescaline.moneytransfer.config.HibernateSessionFactoryHelper;
import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.exceptions.ConcurrentLockException;
import com.themescaline.moneytransfer.util.multithread.AccountLocker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.ws.rs.NotFoundException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static com.themescaline.moneytransfer.dao.HibernateAccountDAO.LOCK_WAITING_SECONDS;

/**
 * Transactional strategy executor
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@AllArgsConstructor
public class LockedTransactionHelper<T> implements Callable<T> {
    private final long accountId;
    private final ConcurrentMap<Long, AccountLocker> currentLockedAccounts;
    private final TransactionalExecutor<T> transactionalExecutor;

    @Override
    public T call() throws InterruptedException {
        currentLockedAccounts.putIfAbsent(accountId, new AccountLocker(accountId));
        if (currentLockedAccounts.get(accountId).getLock().tryLock(LOCK_WAITING_SECONDS, TimeUnit.SECONDS)) {
            try {
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
            } finally {
                currentLockedAccounts.get(accountId).getLock().unlock();
            }
        } else {
            throw new ConcurrentLockException(accountId);
        }
    }

    private void rollbackTransaction(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
