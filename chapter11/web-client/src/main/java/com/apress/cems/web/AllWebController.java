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
package com.apress.cems.web;

import com.apress.cems.detective.Detective;
import com.apress.cems.ex.InvalidCriteriaException;
import com.apress.cems.person.Person;
import com.apress.cems.util.CriteriaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Locale;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Controller
public class AllWebController {

    private static Logger logger = LoggerFactory.getLogger(AllWebController.class);

    private AllWebServices allWebServices;
    private MessageSource messageSource;


    public AllWebController(AllWebServices allWebServices, MessageSource messageSource) {
        this.allWebServices = allWebServices;
        this.messageSource = messageSource;
    }

    @GetMapping(value = "/persons")
    public String listPersons(Model model) {
        logger.info("Populating model with person list...");
        List<Person> persons =  allWebServices.getAllPersons();
        model.addAttribute("persons", persons);
        return "persons/list";
    }

    @GetMapping(value = "/persons/{id}")
    public String getPerson(@PathVariable Long id, Model model){
        model.addAttribute("person", allWebServices.getPerson(id));
        return "persons/show";
    }

    @GetMapping(value = "/persons/form")
    public String getSearchForm(CriteriaDto criteria){
        return "persons/search";
    }

    @GetMapping(value = "persons/search")
    public String processSubmit(@Validated @ModelAttribute("criteriaDto") CriteriaDto criteria, BindingResult result, Model model, Locale locale) {
        if (result.hasErrors()) {
            return "persons/search";
        }
        try {
            List<Person> persons = allWebServices.getByCriteriaDto(criteria);
            if (persons.size() == 0) {
                result.addError(new FieldError("criteriaDto", "noResults", messageSource.getMessage("NotEmpty.criteriaDto.noResults", null, locale)));
                return "persons/search";
            } else if (persons.size() == 1) {
                return "redirect:/persons/" + persons.get(0).getId();
            } else {
                model.addAttribute("persons", persons);
                return "persons/list";
            }
        } catch (InvalidCriteriaException ice) {
            result.addError(new FieldError("criteriaDto", ice.getFieldName(),
                    messageSource.getMessage(ice.getMessageKey(), null, locale)));
            return "persons/search";
        }
    }

    @GetMapping(value = "/detectives")
    public String listDetectives(Model model) {
        logger.info("Populating model with detective list...");
        List<Detective> detectives =  allWebServices.getAllDetectives();
        model.addAttribute("detectives", detectives);
        return "detectives/list";
    }

    @GetMapping(value = "/detectives/{id}")
    public String getDetective(@PathVariable Long id, Model model){
        model.addAttribute("detective", allWebServices.getDetective(id));
        return "detectives/show";
    }

}
