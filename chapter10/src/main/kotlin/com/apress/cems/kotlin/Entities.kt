package com.apress.cems.kotlin

import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

@MappedSuperclass
open class AbstractEntity(@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(updatable = false) var id: Long? = null,
                          @Version var version: Int = 0,
                          @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT) val createdAt: LocalDateTime = LocalDateTime.now(),
                          @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT) var modifiedAt: LocalDateTime = LocalDateTime.now())
    : Serializable

@Entity
data class Person(@Size(min = 3, max = 30) @NotEmpty @Column(unique = true) var firstName: String? = null,
                  @Size(min = 3, max = 30) @NotEmpty @Column(unique = true) var lastName:String? = null,
                  @Size(min = 3, max = 60) @NotEmpty @Column(unique = true) var username: String? = null,
                  @Size(min = 4, max = 50) @NotEmpty var password: String? = null,
                  @DateTimeFormat(pattern = DateProcessor.DATE_FORMAT) var hiringDate: LocalDateTime = LocalDateTime.now())
    : AbstractEntity()

@Entity
data class Detective(@NotNull @OneToOne val person: Person,
                     @NotEmpty @Column(unique = true, nullable = false) var badgeNumber:String? = null,
                     @NotNull @Enumerated(EnumType.STRING) var rank: Rank,
                     var armed: Boolean  = false,
                     @NotNull @Enumerated(EnumType.STRING) var status: EmploymentStatus = EmploymentStatus.ACTIVE)
    : AbstractEntity()

@Entity
data class CriminalCase(@NotEmpty @Column(name = "case_number", unique = true, nullable = false) var number: String? = null,
                        @NotNull @Column(name = "case_type") @Enumerated(EnumType.STRING) var type : CaseType = CaseType.UNCATEGORIZED,
                        @NotEmpty @Column(name = "short_description") var shortDescription: String? = null,
                        var detailedDescription: String? = null,
                        @NotEmpty @Enumerated(EnumType.STRING) var status: CaseStatus = CaseStatus.SUBMITTED,
                        var notes: String? = null,
                        @ManyToOne @JoinColumn(name = "LEAD_INVESTIGATOR", nullable = false) var leadInvestigator: Detective? = null,
                        @ManyToMany @JoinTable(name = "working_detective_case",
                                joinColumns = [JoinColumn(name = "case_id", referencedColumnName = "id")],
                                inverseJoinColumns = [JoinColumn(name = "detective_id", referencedColumnName = "id")])
                        val assigned: MutableSet<Detective> = mutableSetOf())
    : AbstractEntity()