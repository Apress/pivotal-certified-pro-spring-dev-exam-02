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


import com.apress.cems.ex.CreationException;
import com.apress.cems.ex.NotFoundException;
import com.apress.cems.person.services.PersonService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
//@RestController
@Controller
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //@ResponseBody
    @GetMapping(value =  {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> all(){
        return personService.findAll();
    }

    /**
     * Handles requests to show detail about one person.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Person show(@PathVariable Long id) {
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
            return personOpt.get();
        } else {
            throw new NotFoundException(Person.class, id);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Person update(@PathVariable Long id, @Validated Person person){
        Optional<Person> personOpt = personService.findById(id);
        if(personOpt.isPresent()) {
           personService.save(person);
        } else {
            throw new NotFoundException(Person.class, id);
        }
        return person;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Person newPerson, @Value("#{request.requestURL}") StringBuffer originalUrl, HttpServletResponse response){
        if (newPerson.getId() != null) {
            throw new CreationException("Person found with id " + newPerson.getId() + ". Cannot create!");
        }
        personService.save(newPerson);
        response.setHeader("Location", getLocationForTransaction(originalUrl, newPerson.getId()));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        personService.findById(id).ifPresent(person -> personService.delete(person));
    }


    /**
     * Determines URL of transaction resource based on the full URL of the given request,
     * appending the path info with the given childIdentifier using a UriTemplate.
     */
    private static String getLocationForTransaction(StringBuffer url, Object childIdentifier) {
        UriTemplate template = new UriTemplate(url.toString());
        return template.expand(childIdentifier).toASCIIString();
    }
}
