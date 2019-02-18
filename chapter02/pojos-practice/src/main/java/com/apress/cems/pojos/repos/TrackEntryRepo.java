package com.apress.cems.pojos.repos;

import com.apress.cems.dao.TrackEntry;
import com.apress.cems.util.TrackAction;

import java.util.Date;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface TrackEntryRepo extends AbstractRepo<TrackEntry> {

    Set<TrackEntry> findByDetectiveId(Long detectiveId);
    Set<TrackEntry> findByEvidenceId(Long evidenceId);
    Set<TrackEntry> findByDate(Date date);
    Set<TrackEntry> findByDateAndAction(Date date, TrackAction action);

}
