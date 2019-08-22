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
package com.apress.cems.util;

import java.util.Random;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public final class NumberGenerator {
    private static final Random RAND = new Random();
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    public static String getBadgeNumber() {
        final var sb = new StringBuilder();
        sb.append(randomUppercase()).append(randomUppercase());
        for (int i = 0; i < 6; ++i) {
            sb.append(randomDigit());
        }
        return sb.toString();
    }

    public static String getPassword(){
        final var sb = new StringBuilder();
        for (int i = 0; i < 8; ++i) {
            sb.append(randomCharacter());
        }
        return sb.toString();
    }

    public static String getCaseNumber() {
        final var sb = new StringBuilder();
        sb.append(randomUppercase()).append(randomUppercase());
        for (int i = 0; i < 8; ++i) {
            sb.append(randomDigit());
        }
        return sb.toString();
    }

    public static String getEvidenceNumber(){
        final var sb = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            sb.append(randomUppercase());
        }
        for (int i = 0; i < 16; ++i) {
            sb.append(randomDigit());
        }
        return sb.toString();
    }

    private static Character randomUppercase() {
        return UPPER.charAt(RAND.nextInt(UPPER.length() - 1));
    }

    private static Character randomDigit() {
        return DIGITS.charAt(RAND.nextInt(DIGITS.length() - 1));
    }

    private static Character randomCharacter() {
        final var all = UPPER.concat(UPPER.toLowerCase()).concat(DIGITS);
        return all.charAt(RAND.nextInt(all.length() - 1));
    }

    private NumberGenerator() {
        // prevent initialization fo this class
    }
}
