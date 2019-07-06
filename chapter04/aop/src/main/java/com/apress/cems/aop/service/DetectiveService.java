package com.apress.cems.aop.service;

import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Person;
import com.apress.cems.util.Rank;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface DetectiveService {

    Set<Detective> findAll();

    Optional<Detective> findById(Long id);

    Detective createDetective(Person person, Rank rank);

    Optional<Detective> findByBadgeNumber(String badgeNumber);

    Set<Detective> findByRank(Rank rank);
}
