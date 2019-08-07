package com.apress.cems.beans.scalars;

import java.time.LocalDateTime;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface Creature {

    LocalDateTime getBirthDate();

    void setBirthDate(LocalDateTime birthDate);

    String getName();

    void setName(String name);
}
