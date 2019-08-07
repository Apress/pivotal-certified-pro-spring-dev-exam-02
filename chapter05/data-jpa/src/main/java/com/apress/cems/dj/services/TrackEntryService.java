package com.apress.cems.dj.services;

import com.apress.cems.dao.Detective;
import com.apress.cems.dao.Evidence;
import com.apress.cems.dao.TrackEntry;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface TrackEntryService {

    List<TrackEntry> findByDate(LocalDateTime localDate);

    List<TrackEntry> findByEvidence(Evidence evidence);

    List<TrackEntry> findByDetective(Detective detective);

    TrackEntry save(TrackEntry entry);
}
