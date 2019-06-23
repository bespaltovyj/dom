package com.rnd.exception;

public class IllegalArgumentServiceException extends ServiceException {

    private final String key;

    public IllegalArgumentServiceException(String key) {
        this.key = key;
    }

    @Override
    public String getMessageKey() {
        return key;
    }
}
