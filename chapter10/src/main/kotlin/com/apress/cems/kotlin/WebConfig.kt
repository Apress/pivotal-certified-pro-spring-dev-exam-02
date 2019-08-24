package com.apress.cems.kotlin

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = ["com.apress.cems"])
class WebConfig : WebMvcConfigurer {

    @Bean
    fun validator() : Validator {
       val validator = LocalValidatorFactoryBean()
        validator.setValidationMessageSource(messageSource())
       return validator
    }

    override fun getValidator() :Validator {
        return validator()
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:i18n/global")
        messageSource.setDefaultEncoding("UTF-8")
        messageSource.setUseCodeAsDefaultMessage(true)
        return messageSource
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val localeChangeInterceptor = LocaleChangeInterceptor()
        localeChangeInterceptor.paramName = "lang"
        return localeChangeInterceptor
    }

    @Bean
    fun localeResolver(): CookieLocaleResolver {
        val cookieLocaleResolver = CookieLocaleResolver()
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH)
        cookieLocaleResolver.cookieMaxAge = 3600
        cookieLocaleResolver.cookieName = "locale"
        return cookieLocaleResolver
    }
}