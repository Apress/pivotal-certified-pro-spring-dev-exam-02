package com.apress.cems.kotlin

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinApplicationTests(@Autowired val restTemplate: TestRestTemplate) {

	@Test
	fun `Context Loads`() {}

	@Test
	fun `Test Home Page`(){
		val entity = restTemplate.getForEntity<String>("/", String::class.java)
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("Spring MVC Kotlin Example!")
	}
}
