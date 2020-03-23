/*
Freeware License, some rights reserved

Copyright (c) 2020 Iuliana Cosmina

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
package com.apress.cems.beans.required;

import com.apress.cems.beans.required.four.OptionalPartsCar;
import com.apress.cems.beans.required.four.OptionalPartsCarConfig;
import com.apress.cems.beans.required.one.Car;
import com.apress.cems.beans.required.one.CarConfig;
import com.apress.cems.beans.required.three.YetAnotherCar;
import com.apress.cems.beans.required.three.YetAnotherCarConfig;
import com.apress.cems.beans.required.two.AnotherCar;
import com.apress.cems.beans.required.two.AnotherCarConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class RequiredTest {
    private Logger logger = LoggerFactory.getLogger(RequiredTest.class);

    @Test
    void testCarConfig() {
        Assertions.assertThrows(UnsatisfiedDependencyException.class, () -> {
            var ctx = new AnnotationConfigApplicationContext(CarConfig.class);

            var car = ctx.getBean(Car.class);
            assertNotNull(car);
            logger.debug(car.toString());

            ctx.close();
        });
    }

    @Test
    void testAnotherCarConfig() {
        var ctx = new AnnotationConfigApplicationContext(AnotherCarConfig.class);

        var anotherCar = ctx.getBean(AnotherCar.class);
        assertNotNull(anotherCar);
        logger.debug(anotherCar.toString());

        ctx.close();
    }

    @Test
    void testYetAnotherCarConfig() {
        Assertions.assertThrows(UnsatisfiedDependencyException.class, () -> {
            var ctx = new AnnotationConfigApplicationContext(YetAnotherCarConfig.class);

            var yetAnotherCar = ctx.getBean(YetAnotherCar.class);
            assertNotNull(yetAnotherCar);
            logger.debug(yetAnotherCar.toString());

            ctx.close();
        });
    }

    @Test
    void testOptionalPartsCarConfig() {
        var ctx = new AnnotationConfigApplicationContext(OptionalPartsCarConfig.class);

        var anotherCar = ctx.getBean(OptionalPartsCar.class);
        assertNotNull(anotherCar);
        logger.debug(anotherCar.toString());

        ctx.close();
    }
}
