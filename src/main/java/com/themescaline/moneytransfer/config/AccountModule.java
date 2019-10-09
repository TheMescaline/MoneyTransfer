package com.themescaline.moneytransfer.config;

import com.google.inject.AbstractModule;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.dao.HibernateAccountDAO;

public class AccountModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountDAO.class).to(HibernateAccountDAO.class).asEagerSingleton();
    }
}
