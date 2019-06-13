package com.apress.cems.aop.service;

import com.apress.cems.dao.Storage;

import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface StorageService {
    Optional<Storage> findByName(String name);

    Optional<Storage> findByLocation(String location);

    void save(Storage storage);

    void saveEvidenceSet(Storage storage);

    void delete(Storage entity);

    int deleteById(Long entityId);

    Optional<Storage> findById(Long entityId);

}
