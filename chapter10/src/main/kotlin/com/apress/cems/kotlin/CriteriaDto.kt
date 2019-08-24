package com.apress.cems.kotlin

import java.io.Serializable
import javax.validation.constraints.NotEmpty

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
class CriteriaDto( @NotEmpty var fieldName: String = "",
                            @NotEmpty var fieldValue: String = "",
                            var exactMatch: Boolean =  false) : Serializable {

    /**
     * Field needed just to communicate a message of no results found.
     */
    var noResults: String? = null

    val isEmpty: Boolean get() = fieldName.isEmpty() && fieldValue.isEmpty()
}

