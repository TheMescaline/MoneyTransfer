package com.themescaline.moneytransfer.config;

import com.google.inject.AbstractModule;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.dao.InMemoryAccountDAO;

public class TestAccountModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountDAO.class).to(InMemoryAccountDAO.class);
    }
}
