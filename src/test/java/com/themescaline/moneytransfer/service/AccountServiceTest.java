package com.themescaline.moneytransfer.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.themescaline.moneytransfer.config.TestAccountModule;
import com.themescaline.moneytransfer.dao.InMemoryAccountDAO;
import com.themescaline.moneytransfer.exceptions.AccountNotFoundException;
import com.themescaline.moneytransfer.exceptions.BalanceException;
import com.themescaline.moneytransfer.exceptions.NotNewAccountException;
import com.themescaline.moneytransfer.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.themescaline.moneytransfer.TestAccountDataHelper.EXCEEDED_TRANSFER_AMOUNT;
import static com.themescaline.moneytransfer.TestAccountDataHelper.EXISTING_FIRST;
import static com.themescaline.moneytransfer.TestAccountDataHelper.EXISTING_SECOND;
import static com.themescaline.moneytransfer.TestAccountDataHelper.EXISTING_THIRD;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NEW_FIRST;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NEW_SECOND;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NEW_THIRD;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NORMAL_TRANSFER_AMOUNT;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NOT_EXISTING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountServiceTest {
    private Injector injector = Guice.createInjector(new TestAccountModule());
    private InMemoryAccountDAO dao = injector.getInstance(InMemoryAccountDAO.class);
    private AccountService accountService = injector.getInstance(AccountService.class);

    @BeforeEach
    void setUp() {
        dao.clear();
        accountService.save(new Account(NEW_FIRST));
        accountService.save(new Account(NEW_SECOND));
    }

    @Test
    void getAll() {
        assertIterableEquals(Arrays.asList(EXISTING_FIRST, EXISTING_SECOND), accountService.getAll());
    }

    @Test
    void getOneOk() {
        assertEquals(EXISTING_FIRST, accountService.getOne(EXISTING_FIRST.getId()));
    }

    @Test
    void getOneNotExisted() {
        assertThrows(AccountNotFoundException.class, () -> accountService.getOne(NOT_EXISTING.getId()));
    }

    @Test
    void saveOk() {
        assertEquals(EXISTING_THIRD, accountService.save(new Account(NEW_THIRD)));
    }

    @Test
    void saveExisting() {
        assertThrows(NotNewAccountException.class, () -> accountService.save(new Account(EXISTING_FIRST)));
    }

    @Test
    void saveIncorrectBalance() {
        assertThrows(BalanceException.class, () -> accountService.save(new Account(EXISTING_THIRD.getId(), EXISTING_THIRD.getBalance().subtract(EXCEEDED_TRANSFER_AMOUNT))));
    }

    @Test
    void updateOk() {
        Account updated = new Account(EXISTING_FIRST.getId(), EXISTING_FIRST.getBalance().add(NORMAL_TRANSFER_AMOUNT));
        assertEquals(updated, accountService.update(updated.getId(), updated));
    }

    @Test
    void updateNotExisted() {
        Account updated = new Account(NOT_EXISTING.getId(), NOT_EXISTING.getBalance().add(NORMAL_TRANSFER_AMOUNT));
        assertThrows(AccountNotFoundException.class, () -> accountService.update(updated.getId(), updated));
    }

    @Test
    void deleteOk() {
        accountService.delete(EXISTING_FIRST.getId());
        assertIterableEquals(Collections.singletonList(EXISTING_SECOND), accountService.getAll());
    }

    @Test
    void deleteNotExisted() {
        assertThrows(AccountNotFoundException.class, () -> accountService.delete(NOT_EXISTING.getId()));
    }

    @Test
    void clear() {
        dao.clear();
        assertIterableEquals(Collections.EMPTY_LIST, accountService.getAll());
    }
}