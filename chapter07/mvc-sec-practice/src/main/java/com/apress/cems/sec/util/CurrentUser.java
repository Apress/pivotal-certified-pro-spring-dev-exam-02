package com.apress.cems.sec.util;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

    @AliasFor(annotation = AuthenticationPrincipal.class)
    boolean errorOnInvalidType() default false;

    @AliasFor(annotation = AuthenticationPrincipal.class)
    String expression() default "";
}
