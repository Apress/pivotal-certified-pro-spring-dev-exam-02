package com.apress.cems.r2dbc;

import com.apress.cems.r2dbc.person.Person;

import java.time.LocalDateTime;

public class TestBase {

    protected Person createPerson(Long id, String loginuser, String sherlock, String holmes, String dudu) {
        Person person = new Person();
        person.setId(id);
        person.setLoginuser(loginuser);
        person.setFirstname(sherlock);
        person.setLastname(holmes);
        person.setPassword(dudu);
        person.setHiringdate(LocalDateTime.now());
        return person;
    }

}
