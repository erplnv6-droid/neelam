package com.SCM.advice; 

import com.SCM.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;



@RestControllerAdvice
public class TokenControllerAdvice  {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
    }

    
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException noSuchElementException)
//    {
//    	return new ResponseEntity<>("No value is present in DB, please change request",HttpStatus.NOT_FOUND);
//    }
    

    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex)
    {
    	Map<String, String> errormap = new HashMap<>();
    	ex.getBindingResult().getFieldErrors().forEach(error -> {
    		errormap.put(error.getField(), error.getDefaultMessage());
    	});
    	return errormap;
    }
    

    
}
