package com.themescaline.moneytransfer.service;

import com.google.inject.ImplementedBy;
import com.themescaline.moneytransfer.model.TransferInfoPacket;

/**
 * Service for transferring operations
 *
 * @author lex.korovin@gmail.com
 */
@ImplementedBy(TransferServiceImpl.class)
public interface TransferService {
    void doTransfer(TransferInfoPacket packet);
}
