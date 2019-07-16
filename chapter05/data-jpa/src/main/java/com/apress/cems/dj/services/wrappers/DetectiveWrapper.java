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
package com.apress.cems.dj.services.wrappers;

import com.apress.cems.dao.Detective;
import com.apress.cems.util.CaseStatus;
import com.apress.cems.util.EmploymentStatus;
import com.apress.cems.util.Rank;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class DetectiveWrapper {

    private Long id;
    private String name;
    private String badgeNumber;
    private Rank rank;
    private Boolean armed;
    private EmploymentStatus status;
    private Long casesResolved;
    private Long casesInProgress;

    private boolean empty = true;

    public DetectiveWrapper() {
    }

    public DetectiveWrapper(Detective detective) {
        this.id = detective.getId();
        this.name = detective.getPerson().getFirstName().concat( detective.getPerson().getLastName());
        this.badgeNumber = detective.getBadgeNumber();
        this.rank = detective.getRank();
        this.armed = detective.getArmed();
        this.status = detective.getStatus();
        this.casesResolved = detective.getCriminalCases().stream().filter(cc -> cc.getStatus() == CaseStatus.CLOSED).count();
        this.casesInProgress = detective.getCriminalCases().stream().filter(cc -> Set.of(CaseStatus.UNDER_INVESTIGATION, CaseStatus.IN_COURT, CaseStatus.COLD).contains(cc.getStatus())).count();
        this.empty = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber = badgeNumber;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Boolean getArmed() {
        return armed;
    }

    public void setArmed(Boolean armed) {
        this.armed = armed;
    }

    public EmploymentStatus getStatus() {
        return status;
    }

    public void setStatus(EmploymentStatus status) {
        this.status = status;
    }

    public Long getCasesResolved() {
        return casesResolved;
    }

    public Long getCasesInProgress() {
        return casesInProgress;
    }

    public boolean isEmpty() {
        return empty;
    }
}
