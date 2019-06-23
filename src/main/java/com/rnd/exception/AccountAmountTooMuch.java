package com.rnd.exception;


public class AccountAmountTooMuch extends ServiceException {

    private static final String MESSAGE_KEY = "account.amount.too_much";

    public AccountAmountTooMuch(Object... args) {
        super(args);
    }

    @Override
    public String getMessageKey() {
        return MESSAGE_KEY;
    }
}
