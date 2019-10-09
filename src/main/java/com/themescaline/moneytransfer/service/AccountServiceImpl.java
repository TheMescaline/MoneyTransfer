package com.themescaline.moneytransfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.model.Account;
import lombok.extern.slf4j.Slf4j;

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
        log.info(MessageFormat.format("Retrieving account with id = {0}", accountId));
        return accountDAO.getOne(accountId);
    }

    @Override
    public Account save(Account account) {
        log.info(MessageFormat.format("Saving account: {0}", account));
        return accountDAO.save(account);
    }

    @Override
    public Account update(long accountId, Account account) {
        log.info(MessageFormat.format("Updating account: {0} by id = {1}", account, accountId));
        return accountDAO.update(accountId, account);
    }

    @Override
    public boolean delete(long accountId) {
        log.info(MessageFormat.format("Deleting account with id = {0}", accountId));
        return accountDAO.delete(accountId);
    }

    @Override
    public void clear() {
        log.info("Clearing storage");
        accountDAO.clear();
    }
}
