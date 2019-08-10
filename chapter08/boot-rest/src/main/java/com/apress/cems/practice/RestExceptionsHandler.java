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
package com.apress.cems.practice;

import com.apress.cems.practice.person.PersonsController;
import com.apress.cems.practice.person.PersonsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@ControllerAdvice(basePackageClasses = PersonsController.class)
public class RestExceptionsHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        if(e instanceof PersonsException) {
            PersonsException pe = (PersonsException) e;
            return new ResponseEntity<>(pe.errorMessage(), pe.getStatus());
        } else if(e instanceof DataIntegrityViolationException) {
            return new ResponseEntity<>("Another entity with the same identity exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Unexpected Exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
