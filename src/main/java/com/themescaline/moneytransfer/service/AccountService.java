package com.themescaline.moneytransfer.service;

import com.google.inject.ImplementedBy;
import com.themescaline.moneytransfer.model.Account;

import java.util.List;

/**
 * Service for Accounts management
 *
 * @author lex.korovin@gmail.com
 */
@ImplementedBy(AccountServiceImpl.class)
public interface AccountService {
    List<Account> getAll();

    Account getOne(long accountId);

    Account save(Account account);

    Account update(long accountId, Account account);

    void delete(long accountId);
}
