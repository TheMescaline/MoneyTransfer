package com.themescaline.moneytransfer.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.themescaline.moneytransfer.config.TestAccountModule;
import com.themescaline.moneytransfer.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountServiceTest {
    Injector injector = Guice.createInjector(new TestAccountModule());
    AccountService accountService = injector.getInstance(AccountService.class);

    @BeforeEach
    void setUp() {
        accountService.clear();
        accountService.save(new Account(10000.0));
        accountService.save(new Account(15000.0));
    }

    @Test
    void getAll() {
        assertIterableEquals(Arrays.asList(new Account(1L, 10000.0), new Account(2L, 15000.0)), accountService.getAll());
    }

    @Test
    void getOne() {
        assertEquals(new Account(1L, 10000.0), accountService.getOne(1l));
    }

    @Test
    void save() {
        accountService.save(new Account(333.0));
        assertEquals(333.0, accountService.getOne(3L).getBalance());
    }

    @Test
    void update() {
        Account updated = new Account(2L, 666.0);
        assertEquals(updated, accountService.update(updated.getId(), updated));
    }

    @Test
    void delete() {
        assertTrue(accountService.delete(1L));
        assertIterableEquals(Collections.singletonList(new Account(2L, 15000.0)), accountService.getAll());
    }

    @Test
    void clear() {
        accountService.clear();
        assertIterableEquals(Collections.EMPTY_LIST, accountService.getAll());
    }
}