package com.themescaline.moneytransfer.util;

public enum ExceptionMessagesTemplates {
    NOT_FOUND("Account with id {0} is not found"),
    CANT_UPDATE("Can''t update account with id {0} because it''s not found"),
    CANT_DELETE("Can''t delete account with id {0} because it''s not found"),
    NOT_ENOUGH_MONEY("Not enough money on account with id {0}");

    private final String messageTemplate;

    ExceptionMessagesTemplates(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }
}
