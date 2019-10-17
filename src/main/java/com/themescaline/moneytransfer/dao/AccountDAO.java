package com.themescaline.moneytransfer.dao;

import com.themescaline.moneytransfer.model.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * DAO for all operations with storage
 *
 * @author lex.korovin@gmail.com
 */
public interface AccountDAO {
    /**
     * Query for get all operations
     */
    String ALL_SORTED = "from Account a order by a.id";

    List<Account> getAll();

    Account getOne(long accountId);

    Account save(Account account);

    Account update(Account account);

    void delete(long accountId);

    void transfer(long fromAccountId, long toAccountId, BigDecimal amount);

    void withdraw(long accountId, BigDecimal amount);

    void deposit(long accountId, BigDecimal amount);
}
