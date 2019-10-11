package com.themescaline.moneytransfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.model.Account;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import java.text.MessageFormat;
import java.util.List;

@Slf4j
@Singleton
public class AccountServiceImpl implements AccountService {

    @Inject
    private AccountDAO accountDAO;

    @Override
    public List<Account> getAll() {
        log.info("Retrieving list of all accounts");
        return accountDAO.getAll();
    }

    @Override
    public Account getOne(long accountId) {
        log.info(MessageFormat.format("Processing request of retrieving account with id = {0}", accountId));
        return accountDAO.getOne(accountId);
    }

    @Override
    public Account save(Account account) {
        if (account.getBalance() < 0) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Account balance can't be negative");
        }
        if (!account.isNew()) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Save account must be new (must not have id)");
        }
        log.info(MessageFormat.format("Processing request of saving account: {0}", account));
        return accountDAO.save(account);
    }

    @Override
    public Account update(long accountId, Account account) {
        if (account.getBalance() < 0) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Account balance can't be negative");
        }
        account.setId(accountId);
        log.info(MessageFormat.format("Processing request of updating account id: {0} with data from {1}", accountId, account));
        return accountDAO.update(account);
    }

    @Override
    public void delete(long accountId) {
        log.info(MessageFormat.format("Processing request of deleting account with id = {0}", accountId));
        accountDAO.delete(accountId);
    }

    @Override
    public void clear() {
        log.info("Processing request of clearing storage");
        accountDAO.clear();
    }
}
