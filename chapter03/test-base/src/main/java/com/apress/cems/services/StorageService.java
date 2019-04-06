package com.apress.cems.services;

import com.apress.cems.dao.Storage;

import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface StorageService extends AbstractService<Storage> {

    Storage createStorage(String name, String location);

    Optional<Storage> findByName(String name);

    Optional<Storage> findByLocation(String location);
}
