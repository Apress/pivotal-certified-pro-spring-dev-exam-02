package com.apress.cems.pojos.repos;

import com.apress.cems.dao.CriminalCase;
import com.apress.cems.dao.Evidence;
import com.apress.cems.dao.Storage;

import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface EvidenceRepo extends AbstractRepo<Evidence> {

    Set<Evidence> findByCriminalCase(CriminalCase criminalCase);

    Evidence findByNumber(String evidenceNumber);

    boolean isInStorage(Storage storage);
}
