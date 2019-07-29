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
package com.apress.cems.web.controllers;

import com.apress.cems.dao.Person;
import com.apress.cems.dj.services.PersonService;
import com.apress.cems.web.problem.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import static com.apress.cems.util.Functions.COMPARATOR_BY_ID;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Controller
@RequestMapping("/persons")
public class PersonController {

    private Logger logger = LoggerFactory.getLogger(PersonController.class);

    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Handles requests to list all persons.
     */
    @GetMapping(value = "/list")
    public String list(Model model) {
        logger.info("Populating model with list...");
        List<Person> persons =  personService.findAll();
        persons.sort(COMPARATOR_BY_ID);
        model.addAttribute("persons", persons);
        return "persons/list";
    }

    /**
     * Handles requests to show detail about one person.
     */
    @RequestMapping(value = "/{id:[\\d]*}", method = RequestMethod.GET)
    public String show(@PathVariable Long id, Model model) {
        Optional<Person> personOpt = personService.findById(id);
       if(personOpt.isPresent()) {
           model.addAttribute("person", personOpt.get());
       } else {
           throw new NotFoundException(Person.class, id);
       }
        return "persons/show";
    }

    // This was commented to move the implementation in the MissingRecordsHandler
   /* @ExceptionHandler
    public ModelAndView notFound(HttpServletRequest req, NotFoundException nfe) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("problem", "Malformed URL: " + req.getRequestURI() + "<br/>" + nfe.getMessage());
        mav.setViewName("error");
        return mav;
    }*/
}
