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
package com.apress.cems.sec.mine;

import com.apress.cems.sec.detective.DetectiveRepo;
import com.apress.cems.sec.person.PersonRepo;
import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Component
@WebEndpoint(id = "dao")
public class DaoEndpoint implements ApplicationContextAware {
    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @ReadOperation
    public DaoHealth daoHealth(){
        Map<String, Object> details = new LinkedHashMap<>();
        if(personCheck(ctx.getBean(PersonRepo.class))) {
            details.put("personInit","SUCCESSFUL");
        }
        if(detectiveCheck(ctx.getBean(DetectiveRepo.class))) {
            details.put("detectiveInit","SUCCESSFUL");
        }
        DaoHealth daoHealth = new DaoHealth();
        daoHealth.setDaoDetails(details);
        return daoHealth;
    }

    @ReadOperation
    public DaoHealth repoHealth(@Selector String name){
        JpaRepository repo = (JpaRepository) ctx.getBean(name);
        Map<String, Object> details = new LinkedHashMap<>();
        if(repo instanceof PersonRepo && personCheck((PersonRepo) repo)) {
            details.put("personInit","SUCCESSFUL");
        } else if(repo instanceof DetectiveRepo && detectiveCheck((DetectiveRepo) repo)) {
            details.put("detectiveInit","SUCCESSFUL");
        } else {
            details.put("repoInit", "N/A");
        }
        DaoHealth daoHealth = new DaoHealth();
        daoHealth.setDaoDetails(details);
        return daoHealth;
    }

    @WriteOperation
    public void writeOperation(@Selector String name) {
        //perform write operation
    }
    @DeleteOperation
    public void deleteOperation(@Selector String name){
        //delete operation
    }

    private boolean personCheck(PersonRepo personRepo){
        return personRepo.findAll().size() == 4;
    }

    private boolean detectiveCheck(DetectiveRepo detectiveRepo){
        return detectiveRepo.findAll().size() == 4;
    }
}

