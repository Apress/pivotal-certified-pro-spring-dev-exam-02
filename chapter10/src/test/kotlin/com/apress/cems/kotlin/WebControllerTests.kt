package com.apress.cems.kotlin

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import org.hamcrest.Matchers.*
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.isNotNull
import org.mockito.Mockito.`when`
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*


/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@WebMvcTest
class WebControllerTests() {

    @MockBean lateinit var personRepo: PersonRepo
    @MockBean lateinit var detectiveRepo: DetectiveRepo

    lateinit var mockMvc: MockMvc

    @Autowired lateinit var webApplicationContext: WebApplicationContext

    @BeforeEach
    fun setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `List Persons` () {
        val person1 = Person("test", "person", "test.person","test", DateProcessor.toDate("1983-08-15 00:23"))
        val person2 = Person("second", "person", "second.person","second", DateProcessor.toDate("1983-08-18 00:23"))
        val list = listOf(person1, person2)

        `when`(personRepo.findAll()).thenReturn(list)

        mockMvc.perform(get("/persons/"))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/list"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("persons"))
                .andExpect(model().size<List<Person>>(1))
    }

    @Test
    fun `Person` () {
        val person = Person("test", "person", "test.person","test", DateProcessor.toDate("1983-08-15 00:23"))
        person.id = 1

        `when`(personRepo.findById(anyLong())).thenReturn(Optional.of(person))

        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/show"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("person"))
                .andExpect(model().size<Person>(1))
    }
}