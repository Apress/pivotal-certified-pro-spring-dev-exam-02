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

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
class FluxPlayTest {
    private Logger logger = LoggerFactory.getLogger(FluxPlayTest.class);

    @Test
    void emptyFlux() throws InterruptedException {
        Flux.empty().log().subscribe();

        Thread.sleep(1000L);
    }

    @Test
    void simple() throws InterruptedException {
        Flux.just("0", "1", "2", "3", "4", "5", "6", "7")
                .log()
                .subscribe();

        Thread.sleep(1000L);
    }

    // Showing you how SignalLogger identifies streams :D
    @Test
    void simple2() throws InterruptedException {
        Flux f1 = Flux.just("0", "1", "2", "3", "4", "5", "6", "7").log();
        Flux f2 = Flux.just("8", "9", "10", "11", "12", "13", "14", "15").log();
        f1.subscribe();
        f2.subscribe();

        Thread.sleep(1000L);
    }

    @Test
    void simpleSubscriber() throws InterruptedException {
        Flux.just("0", "1", "2", "3", "4", "5", "6", "7")
                .subscribe(new SimpleSubscriber());

        Thread.sleep(1000L);
    }

    @Test
    void genericSubscriber() throws InterruptedException {
        Flux f = Flux.just("0", "1", "2", "3", "4", "5", "6", "7");
                f.subscribe(new GenericSubscriber<>());

        Thread.sleep(1000L);
    }

    @Test
    void simpleConsumer() throws InterruptedException {
        Flux.just("0", "1", "2", "3", "4", "5", "6", "7")
                .subscribe(s -> logger.info("Consuming {}", s));

        Thread.sleep(1000L);
    }

    @Test
    void fromList() throws  InterruptedException {
        Flux.fromIterable(List.of("0", "1", "2", "3", "4", "5", "6", "7"))
                .log()
                .subscribe();

        Thread.sleep(1000L);
    }

    @Test
    void parallelFlux() throws  InterruptedException {
        Flux.fromIterable(List.of("0", "1", "2", "3", "4", "5", "6", "7")).parallel(2)
                .log()
                .runOn(Schedulers.parallel(), 1)
                .subscribe();

        Thread.sleep(1000L);
    }

    @Test
    void parallelSubscribe() throws  InterruptedException {
        Flux.fromIterable(List.of("0", "1", "2", "3", "4", "5", "6", "7"))
                .log()
                .subscribeOn(Schedulers.parallel())
                .subscribe();

        Thread.sleep(1000L);
    }

    @Test
    void withSubscriber() throws InterruptedException {
        Flux.just("0", "1", "2", "3", "4", "5", "6", "7")
                .log()
                .subscribeOn(Schedulers.newParallel("sub"))
                .publishOn(Schedulers.newParallel("pub"), 2)
                .subscribe(new SimpleSubscriber());

        Thread.sleep(5000L);
    }

    @Test
    void integerStream() throws InterruptedException {
       Flux<Integer> ints =  Flux.just("0", "1", "2", "3", "4", "5", "6", "7")
               .map(Integer::parseInt);

        ints.reduce(0, Integer::sum)
                .subscribe(s -> logger.info("Sum: {}", s));

        Thread.sleep(5000L);
    }
}
