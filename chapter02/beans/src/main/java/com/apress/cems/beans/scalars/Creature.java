package com.apress.cems.beans.scalars;

import java.time.LocalDate;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public interface Creature {

    LocalDate getBirthDate();

    void setBirthDate(LocalDate birthDate);

    String getName();

    void setName(String name);
}
