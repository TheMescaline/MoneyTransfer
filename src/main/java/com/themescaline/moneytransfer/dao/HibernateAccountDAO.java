package com.themescaline.moneytransfer.dao;

import com.google.inject.Singleton;
import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.util.TransactionHelper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.LockModeType;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.List;

/**
 * Account DAO implementation
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@Singleton
public class HibernateAccountDAO implements AccountDAO {
    @SuppressWarnings("unchecked")
    @Override
    public List<Account> getAll() {
        return TransactionHelper.transactionalExecute(session -> session.createQuery(ALL_SORTED).list());
    }

    @Override
    public Account getOne(long accountId) {
        final Account account = TransactionHelper.transactionalExecute(session -> session.find(Account.class, accountId));
        if (account == null) {
            throw new NotFoundException(MessageFormat.format("Account with id {0} not found", accountId));
        }
        return account;
    }

    @Override
    public Account save(Account account) {
        TransactionHelper.transactionalExecute(session -> session.save(account));
        return account;
    }

    @Override
    public Account update(Account account) {
        return TransactionHelper.transactionalExecute(session -> {
            Account temp = session.find(Account.class, account.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (temp != null) {
                temp.setBalance(account.getBalance());
                session.update(temp);
                return temp;
            }
            throw new NotFoundException(MessageFormat.format("Can''t update account with id {0} because it doesn''t exists", account.getId()));
        });
    }

    @Override
    public void delete(long accountId) {
        TransactionHelper.transactionalExecute(session -> {
            Account toDelete = session.find(Account.class, accountId, LockModeType.PESSIMISTIC_WRITE);
            if (toDelete != null) {
                session.delete(toDelete);
                return null;
            }
            throw new NotFoundException(MessageFormat.format("Can''t delete account with id {0} because it doesn''t exists", accountId));
        });
    }

    @Override
    public void transfer(long fromAccountId, long toAccountId, double amount) {
        TransactionHelper.transactionalExecute(session -> {
            Account fromAccount = session.find(Account.class, fromAccountId, LockModeType.PESSIMISTIC_WRITE);
            Account toAccount = session.find(Account.class, toAccountId, LockModeType.PESSIMISTIC_WRITE);
            if (fromAccount == null) {
                throw new NotFoundException(MessageFormat.format("Can''t find account with id {0}", fromAccountId));
            }
            if (toAccount == null) {
                throw new NotFoundException(MessageFormat.format("Can''t find account with id {0}", toAccountId));
            }
            if (fromAccount.getBalance() < amount) {
                throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), MessageFormat.format("Can''t get {0} money from account with id {1}", amount, fromAccountId));
            }
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            session.merge(fromAccount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            session.merge(toAccount);
            log.debug("Transaction is done");
            return null;
        });
    }

    @Override
    public void withdraw(long accountId, double amount) {

    }

    @Override
    public void deposit(long accountId, double amount) {

    }

    @Override
    public void clear() {
        TransactionHelper.transactionalExecute(session -> session.createQuery(CLEAR).executeUpdate());
    }
}
