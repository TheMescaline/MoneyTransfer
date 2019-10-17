package com.themescaline.moneytransfer;

import com.themescaline.moneytransfer.model.Account;

import java.math.BigDecimal;

public class TestAccountDataHelper {
    public final static BigDecimal NORMAL_TRANSFER_AMOUNT = new BigDecimal(5000.0);
    public final static BigDecimal EXCEEDED_TRANSFER_AMOUNT = new BigDecimal(1_000_000_000.0);
    public final static BigDecimal NEGATIVE_AMOUNT = new BigDecimal(-1_000.0);
    public final static Account NEW_FIRST = new Account(new BigDecimal(10000.0));
    public final static Account NEW_SECOND = new Account(new BigDecimal(20000.0));
    public final static Account NEW_THIRD = new Account(new BigDecimal(30000.0));
    public final static Account EXISTING_FIRST = new Account(1L, new BigDecimal(10000.0));
    public final static Account EXISTING_SECOND = new Account(2L, new BigDecimal(20000.0));
    public final static Account EXISTING_THIRD = new Account(3L, new BigDecimal(30000.0));
    public final static Account NOT_EXISTING = new Account(999L, new BigDecimal(3000000.0));
}
