package com.rnd.exception;


import lombok.Getter;

@Getter
public abstract class ServiceException extends RuntimeException {

    private Object[] args;

    ServiceException(Object... args) {
        this.args = args;
    }

    public abstract String getMessageKey();

}
