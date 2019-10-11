package com.themescaline.moneytransfer;

import com.themescaline.moneytransfer.model.Account;

public class TestAccountDataHelper {
    public final static double NORMAL_TRANSFER_AMOUNT = 5000.0;
    public final static double EXCEEDED_TRANSFER_AMOUNT = 1_000_000_000.0;
    public final static Account NEW_FIRST = new Account(10000.0);
    public final static Account NEW_SECOND = new Account(20000.0);
    public final static Account NEW_THIRD = new Account(30000.0);
    public final static Account EXISTING_FIRST = new Account(1L, 10000.0);
    public final static Account EXISTING_SECOND = new Account(2L, 20000.0);
    public final static Account EXISTING_THIRD = new Account(3L, 30000.0);
    public final static Account NOT_EXISTING = new Account(999L, 3000000.0);
}
