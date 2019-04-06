package com.apress.cems.services;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Evidence;
import com.apress.cems.dao.Storage;

import java.util.Optional;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface EvidenceService extends AbstractService<Evidence> {
    Evidence createEvidence(CriminalCase criminalCase, Storage storage, String itemName);

    Set<Evidence> findByCriminalCase(CriminalCase criminalCase);

    Optional<Evidence> findByNumber(String evidenceNumber);
}
