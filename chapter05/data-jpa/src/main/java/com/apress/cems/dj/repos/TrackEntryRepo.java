package com.apress.cems.dj.repos;

import com.apress.cems.dao.TrackEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface TrackEntryRepo extends JpaRepository<TrackEntry, Long> {
}
