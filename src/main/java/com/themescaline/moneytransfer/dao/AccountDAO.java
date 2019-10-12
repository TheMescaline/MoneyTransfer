package com.themescaline.moneytransfer.dao;

import com.themescaline.moneytransfer.model.Account;
import java.util.List;

/**
 * DAO for all operations with storage
 *
 * @author lex.korovin@gmail.com
 */
public interface AccountDAO {
    String ALL_SORTED = "from Account";
    String CLEAR = "DELETE FROM Account";

    List<Account> getAll();

    Account getOne(long accountId);

    Account save(Account account);

    Account update(Account account);

    void delete(long accountId);

    void transfer(long fromAccountId, long toAccountId, double amount);

    void clear();

    void withdraw(long accountId, double amount);

    void deposit(long accountId, double amount);
}
