package com.apress.cems.kotlin

import org.springframework.context.MessageSource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDateTime
import java.time.format.DateTimeParseException
import java.util.*

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

@Controller
class WebControllers(private val personRepo: PersonRepo, private val detectiveRepo: DetectiveRepo, private val messageSource: MessageSource) {

    @GetMapping(value = ["/", "/home"])
    fun home(model: Model): String {
        model["message"] = "Spring MVC Kotlin Example!"
        return "home"
    }

    @GetMapping("/persons")
    fun persons(model:Model) : String {
        model["persons"] = personRepo.findAll()
        return "persons/list"
    }

    @GetMapping("/persons/{id}")
    fun person(@PathVariable id: Long, model:Model) : String {
        personRepo.findById(id).ifPresent { p ->
            model["person"] = p
        }
        return "persons/show"
    }

    @GetMapping("/detectives")
    fun detectives(model:Model) : String {
        model["detectives"] = detectiveRepo.findAll()
        return "detectives/list"
    }

    @GetMapping("/detectives/{id}")
    fun detective(@PathVariable id: Long, model:Model) : String {
        detectiveRepo.findById(id).ifPresent { d ->
            model["detective"] = d
        }
        return "detective/show"
    }

    @GetMapping("/persons/form")
    fun getSearchForm(criteria: CriteriaDto): String {
        return "persons/search"
    }

    @GetMapping("persons/search")
    fun performSearch(@Validated @ModelAttribute("criteriaDto")criteria: CriteriaDto, result: BindingResult , model: Model, locale: Locale) :String {
        if (result.hasErrors()) return "persons/search"

        return try {
            val persons = getByCriteriaDto(criteria)
            if (persons.isEmpty()) {
                result.addError(FieldError("criteriaDto", "noResults", messageSource.getMessage("NotEmpty.criteriaDto.noResults", null, locale)))
                "persons/search"
            } else if (persons.size == 1) {
                "redirect:/persons/" + persons[0].id
            } else {
                model["persons"] =  persons
                "persons/list"
            }
        } catch (ice: InvalidCriteriaException) {
            result.addError(FieldError("criteriaDto", ice.fieldName, messageSource.getMessage(ice.messageKey, null, locale)))
            "persons/search"
        }
    }

    @Throws(InvalidCriteriaException::class)
    fun getByCriteriaDto(criteria: CriteriaDto): List<Person> {
        var persons: List<Person> = ArrayList()
        val fg = FieldGroup.getField(criteria.fieldName)

        when (fg) {
            FieldGroup.FIRSTNAME -> persons = if (criteria.exactMatch)
                personRepo.findByFirstName(criteria.fieldValue)
            else
                personRepo.findByFirstNameLike(criteria.fieldValue)

            FieldGroup.LASTNAME -> persons = if (criteria.exactMatch)
                personRepo.findByLastName(criteria.fieldValue)
            else
                personRepo.findByLastNameLike(criteria.fieldValue)

            FieldGroup.USERNAME -> if (criteria.exactMatch) {
                val persOpt = personRepo.findByUsername(criteria.fieldValue)
                if (persOpt.isPresent()) {
                    return listOf(persOpt.get())
                }
            } else {
                persons = personRepo.findByUsernameLike(criteria.fieldValue)
            }
            FieldGroup.HIREDIN -> {
                val date: LocalDateTime
                try {
                    date = DateProcessor.toDate(criteria.fieldValue)
                } catch (e: DateTimeParseException) {
                    throw InvalidCriteriaException("fieldValue", "typeMismatch.hiringDate")
                }
                persons = personRepo.findByHiringDate(date)
            }
        }
        return persons
    }
}