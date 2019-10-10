package com.themescaline.moneytransfer.dao;

import com.themescaline.moneytransfer.model.Account;

import java.util.List;

public interface AccountDAO {
    List<Account> getAll();

    Account getOne(long accountId);

    Account save(Account account);

    Account update(long accountId, Account account);

    boolean delete(long accountId);

    boolean doTransfer(long fromAccountId, long toAccountId, double amount);

    void clear();
}
