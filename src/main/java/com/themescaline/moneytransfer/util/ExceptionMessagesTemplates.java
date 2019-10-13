package com.themescaline.moneytransfer.util;

public enum ExceptionMessagesTemplates {
    NOT_FOUND("Account with id {0} is not found"),
    CANT_UPDATE("Can''t update account with id {0} because it''s not found"),
    CANT_DELETE("Can''t delete account with id {0} because it''s not found"),
    NOT_ENOUGH_MONEY("Not enough money on account with id {0}"),
    SERVER_CONFIGURATION_ERROR("Exception in HibernateSessionFactory configuration. Root: {0}"),
    NEGATIVE_BALANCE("Account balance can''t be negative"),
    NOT_NEW_ACCOUNT("Save account must be new (must not have id)"),
    NEGATIVE_TRANSFER("Amount of transferring money must not be negative"),
    NEGATIVE_WITHDRAW("Amount of withdrawing money must not be negative"),
    NEGATIVE_DEPOSIT("Amount of depositing money must not be negative");

    private final String messageTemplate;

    ExceptionMessagesTemplates(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}
