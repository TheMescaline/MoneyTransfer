package com.themescaline.moneytransfer;

import com.themescaline.moneytransfer.model.Account;

public class TestAccountData {
    public final static Account NEW_FIRST = new Account(10000.0);
    public final static Account NEW_SECOND = new Account(20000.0);
    public final static Account NEW_THIRD = new Account(30000.0);
    public final static Account EXISTED_FIRST = new Account(1L, 10000.0);
    public final static Account EXISTED_SECOND = new Account(2L, 20000.0);
    public final static Account EXISTED_THIRD = new Account(3L, 30000.0);
}
