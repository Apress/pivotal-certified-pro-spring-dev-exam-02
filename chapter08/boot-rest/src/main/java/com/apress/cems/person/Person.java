/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.apress.cems.person;

import com.apress.cems.util.DateProcessor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Entity
@SequenceGenerator(name = "seqGen", allocationSize = 1)
@NamedQueries({
        @NamedQuery(name = Person.FIND_BY_COMPLETE_NAME, query = "from Person p where p.firstName=:fn and p.lastName=:ln"),
        @NamedQuery(name = Person.FIND_BY_LAST_NAME, query = "from Person p where p.lastName= ?1")
})
public class Person {
    static final String FIND_BY_COMPLETE_NAME = "findByCompleteName";
    static final String FIND_BY_LAST_NAME = "findAllByLastName";

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 4, max = 50)
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    private LocalDateTime hiringDate;

    @Transient
    private String newPassword;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    protected Long id;

    @Version
    protected int version;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    protected LocalDateTime createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT)
    protected LocalDateTime modifiedAt;


    public Person() {
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDateTime hiringDate) {
        this.hiringDate = hiringDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        if (!Objects.equals(id, person.id)) return false;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(hiringDate, person.hiringDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, hiringDate);
    }

    @Override
    public String toString() {
        return String.format("Person[username='%s', firstName='%s', lastName='%s', hiringDate='%s']\n",
                username, firstName, lastName, hiringDate.toString());

    }

    /**
     * Returns the entity identifier. This identifier is unique per entity. It is used by persistence frameworks used in a project,
     * and although is public, it should not be used by application code.
     * This identifier is mapped by ORM (Object Relational Mapper) to the database primary key of the Person record to which
     * the entity instance is mapped.
     *
     * @return the unique entity identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the entity identifier. This identifier is unique per entity.  Is is used by persistence frameworks
     * and although is public, it should never be set by application code.
     *
     * @param id the unique entity identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
