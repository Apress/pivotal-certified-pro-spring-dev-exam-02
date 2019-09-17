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
package com.apress.cems.mine;

import com.apress.cems.detective.DetectiveRepo;
import com.apress.cems.person.PersonRepo;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Profile("two")
@Component
public class HealthChecker implements HealthIndicator, ApplicationContextAware {
    private ApplicationContext ctx;

    private final static Status FATAL = new Status("FATAL", "All Systems Down!");

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public Health health() {
        // check initialization of Person table by counting the records

        Map<String,String> inits = new HashMap<>();
        if(personCheck(ctx.getBean(PersonRepo.class))) {
           inits.put("personInit","SUCCESSFUL");
        } else {
            //return Health.down().withDetail("personInit", "FAILED").build();
            return Health.status(FATAL).withDetail("personInit", "FAILED").build();
        }
        if(detectiveCheck(ctx.getBean(DetectiveRepo.class))) {
            inits.put("detectiveInit","SUCCESSFUL");
        } else {
            //return Health.down().withDetail("detectiveInit", "FAILED").build();
            return Health.status(FATAL).withDetail("detectiveInit", "FAILED").build();
        }
       return Health.up().withDetails(inits).build();
    }

    private boolean personCheck(PersonRepo personRepo){
        return personRepo.findAll().size() == 4;
    }

    private boolean detectiveCheck(DetectiveRepo detectiveRepo){
        return detectiveRepo.findAll().size() == 4;
    }
}

