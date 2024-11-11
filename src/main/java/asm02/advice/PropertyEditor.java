package asm02.advice;


import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

@ControllerAdvice
public class PropertyEditor {
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.isEmpty()) {
                    setValue(null); // Handle empty string as null
                } else {
                    setValue(Date.valueOf(text)); // Parse valid string to java.sql.Date
                }
            }
        });
    }
}
