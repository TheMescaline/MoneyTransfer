package com.themescaline.moneytransfer.config;

import com.google.inject.AbstractModule;
import com.themescaline.moneytransfer.dao.AccountDAO;
import com.themescaline.moneytransfer.dao.HibernateAccountDAO;

/**
 * Class for configuring Guice library
 *
 * @author lex.korovin@gmail.com
 */
public class AccountModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountDAO.class).to(HibernateAccountDAO.class);
    }
}
