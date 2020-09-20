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
package com.apress.cems.r2dbc.person;

import com.apress.cems.r2dbc.person.services.PersonService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

@Component
public class PersonHandler {
    private PersonService personService;

    public PersonHandler(PersonService personService) {
        this.personService = personService;
    }

    public HandlerFunction<ServerResponse> list = serverRequest -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON).body(personService.findAll(), Person.class);

    public Mono<ServerResponse> show(ServerRequest serverRequest) {
        return personService.findById(Long.parseLong(serverRequest.pathVariable("id")))
                .flatMap(person -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Person.class)
                .flatMap(person -> personService.save(person))
                .flatMap(person -> ServerResponse.created(URI.create(
                        "/persons/" + person.getId())
                ).contentType(MediaType.APPLICATION_JSON).bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public HandlerFunction<ServerResponse> update = serverRequest -> ServerResponse.noContent()
            .build(personService.update(Long.parseLong(serverRequest.pathVariable("id")),
                    serverRequest.bodyToMono(Person.class)));

    public HandlerFunction<ServerResponse> delete = serverRequest -> ServerResponse.noContent()
            .build(personService.delete(Long.parseLong(serverRequest.pathVariable("id"))));
}

