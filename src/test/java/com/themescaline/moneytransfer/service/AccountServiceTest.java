package com.themescaline.moneytransfer.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.themescaline.moneytransfer.TestAccountData;
import com.themescaline.moneytransfer.config.TestAccountModule;
import com.themescaline.moneytransfer.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static com.themescaline.moneytransfer.TestAccountData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountServiceTest {
    private Injector injector = Guice.createInjector(new TestAccountModule());
    private AccountService accountService = injector.getInstance(AccountService.class);

    @BeforeEach
    void setUp() {
        accountService.clear();
        accountService.save(new Account(NEW_FIRST));
        accountService.save(new Account(NEW_SECOND));
    }

    @Test
    void getAll() {
        assertIterableEquals(Arrays.asList(EXISTED_FIRST, EXISTED_SECOND), accountService.getAll());
    }

    @Test
    void getOne() {
        assertEquals(EXISTED_FIRST, accountService.getOne(1L));
    }

    @Test
    void save() {
        accountService.save(new Account(NEW_THIRD));
        assertEquals(EXISTED_THIRD, accountService.getOne(3L));
    }

    @Test
    void update() {
        Account updated = new Account(EXISTED_FIRST.getId(), EXISTED_FIRST.getBalance() + 10000.0);
        assertEquals(updated, accountService.update(updated.getId(), updated));
    }

    @Test
    void delete() {
        assertTrue(accountService.delete(1L));
        assertIterableEquals(Collections.singletonList(EXISTED_SECOND), accountService.getAll());
    }

    @Test
    void clear() {
        accountService.clear();
        assertIterableEquals(Collections.EMPTY_LIST, accountService.getAll());
    }
}