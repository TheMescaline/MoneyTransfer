package com.themescaline.moneytransfer.dao;

import com.google.inject.Singleton;
import com.themescaline.moneytransfer.config.HibernateSessionFactory;
import com.themescaline.moneytransfer.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.LockModeType;
import java.util.Collections;
import java.util.List;

@Slf4j
@Singleton
public class HibernateAccountDAO implements AccountDAO {
    @Override
    public List<Account> getAll() {
        List<Account> result = Collections.emptyList();
        Transaction transaction = null;

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            result = session.createNamedQuery(Account.ALL_SORTED, Account.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }

    @Override
    public Account getOne(long accountId) {
        Account result = null;
        Transaction transaction = null;

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            result = session.find(Account.class, accountId);
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return result;
    }

    @Override
    public Account save(Account account) {
        if (account.isNew()) {
            Transaction transaction = null;
            try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                session.save(account);
                transaction.commit();
            } catch (Exception e) {
                log.error(e.getMessage());
                if (transaction != null) {
                    transaction.rollback();
                }
                return null;
            }
        } else {
            account = null;
        }
        return account;
    }

    @Override
    public Account update(long accountId, Account account) {
        Account old = null;
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            old = session.find(Account.class, accountId, LockModeType.PESSIMISTIC_WRITE);
            if (old != null) {
                old.setBalance(account.getBalance());
                session.saveOrUpdate(old);
            }
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return old;
    }

    @Override
    public Boolean delete(long accountId) {
        Boolean isDeleted = null;
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Account toDelete = session.find(Account.class, accountId, LockModeType.PESSIMISTIC_WRITE);
            if (toDelete != null) {
                session.delete(toDelete);
                isDeleted = true;
            }
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return isDeleted;
    }

    @Override
    public Boolean doTransfer(long fromAccountId, long toAccountId, double amount) {
        Boolean isSuccess = false;
        Transaction transaction = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Account fromAccount = session.find(Account.class, fromAccountId, LockModeType.PESSIMISTIC_WRITE);
            Account toAccount = session.find(Account.class, toAccountId, LockModeType.PESSIMISTIC_WRITE);
            if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) {
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                session.merge(fromAccount);
                toAccount.setBalance(toAccount.getBalance() + amount);
                session.merge(toAccount);
                isSuccess = true;
            }
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return isSuccess;
    }

    @Override
    public void clear() {
        Transaction transaction = null;

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNamedQuery(Account.CLEAR).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
