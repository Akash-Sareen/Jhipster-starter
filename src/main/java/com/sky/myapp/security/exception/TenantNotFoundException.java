package com.sky.myapp.security.exception;

public class TenantNotFoundException extends RuntimeException {

    public TenantNotFoundException() {
        super();
    }

    public TenantNotFoundException(String message) {
        super(message);
    }
}
