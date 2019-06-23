package com.rnd.exception;


public class AccountAmountIsInsufficiently extends ServiceException {

    private static final String MESSAGE_KEY = "account.amount.insufficiently";

    @Override
    public String getMessageKey() {
        return MESSAGE_KEY;
    }
}
