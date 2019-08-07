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
package com.apress.cems.dj.services;

import com.apress.cems.dao.*;
import com.apress.cems.util.*;
import org.apache.commons.lang3.time.DateParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
public class Initializer {
    private Logger logger = LoggerFactory.getLogger(Initializer.class);

    private PersonService personService;

    private DetectiveService detectiveService;

    private CriminalCaseService criminalCaseService;

    private StorageService storageService;

    public Initializer(PersonService personService, DetectiveService detectiveService, CriminalCaseService criminalCaseService, StorageService storageService) {
        this.personService = personService;
        this.detectiveService = detectiveService;
        this.criminalCaseService = criminalCaseService;
        this.storageService = storageService;
    }

    @PostConstruct
    public void init() {
        logger.info(" -->> Starting database initialization...");

        if (storageService.findAll().isEmpty()) {
            createStorages();
        }

        if (personService.findAll().isEmpty()) {
            createPersons();
        }

        if (detectiveService.findAll().isEmpty()) {
            createDetectives();
        }
        logger.info(" -->> Database initialization finished.");
    }

    private void createStorages() {
        var storage = new Storage();
        storage.setName("Central Storage");
        storage.setLocation("London, NW1 6XE");
        storageService.save(storage);
    }

    private void createDetectives() {
        var storage = storageService.findAll().get(0);

        Optional<Person> personOpt = personService.findByCompleteName("Sherlock", "Holmes");
        personOpt.ifPresent( person -> {
            Detective detective = createDetective(person, Rank.INSPECTOR, false, EmploymentStatus.ACTIVE);
            detectiveService.save(detective);

            CriminalCase criminalCase = new CriminalCase();
            criminalCase.setNumber(NumberGenerator.getCaseNumber());
            criminalCase.setType(CaseType.FELONY);
            criminalCase.setShortDescription("White female stabbed 13 times.");
            criminalCase.setStatus(CaseStatus.CLOSED);
            criminalCase.setNotes("It was obvious the husband did it.");
            criminalCase.setLeadInvestigator(detective);

            var evidence = new Evidence();
            evidence.setStorage(storage);
            evidence.setCriminalCase(criminalCase);
            evidence.setNumber(NumberGenerator.getEvidenceNumber());
            evidence.setItemName("Bloody Knife");
            evidence.setNotes("The printed of the husband were on the bloody knife.");
            evidence.setArchived(true);
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.SUBMITTED,"Submitting bloody knife as evidence."));
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.RETRIEVED,"Forensic Analysis - look for prints."));
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.RETURNED,"Forensic Analysis completed."));

            criminalCase.addEvidence(evidence);
            criminalCaseService.save(criminalCase);

            criminalCase = new CriminalCase();
            criminalCase.setNumber(NumberGenerator.getCaseNumber());
            criminalCase.setType(CaseType.FELONY);
            criminalCase.setShortDescription("Black child kidnapped from kindergarden.");
            criminalCase.setStatus(CaseStatus.UNDER_INVESTIGATION);
            criminalCase.setNotes("Suspect: ex-wife.");
            criminalCase.setLeadInvestigator(detective);

            evidence = new Evidence();
            evidence.setStorage(storage);
            evidence.setCriminalCase(criminalCase);
            evidence.setNumber(NumberGenerator.getEvidenceNumber());
            evidence.setItemName("Dirty backpack");
            evidence.setNotes("A dirty backpack was found in the woods.");
            evidence.setArchived(true);
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.SUBMITTED,"Submitting the backpack as evidence."));
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.RETRIEVED,"Forensic Analysis - look for prints."));
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.RETURNED,"Forensic Analysis completed."));

            criminalCase.addEvidence(evidence);
            criminalCaseService.save(criminalCase);

        });

        personOpt = personService.findByCompleteName("Jackson", "Brodie");
        personOpt.ifPresent( person -> {
            Detective detective = createDetective(person, Rank.SENIOR, true, EmploymentStatus.ACTIVE);
            detectiveService.save(detective);

            CriminalCase criminalCase = new CriminalCase();
            criminalCase.setNumber(NumberGenerator.getCaseNumber());
            criminalCase.setType(CaseType.FELONY);
            criminalCase.setShortDescription("Arson: house was intentionally burned down.");
            criminalCase.setStatus(CaseStatus.CLOSED);
            criminalCase.setNotes("Owners burned it for insurance.");
            criminalCase.setLeadInvestigator(detective);


            var evidence = new Evidence();
            evidence.setStorage(storage);
            evidence.setCriminalCase(criminalCase);
            evidence.setNumber(NumberGenerator.getEvidenceNumber());
            evidence.setItemName("Dirty accelerant bottle.");
            evidence.setNotes("A broken whiskey bottle was found at the scene.");
            evidence.setArchived(true);
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.SUBMITTED,"Submitting broken bottle as evidence."));
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.RETRIEVED,"Forensic Analysis - look for prints and take samples."));
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.RETURNED,"Forensic Analysis completed."));

            criminalCase.addEvidence(evidence);
            criminalCaseService.save(criminalCase);

            criminalCase = new CriminalCase();
            criminalCase.setNumber(NumberGenerator.getCaseNumber());
            criminalCase.setType(CaseType.INFRACTION);
            criminalCase.setShortDescription("Littering next to St.George High School.");
            criminalCase.setStatus(CaseStatus.CLOSED);
            criminalCase.setNotes("Really guys? Do you expect me to investigate this? Ok, fine. Got the damn kids.");
            criminalCase.setLeadInvestigator(detective);

            evidence = new Evidence();
            evidence.setStorage(storage);
            evidence.setCriminalCase(criminalCase);
            evidence.setNumber(NumberGenerator.getEvidenceNumber());
            evidence.setItemName("Cigarette butts.");
            evidence.setNotes("FML, really?");
            evidence.setArchived(true);
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.SUBMITTED,"Submitting cigarette butts."));

            criminalCaseService.save(criminalCase);
        });

        personOpt = personService.findByCompleteName("Nancy", "Drew");
        personOpt.ifPresent( person -> {
            Detective detective = createDetective(person, Rank.TRAINEE, false, EmploymentStatus.VACATION);
            detectiveService.save(detective);

            var criminalCase = new CriminalCase();
            criminalCase.setNumber(NumberGenerator.getCaseNumber());
            criminalCase.setType(CaseType.INFRACTION);
            criminalCase.setShortDescription("Parking Violation by John Doe.");
            criminalCase.setStatus(CaseStatus.DISMISSED);
            criminalCase.setNotes("John Doe has been warned.");
            criminalCase.setLeadInvestigator(detective);

            Evidence evidence = new Evidence();
            evidence.setStorage(storage);
            evidence.setCriminalCase(criminalCase);
            evidence.setNumber(NumberGenerator.getEvidenceNumber());
            evidence.setItemName("Photo of the car parked illegally.");
            evidence.setArchived(true);
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.SUBMITTED,"Photo of the car parked illegally submitted into evidence."));

            criminalCaseService.save(criminalCase);
        });

        personOpt = personService.findByCompleteName("Irene", "Adler");
        personOpt.ifPresent( person -> {
            Detective detective = createDetective(person, Rank.INSPECTOR, true, EmploymentStatus.SUSPENDED);
            detectiveService.save(detective);

            CriminalCase criminalCase = new CriminalCase();
            criminalCase.setNumber(NumberGenerator.getCaseNumber());
            criminalCase.setType(CaseType.FELONY);
            criminalCase.setShortDescription("Second-degree felony: Family severely injured during robbery.");
            criminalCase.setStatus(CaseStatus.CLOSED);
            criminalCase.setNotes("This is case terrifying, such violence! Suspects are members of a local gang.");
            criminalCase.setLeadInvestigator(detective);

            Evidence evidence = new Evidence();
            evidence.setStorage(storage);
            evidence.setCriminalCase(criminalCase);
            evidence.setNumber(NumberGenerator.getEvidenceNumber());
            evidence.setItemName("Bloody baseball bat.");
            evidence.setNotes("A bloody baseball bat was confiscated from one of the suspects that was apprehended at the scene.");
            evidence.setArchived(true);
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.SUBMITTED,"Submitting bloody bat as evidence."));
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.RETRIEVED,"Forensic Analysis - look for prints and DNA."));
            evidence.addTrackEntry(createTrackEntry(detective, TrackAction.RETURNED,"Forensic Analysis completed."));

            criminalCase.addEvidence(evidence);
            criminalCaseService.save(criminalCase);
        });
    }

    private void createPersons() {
        var person = new Person();
        person.setUsername("sherlock.holmes");
        person.setFirstName("Sherlock");
        person.setLastName("Holmes");
        person.setPassword("dudu");
        person.setHiringDate(DateProcessor.toDate("1983-08-15 00:20"));
        personService.save(person);

        person = new Person();
        person.setUsername("jackson.brodie");
        person.setFirstName("Jackson");
        person.setLastName("Brodie");
        person.setPassword("bagy");
        person.setHiringDate(DateProcessor.toDate("1983-06-22 00:21"));
        personService.save(person);

        person = new Person();
        person.setUsername("nancy.drew");
        person.setFirstName("Nancy");
        person.setLastName("Drew");
        person.setPassword("dada45");
        person.setHiringDate(DateProcessor.toDate("1990-05-21 00:25"));
        personService.save(person);

        person = new Person();
        person.setUsername("irene.adler");
        person.setFirstName("Irene");
        person.setLastName("Adler");
        person.setPassword("xxxyy");
        person.setHiringDate(DateProcessor.toDate("1987-03-11 00:27"));
        personService.save(person);
    }

    private Detective createDetective(Person person, Rank rank, Boolean armed, EmploymentStatus status){
        var detective = new Detective();
        detective.setPerson(person);
        detective.setBadgeNumber(NumberGenerator.getBadgeNumber());
        detective.setRank(rank);
        detective.setArmed(armed);
        detective.setStatus(status);
        return detective;
    }

    private TrackEntry createTrackEntry(Detective detective, TrackAction action, String reason) {
        var trackEntry = new TrackEntry();
        trackEntry.setDate(LocalDateTime.now());
        trackEntry.setDetective(detective);
        trackEntry.setAction(action);
        trackEntry.setReason(reason);
        return trackEntry;
    }
}
