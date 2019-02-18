package com.apress.cems.pojos.services;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Evidence;
import com.apress.cems.dao.Storage;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface EvidenceService extends AbstractService<Evidence> {
    Evidence createEvidence(CriminalCase criminalCase, Storage storage, String itemName);
}
