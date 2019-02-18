package com.apress.cems.pojos.services;

import com.apress.cems.dao.Storage;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface StorageService extends AbstractService<Storage> {

    Storage createStorage(String name, String location);
}
