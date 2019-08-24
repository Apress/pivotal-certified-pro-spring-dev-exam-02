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
package com.apress.cems.web;

import com.apress.cems.detective.Detective;
import com.apress.cems.ex.InvalidCriteriaException;
import com.apress.cems.person.Person;
import com.apress.cems.util.CriteriaDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.apress.cems.base.AbstractEntity.COMPARATOR_BY_ID;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
public class AllWebServices {

    private RestTemplate restTemplate;

    private static final String PERSONS_SERVICE_URL = "http://persons-service";

    private static final String DETECTIVES_SERVICE_URL = "http://detectives-service";

    public AllWebServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    List<Person> getAllPersons(){
        var persons = restTemplate.getForObject(PERSONS_SERVICE_URL.concat("/persons"), Person[].class);
        assert persons != null;
        var personsList =  Arrays.asList(persons);
        personsList.sort(COMPARATOR_BY_ID);
        return personsList;
    }

    Person getPerson(Long personId) {
        return restTemplate.getForObject(PERSONS_SERVICE_URL.concat("/persons/" + personId), Person.class);
    }

    List<Detective> getAllDetectives(){
        var detectives = restTemplate.getForObject(DETECTIVES_SERVICE_URL.concat("/detectives"), Detective[].class);
        assert detectives != null;
        var detectiveList =  Arrays.asList(detectives);
        detectiveList.forEach(d ->
            d.setPerson(restTemplate.getForObject(PERSONS_SERVICE_URL.concat("/persons/" + d.getPersonId()), Person.class))
        );
        detectiveList.sort(COMPARATOR_BY_ID);
        return detectiveList;
    }

    Detective getDetective(Long detectiveId) {
        var detective = restTemplate.getForObject(DETECTIVES_SERVICE_URL.concat("/detectives/" + detectiveId), Detective.class);
        detective.setPerson(getPerson(detective.getPersonId()));
        return detective;
    }

    List<Person> getByCriteriaDto(CriteriaDto criteria) throws InvalidCriteriaException {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CriteriaDto> entity = new HttpEntity<>(criteria, headers);
        ResponseEntity<Person[]> responseEntity = restTemplate.exchange(PERSONS_SERVICE_URL.concat("/persons/search"), HttpMethod.GET, entity, Person[].class);

        var persons = responseEntity.getBody();
        assert persons != null;
        var personsList =  Arrays.asList(persons);
        personsList.sort(COMPARATOR_BY_ID);
        return personsList;
    }
}
