package com.SCM.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.SCM.dto.ExceptionDto;


@RestControllerAdvice
public class GlobalException {
	
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> globallyException(NoSuchElementException ex)
	{
		ExceptionDto dto=new ExceptionDto();
		
		dto.setMessage(ex.getMessage());
		dto.setStatuscode("400 bad request");
		dto.setStatus(false);
	
		System.out.println(dto.toString());
		
		return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> globallyException(EntityNotFoundException ex)
	{
		ExceptionDto dto=new ExceptionDto();
		
		dto.setMessage(ex.getMessage());
		dto.setStatuscode("400 bad request");
		dto.setStatus(false);
	
		System.out.println(dto.toString());
		
		return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
	}
	
//	  @ExceptionHandler(MethodArgumentNotValidException.class)
//	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//	        Map<String, String> errors = new HashMap<>();
//
//	        ex.getBindingResult().getFieldErrors().forEach(error ->
//	                errors.put(error.getField(), error.getDefaultMessage())
//	        );
//
//	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//	    }
	

}
