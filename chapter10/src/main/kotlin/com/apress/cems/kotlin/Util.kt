package com.apress.cems.kotlin

import java.lang.RuntimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

object DateProcessor {
    const val DATE_FORMAT: String = "yyyy-MM-dd HH:mm"
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)

    fun toDate (date: String) : LocalDateTime {
        return LocalDateTime.parse(date,formatter)
    }

    fun toString(date: LocalDateTime):String {
        return date.format(formatter)
    }
}

object NumberGenerator {
    val ClosedRange<Int>.random: Int get() = Random.nextInt((endInclusive + 1) - start) +  start
    const val UPPER: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val DIGITS: String = "0123456789"

    fun getBadgeNumber():String {
        val sb : StringBuilder = java.lang.StringBuilder()
        sb.append(randomUppercase()).append(randomUppercase())
        for (i in 0..6) sb.append(randomDigit())
        return sb.toString()
    }

    fun randomUppercase() : Char {
       return UPPER[(0 until UPPER.length).random]
    }

    fun randomDigit() : Char {
        return UPPER[(0 until DIGITS.length).random]
    }
}

//converts a Java Optional into a Kotlin Nullable
//fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)

enum class Rank(val code: Int){
    TRAINEE(1),
    JUNIOR(2),
    SENIOR(3),
    INSPECTOR(4),
    CHIEF_INSPECTOR(5)
}

enum class EmploymentStatus {
    ACTIVE,
    SUSPENDED,
    VACATION,
    UNDER_INVESTIGATION,
    RETIRED
}

internal enum class FieldGroup {
    FIRSTNAME,
    LASTNAME,
    USERNAME,
    HIREDIN;

    companion object {
        fun getField(field: String): FieldGroup {
            return valueOf(field.toUpperCase())
        }
    }
}

enum class CaseStatus {
    SUBMITTED,
    UNDER_INVESTIGATION,
    IN_COURT,
    CLOSED,
    DISMISSED,
    COLD
}

enum class CaseType {
    UNCATEGORIZED,
    INFRACTION,
    MISDEMEANOR,
    FELONY
}

class InvalidCriteriaException(var fieldName: String = "", var messageKey: String = "") : Exception()