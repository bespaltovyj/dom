package com.rnd.exception;

public class NotFoundException extends ServiceException {

    private final String key;

    public NotFoundException(String key, Object... args) {
        super(args);
        this.key = key;
    }

    public NotFoundException(String key) {
        this.key = key;
    }

    @Override
    public String getMessageKey() {
        return key;
    }
}
