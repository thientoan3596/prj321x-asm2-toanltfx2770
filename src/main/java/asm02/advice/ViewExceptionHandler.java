package asm02.advice;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice(basePackages = "asm02.controller.view")
public class ViewExceptionHandler {
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e, Model model) {
        System.out.println("Caught");
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", 403);
        model.addAttribute("errorStatus", "Forbidden");
        model.addAttribute("errorDetail", "You do not have permission to access this resource.");
        return "public/error";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", 500);
        model.addAttribute("errorStatus", "Internal Server Error");
        return "public/error";
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", 404);
        model.addAttribute("errorStatus", "Not Found");
        model.addAttribute("errorDetail", "The resource you requested could not be found.");
        return "public/error";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(IllegalStateException e, Model model) {
        e.printStackTrace();
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.FORBIDDEN);
        model.addAttribute("errorStatus", "Forbidden");
        model.addAttribute("errorDetail", "How can you get there?");
        return "public/error";
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
