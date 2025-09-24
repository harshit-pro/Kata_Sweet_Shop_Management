package com.res.server.kata_sweet_shop.exception;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg) { super(msg); }
}
