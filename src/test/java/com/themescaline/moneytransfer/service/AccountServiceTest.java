package com.themescaline.moneytransfer.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.themescaline.moneytransfer.config.TestAccountModule;
import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.Collections;

import static com.themescaline.moneytransfer.TestAccountData.*;
import static org.junit.jupiter.api.Assertions.*;

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
        assertIterableEquals(Arrays.asList(EXISTING_FIRST, EXISTING_SECOND), accountService.getAll());
    }

    @Test
    void getOneOk() {
        assertEquals(EXISTING_FIRST, accountService.getOne(EXISTING_FIRST.getId()));
    }

    @Test
    void getOneNotExisted() {
        assertThrows(NotFoundException.class, () -> accountService.getOne(NOT_EXISTING.getId()));
    }

    @Test
    void saveOk() {
        assertEquals(EXISTING_THIRD, accountService.save(new Account(NEW_THIRD)));
    }

    @Test
    void saveExisting() {
        assertThrows(AppException.class, () -> accountService.save(new Account(EXISTING_FIRST)));
    }

    @Test
    void saveIncorrectBalance() {
        assertThrows(AppException.class, () -> accountService.save(new Account(EXISTING_THIRD.getId(), EXISTING_THIRD.getBalance() - EXCEEDED_TRANSFER_AMOUNT)));
    }

    @Test
    void updateOk() {
        Account updated = new Account(EXISTING_FIRST.getId(), EXISTING_FIRST.getBalance() + NORMAL_TRANSFER_AMOUNT);
        assertEquals(updated, accountService.update(updated.getId(), updated));
    }

    @Test
    void updateNotExisted() {
        Account updated = new Account(NOT_EXISTING.getId(), NOT_EXISTING.getBalance() + NORMAL_TRANSFER_AMOUNT);
        assertThrows(NotFoundException.class, () -> accountService.update(updated.getId(), updated));
    }

    @Test
    void deleteOk() {
        accountService.delete(EXISTING_FIRST.getId());
        assertIterableEquals(Collections.singletonList(EXISTING_SECOND), accountService.getAll());
    }

    @Test
    void deleteNotExisted() {
        assertThrows(NotFoundException.class, () -> accountService.delete(NOT_EXISTING.getId()));
    }

    @Test
    void clear() {
        accountService.clear();
        assertIterableEquals(Collections.EMPTY_LIST, accountService.getAll());
    }
}