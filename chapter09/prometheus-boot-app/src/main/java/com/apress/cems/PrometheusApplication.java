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
package com.apress.cems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Iuliana
 * Cosmina
 * @since 1.0
 * Observation: In application.yml add a property named:
 * spring:
    profiles:
 *    active: {profileName}
 * {@code profileName} can be any of:
 *  - all : exposes all actuator endpoints
 *  - none : hides all actuator endpoints
 *  - some : exposes only some of the actuator endpoints
 *  Then run this class and go to {@code http://localhost:8081/actuator} to check the list of exposed endpoints
 */
@SpringBootApplication
public class PrometheusApplication {

    private static Logger logger = LoggerFactory.getLogger(PrometheusApplication.class);

    public static void main(String... args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(PrometheusApplication.class, args);
        ctx.registerShutdownHook();
        logger.info("Application Started ...");
    }
}
