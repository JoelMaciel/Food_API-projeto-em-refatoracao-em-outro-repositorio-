package com.joel.food.infrastructure.service.storage;

public class StorageExcepition extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public StorageExcepition(String message, Throwable cause) {
		super(message, cause);
	}

	public StorageExcepition(String message) {
		super(message);
	}
	

}
