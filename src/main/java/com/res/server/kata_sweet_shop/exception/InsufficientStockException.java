package com.res.server.kata_sweet_shop.exception;


public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String msg) { super(msg); }
}
