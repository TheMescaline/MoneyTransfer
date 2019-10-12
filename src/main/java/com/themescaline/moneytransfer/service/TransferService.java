package com.themescaline.moneytransfer.service;

import com.google.inject.ImplementedBy;
import com.themescaline.moneytransfer.model.DepositInfoPacket;
import com.themescaline.moneytransfer.model.TransferInfoPacket;
import com.themescaline.moneytransfer.model.WithdrawInfoPacket;

/**
 * Service for transferring operations
 *
 * @author lex.korovin@gmail.com
 */
@ImplementedBy(TransferServiceImpl.class)
public interface TransferService {
    void transfer(TransferInfoPacket packet);

    void withdraw(WithdrawInfoPacket packet);

    void deposit(DepositInfoPacket packet);
}
