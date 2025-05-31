<<<<<<< HEAD
package com.jobportal.exception; // update this with your actual package
=======
package com.jobportal.exception; 
>>>>>>> 37b41dd1f627368575567e5ddfe807bcc402c212

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Catch all unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>("Something went wrong. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
