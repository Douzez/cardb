package com.packt.cardatabase.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.packt.cardatabase.exception.OwnerNotFoundException;

@ControllerAdvice
public class OwnerNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(OwnerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String ownerNotFOundHandler(OwnerNotFoundException e) {
		return e.getMessage();
	}
	
}
