package com.apress.cems.util;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public enum Rank {
    TRAINEE(1),
    JUNIOR(2),
    SENIOR(3),
    INSPECTOR(4),
    CHIEF_INSPECTOR(5);

    private int code;

    Rank(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
