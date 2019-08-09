/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.apress.cems;

import com.apress.cems.person.BetterPersonsController;
import com.apress.cems.person.PersonsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
//@ControllerAdvice(basePackages = "com.apress.cems.person")
@ControllerAdvice(basePackageClasses = BetterPersonsController.class)
public class RestExceptionsHandler {

    /*@ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> handleException(PersonsException pe) {
            return new ResponseEntity<>(pe.errorMessage(), pe.getStatus());
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("Unexpected Exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

   /* @ExceptionHandler
    @ResponseBody
    public ResponseEntity<JsonError> handleException(HttpServletRequest req, Exception e) {
        String errorURL = req.getRequestURL().toString();
        if(e instanceof PersonsException) {
            PersonsException pe = (PersonsException) e;
            return new ResponseEntity<>(new JsonError(errorURL, pe.getMessage()), pe.getStatus());
        }
        return new ResponseEntity<>(new JsonError(errorURL,"Unexpected Exception: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        if(e instanceof PersonsException) {
            PersonsException pe = (PersonsException) e;
            return new ResponseEntity<>(pe.errorMessage(), pe.getStatus());
        }
        return new ResponseEntity<>("Unexpected Exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
