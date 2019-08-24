package com.apress.cems.kotlin

import javax.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

val logger: Logger =    LoggerFactory.getLogger(Initializer::class.java)

@Service
class Initializer(private val personRepo: PersonRepo, private val detectiveRepo: DetectiveRepo) {

    @PostConstruct
    fun init() {
        logger.info(" -->> Starting database initialization...")
        if (personRepo.findAll().isEmpty()) {
            createPersons()
        }
        if (detectiveRepo.findAll().isEmpty()) {
            createDetectives()
        }
        logger.info(" -->> Database initialization finished.")
    }


    fun createPersons(){
        personRepo.save(Person("sherlock", "holmes", "sherlock.holmes", "buuu", DateProcessor.toDate("1983-08-15 00:23")))
        personRepo.save(Person("jackson", "brodie", "jackson.brodie", "12er", DateProcessor.toDate("1983-06-22 00:24")))
        personRepo.save(Person("joan", "watson", "joan.watson", "w34e", DateProcessor.toDate("1990-05-23 00:24")))
        personRepo.save(Person("irene", "adler", "irene.adler", "cdf5", DateProcessor.toDate("1987-02-11 00:26")))
    }

    fun createDetectives() {
        personRepo.findById(1L).ifPresent { p ->
            detectiveRepo.save(Detective(p, NumberGenerator.getBadgeNumber(), Rank.INSPECTOR, true, EmploymentStatus.ACTIVE))
        }

        personRepo.findById(2L).ifPresent { p ->
            detectiveRepo.save(Detective(p, NumberGenerator.getBadgeNumber(), Rank.SENIOR, true, EmploymentStatus.ACTIVE))
        }

        personRepo.findById(3L).ifPresent { p ->
            detectiveRepo.save(Detective(p, NumberGenerator.getBadgeNumber(), Rank.TRAINEE, true, EmploymentStatus.VACATION))
        }

        personRepo.findById(4L).ifPresent { p ->
            detectiveRepo.save(Detective(p, NumberGenerator.getBadgeNumber(), Rank.INSPECTOR, true, EmploymentStatus.SUSPENDED))
        }
    }
}