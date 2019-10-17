package com.themescaline.moneytransfer.service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.themescaline.moneytransfer.config.TestAccountModule;
import com.themescaline.moneytransfer.dao.InMemoryAccountDAO;
import com.themescaline.moneytransfer.exceptions.AccountNotFoundException;
import com.themescaline.moneytransfer.exceptions.BalanceException;
import com.themescaline.moneytransfer.exceptions.IncorrectDataPacketException;
import com.themescaline.moneytransfer.model.Account;
import com.themescaline.moneytransfer.model.DepositInfoPacket;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import com.themescaline.moneytransfer.model.WithdrawInfoPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.themescaline.moneytransfer.TestAccountDataHelper.EXCEEDED_TRANSFER_AMOUNT;
import static com.themescaline.moneytransfer.TestAccountDataHelper.EXISTING_FIRST;
import static com.themescaline.moneytransfer.TestAccountDataHelper.EXISTING_SECOND;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NEGATIVE_AMOUNT;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NEW_FIRST;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NEW_SECOND;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NORMAL_TRANSFER_AMOUNT;
import static com.themescaline.moneytransfer.TestAccountDataHelper.NOT_EXISTING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferServiceTest {
    private Injector injector = Guice.createInjector(new TestAccountModule());
    private InMemoryAccountDAO dao = injector.getInstance(InMemoryAccountDAO.class);
    private TransferService transferService = injector.getInstance(TransferService.class);
    private AccountService accountService = injector.getInstance(AccountService.class);

    @BeforeEach
    void setUp() {
        dao.clear();
        accountService.save(new Account(NEW_FIRST));
        accountService.save(new Account(NEW_SECOND));
    }

    @Test
    void doTransferSuccess() {
        transferService.transfer(new TransferInfoPacket(EXISTING_FIRST.getId(), EXISTING_SECOND.getId(), NORMAL_TRANSFER_AMOUNT));
        assertEquals(EXISTING_FIRST.getBalance().subtract(NORMAL_TRANSFER_AMOUNT), accountService.getOne(EXISTING_FIRST.getId()).getBalance());
        assertEquals(EXISTING_SECOND.getBalance().add(NORMAL_TRANSFER_AMOUNT), accountService.getOne(EXISTING_SECOND.getId()).getBalance());
    }

    @Test
    void doTransferFailure() {
        assertThrows(BalanceException.class, () -> transferService.transfer(new TransferInfoPacket(EXISTING_FIRST.getId(), EXISTING_SECOND.getId(), EXCEEDED_TRANSFER_AMOUNT)));
    }

    @Test
    void doTransferNotExist() {
        assertThrows(AccountNotFoundException.class, () -> transferService.transfer(new TransferInfoPacket(NOT_EXISTING.getId(), EXISTING_SECOND.getId(), EXCEEDED_TRANSFER_AMOUNT)));
    }

    @Test
    void doWithdrawSuccess() {
        transferService.withdraw(new WithdrawInfoPacket(EXISTING_FIRST.getId(), NORMAL_TRANSFER_AMOUNT));
        assertEquals(EXISTING_FIRST.getBalance().subtract(NORMAL_TRANSFER_AMOUNT), accountService.getOne(EXISTING_FIRST.getId()).getBalance());
    }

    @Test
    void doWithdrawFailure() {
        assertThrows(BalanceException.class, () -> transferService.withdraw(new WithdrawInfoPacket(EXISTING_FIRST.getId(), EXCEEDED_TRANSFER_AMOUNT)));
    }

    @Test
    void doWithdrawNotExist() {
        assertThrows(AccountNotFoundException.class, () -> transferService.withdraw(new WithdrawInfoPacket(NOT_EXISTING.getId(), EXCEEDED_TRANSFER_AMOUNT)));
    }

    @Test
    void doDepositSuccess() {
        transferService.deposit(new DepositInfoPacket(EXISTING_FIRST.getId(), NORMAL_TRANSFER_AMOUNT));
        assertEquals(EXISTING_FIRST.getBalance().add(NORMAL_TRANSFER_AMOUNT), accountService.getOne(EXISTING_FIRST.getId()).getBalance());
    }

    @Test
    void doDepositFailure() {
        assertThrows(IncorrectDataPacketException.class, () -> transferService.deposit(new DepositInfoPacket(EXISTING_FIRST.getId(), NEGATIVE_AMOUNT)));
    }

    @Test
    void doDepositNotExist() {
        assertThrows(AccountNotFoundException.class, () -> transferService.deposit(new DepositInfoPacket(NOT_EXISTING.getId(), EXCEEDED_TRANSFER_AMOUNT)));
    }
}