package com.apress.cems.repos;

import com.apress.cems.dao.Person;

import java.util.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface PersonRepo extends AbstractRepo<Person> {
    Optional<Person> findByUsername(String username);

    default Set<Person> findAllByUsernamePart(String part){
        return Set.of();
    }

    Optional<Person> findByCompleteName(String firstName, String lastName);

    Set<Person> findAll();

    int updatePassword(Long personId, String newPass);

    long count();

    default Map<String, Object> findByIdAsMap(Long id) {
        return new HashMap<>();
    }

    default List<Map<String, Object>> findAllAsMaps() {
        return new ArrayList<>();
    }

    default void htmlAllByName(String name) {}

    default int createPerson(Long userId, String username, String firstName, String lastName, String password) {
        return 0;
    }

    default List<String> findAllUsernames() { return List.of();}

    default List<Person> findAllByLastName(String firstName){return List.of();};
}
