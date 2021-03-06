package com.themescaline.moneytransfer.util.multithread;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Locker object, used for concurrent access
 *
 * @author lex.korovin@gmail.com
 */
@Data
@EqualsAndHashCode
public class AccountLocker {
    @EqualsAndHashCode.Exclude
    private Lock lock = new ReentrantLock();
    private long accountId;

    public AccountLocker(long accountId) {
        this.accountId = accountId;
    }
}
