package com.themescaline.moneytransfer.util;

import java.text.MessageFormat;

public enum ExceptionMessage {
    NOT_NEW_ACCOUNT("Save account must be new (must not have ID)"),
    NEGATIVE_BALANCE("Account balance can''t be negative"),
    NEGATIVE_TRANSFER("Amount of transferring money must not be negative"),
    NEGATIVE_WITHDRAW("Amount of withdrawing money must not be negative"),
    NEGATIVE_DEPOSIT("Amount of depositing money must not be negative"),

    NOT_FOUND("Account with ID {0} is not found"),
    NOT_ENOUGH_MONEY("Not enough money on account with ID {0}"),
    SERVER_CONFIGURATION_ERROR("Exception in HibernateSessionFactory configuration. Root: {0}"),
    ACCOUNT_LOCKED("Account with ID {0} is locked for another operation. Try again later"),
    CONCURRENT("Something went wrong. Exception message: {1}");

    private final String messageTemplate;

    ExceptionMessage(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    private String getMessageTemplate() {
        return messageTemplate;
    }

    public static String getFormatted(ExceptionMessage exceptionMessage, String... arguments) {
        return MessageFormat.format(exceptionMessage.getMessageTemplate(), arguments);
    }
}
