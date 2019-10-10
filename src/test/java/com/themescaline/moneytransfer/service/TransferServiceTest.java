package com.themescaline.moneytransfer.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.themescaline.moneytransfer.config.TestAccountModule;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.themescaline.moneytransfer.TestAccountData.*;
import static org.junit.jupiter.api.Assertions.*;

class TransferServiceTest {
    private final static double NORMAL_TRANSFER_AMOUNT = 5000.0;
    private final static double EXCEEDED_TRANSFER_AMOUNT = 1_000_000_000.0;

    private Injector injector = Guice.createInjector(new TestAccountModule());
    private TransferService transferService = injector.getInstance(TransferService.class);
    private AccountService accountService = injector.getInstance(AccountService.class);

    @BeforeEach
    void setUp() {
        accountService.clear();
        accountService.save(new Account(NEW_FIRST));
        accountService.save(new Account(NEW_SECOND));
    }

    @Test
    void doTransferSuccess() {
        assertTrue(transferService.doTransfer(new TransferInfoPacket(EXISTED_FIRST.getId(), EXISTED_SECOND.getId(), NORMAL_TRANSFER_AMOUNT)));
        assertEquals(accountService.getOne(EXISTED_FIRST.getId()).getBalance(), EXISTED_FIRST.getBalance() - NORMAL_TRANSFER_AMOUNT);
        assertEquals(accountService.getOne(EXISTED_SECOND.getId()).getBalance(), EXISTED_SECOND.getBalance() + NORMAL_TRANSFER_AMOUNT);
    }

    @Test
    void doTransferFailure() {
        assertFalse(transferService.doTransfer(new TransferInfoPacket(EXISTED_FIRST.getId(), EXISTED_SECOND.getId(), EXCEEDED_TRANSFER_AMOUNT)));
        assertEquals(accountService.getOne(EXISTED_FIRST.getId()).getBalance(), EXISTED_FIRST.getBalance());
        assertEquals(accountService.getOne(EXISTED_SECOND.getId()).getBalance(), EXISTED_SECOND.getBalance());
    }
}