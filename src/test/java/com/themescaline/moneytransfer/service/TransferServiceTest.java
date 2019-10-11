package com.themescaline.moneytransfer.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.themescaline.moneytransfer.config.TestAccountModule;
import com.themescaline.moneytransfer.exceptions.AppException;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotFoundException;

import static com.themescaline.moneytransfer.TestAccountData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferServiceTest {
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
        transferService.doTransfer(new TransferInfoPacket(EXISTING_FIRST.getId(), EXISTING_SECOND.getId(), NORMAL_TRANSFER_AMOUNT));
    }

    @Test
    void doTransferFailure() {
        assertThrows(AppException.class, () -> transferService.doTransfer(new TransferInfoPacket(EXISTING_FIRST.getId(), EXISTING_SECOND.getId(), EXCEEDED_TRANSFER_AMOUNT)));
    }

    @Test
    void doTransferNotExist() {
        assertThrows(NotFoundException.class, () -> transferService.doTransfer(new TransferInfoPacket(NOT_EXISTING.getId(), EXISTING_SECOND.getId(), EXCEEDED_TRANSFER_AMOUNT)));
    }
}