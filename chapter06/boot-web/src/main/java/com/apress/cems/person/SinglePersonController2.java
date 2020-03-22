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
package com.apress.cems.person;

import com.apress.cems.ex.NotFoundException;
import com.apress.cems.person.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Size;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
//@Controller
//@RequestMapping("/persons/{id}")
public class SinglePersonController2 {

    private PersonService personService;

    public SinglePersonController2(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Finds the person managed by the methods in this controller and adds it to the model
     * @param id
     *      the id of the Person instance to retrieve
     * @return person
     */
    @ModelAttribute
    protected Person modelPerson(@PathVariable Long id){
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            return personOpt.get();
        } else {
            throw new NotFoundException(Person.class, id);
        }
    }

    /**
     * Handles requests to show detail about one person.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String show() {
        return "persons/show";
    }

    @GetMapping("/edit")
    public String edit() {
        return "persons/edit";
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String update(@Validated Person person, BindingResult result){
        // do some logic
        return "redirect:/persons/" + person.getId();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Validated Person person, BindingResult result){
        //do some logic
        return "redirect:/persons/" + person.getId();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(){
        //do some logic
        return "redirect:/persons/list";
    }
}
