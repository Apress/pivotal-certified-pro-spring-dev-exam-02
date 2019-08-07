package com.apress.cems.dj.repos;

import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Evidence;
import com.apress.cems.dao.TrackEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface TrackEntryRepo extends JpaRepository<TrackEntry, Long> {
    List<TrackEntry> findByDate(LocalDateTime localDate);

    List<TrackEntry> findByEvidence(Evidence evidence);

    List<TrackEntry> findByDetective(Detective detective);
}
