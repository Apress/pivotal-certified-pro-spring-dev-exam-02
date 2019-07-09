package com.apress.cems.dj.repos;

import com.apress.cems.dao.CriminalCase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface CriminalCaseRepo extends JpaRepository<CriminalCase, Long> {
}
