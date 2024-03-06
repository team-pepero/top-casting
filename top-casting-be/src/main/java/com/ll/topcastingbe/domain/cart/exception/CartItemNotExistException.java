package com.ll.topcastingbe.domain.cart.exception;

public class CartItemNotExistException extends RuntimeException {
	public CartItemNotExistException(String message) {
		super(message);
	}
}
