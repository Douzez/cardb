package com.packt.cardatabase.exception;

public class OwnerNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public OwnerNotFoundException(Long id) {
		super("Could not find Owner with ID: " + id);
	}

}
