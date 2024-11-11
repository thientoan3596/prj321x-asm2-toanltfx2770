package asm02.advice;

import asm02.client.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(basePackages = "asm02.controller.rest")
public class RestExceptionHandler {
    @ExceptionHandler({org.springframework.security.access.AccessDeniedException.class, HttpClientErrorException.Forbidden.class})
    public ResponseEntity<List<ErrorResponse>> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse error = new ErrorResponse();
        error.setDefaultMessage(e.getMessage());
        error.setName(e.getClass().getSimpleName().replace("Exception",""));
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(error);
        System.out.println("REST Exception Handler.\n"+e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errors);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorResponse>> handleGeneralException(Exception e) {
        ErrorResponse error = new ErrorResponse();
        error.setDefaultMessage(e.getMessage());
        error.setName(e.getClass().getSimpleName().replace("Exception",""));
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(error);
        System.out.println("REST Exception Handler.\n"+e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errors);
    }
    /*@ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException e,Model model){
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", 404);
        model.addAttribute("errorStatus", "Not Found");
        model.addAttribute("errorDetail", "Page you are looking for ");
        return "public/error";
    }*/
}
