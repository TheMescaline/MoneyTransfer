package com.themescaline.moneytransfer.dao;

import com.google.inject.Singleton;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.util.TransactionHelper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.LockModeType;
import java.util.List;

@Slf4j
@Singleton
public class HibernateAccountDAO implements AccountDAO {
    @Override
    public List<Account> getAll() {
        return TransactionHelper.transactionalExecute(session -> session.createNamedQuery(Account.ALL_SORTED, Account.class).getResultList());
    }

    @Override
    public Account getOne(long accountId) {
        return TransactionHelper.transactionalExecute(session -> session.find(Account.class, accountId));
    }

    @Override
    public Account save(Account account) {
        Account finalAccount = account;
        if (account.isNew() && account.getBalance() >= 0) {
            TransactionHelper.transactionalExecute(session -> session.save(finalAccount));
        } else {
            account = null;
        }
        return account;
    }

    @Override
    public Account update(long accountId, Account account) {
        if (account.getBalance() < 0) {
            return null;
        }
        return TransactionHelper.transactionalExecute(session -> {
            Account temp = session.find(Account.class, accountId, LockModeType.PESSIMISTIC_WRITE);
            if (temp != null) {
                temp.setBalance(account.getBalance());
                session.update(temp);
            }
            return temp;
        });
    }

    @Override
    public boolean delete(long accountId) {
        return TransactionHelper.transactionalExecute(session -> {
            boolean deletingResult = false;
            Account toDelete = session.find(Account.class, accountId, LockModeType.PESSIMISTIC_WRITE);
            if (toDelete != null) {
                session.delete(toDelete);
                deletingResult = true;
            }
            return deletingResult;
        });
    }

    @Override
    public boolean doTransfer(long fromAccountId, long toAccountId, double amount) {
        return TransactionHelper.transactionalExecute(session -> {
            boolean transferResult = false;
            Account fromAccount = session.find(Account.class, fromAccountId, LockModeType.PESSIMISTIC_WRITE);
            Account toAccount = session.find(Account.class, toAccountId, LockModeType.PESSIMISTIC_WRITE);
            if (fromAccount != null && toAccount != null && amount >= 0 && fromAccount.getBalance() >= amount) {
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                session.merge(fromAccount);
                toAccount.setBalance(toAccount.getBalance() + amount);
                session.merge(toAccount);
                transferResult = true;
            }
            return transferResult;
        });
    }

    @Override
    public void clear() {
        TransactionHelper.transactionalExecute(session -> session.createNamedQuery(Account.CLEAR).executeUpdate());
    }
}
