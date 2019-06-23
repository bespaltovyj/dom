package com.rnd.exception;


public abstract class ServiceException extends RuntimeException {

    public abstract String getMessageKey();

}
