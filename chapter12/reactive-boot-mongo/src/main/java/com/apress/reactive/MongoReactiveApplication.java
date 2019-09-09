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
package com.apress.reactive;

import com.apress.reactive.person.PersonHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 * Spring Data, an umbrella data access framework, supports a number of reactive data access options including reactive Cassandra, reactive MongoDB, reactive Couchbase and reactive Redis.
 */
@SpringBootApplication
public class MongoReactiveApplication {

    private static Logger logger = LoggerFactory.getLogger(MongoReactiveApplication.class);

    private final PersonHandler personHandler;

    public MongoReactiveApplication(PersonHandler personHandler) {
        this.personHandler = personHandler;
    }

    @Bean
    RouterFunction<ServerResponse> routingFunction() {
        return route(GET("/home"), serverRequest -> ok().body(fromObject("works!")))
                .andRoute(GET("/persons"), personHandler.list)
                .andRoute(GET("/persons/{id}"), personHandler::show)
                .andRoute(PUT("/persons/{id}"), personHandler.update)
                .andRoute(POST("/persons"), personHandler::save)
                .andRoute(DELETE("/persons/{id}"), personHandler.delete)
                .filter((request, next) -> {
                    logger.info("Before handler invocation: " + request.path());
                    return next.handle(request);
                });
    }

    public static void main(String... args) {
        ConfigurableApplicationContext ctx = SpringApplication.run( MongoReactiveApplication.class, args);
        ctx.registerShutdownHook();
        logger.info("Application Started ...");
    }
}
