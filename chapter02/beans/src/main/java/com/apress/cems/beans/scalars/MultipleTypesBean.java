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
package com.apress.cems.beans.scalars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Component
public class MultipleTypesBean {
    private int noOne;
    private Integer noTwo;

    private long longOne;
    private Long longTwo;

    private float floatOne;
    private Float floatTwo;

    private double doubleOne;
    private Double doubleTwo;

    private boolean boolOne;
    private Boolean boolTwo;

    private char charOne;
    private Character charTwo;

    private Date date;

    @Autowired void setNoOne(@Value("1") int noOne) {
        this.noOne = noOne;
    }

    @Autowired void setNoTwo(@Value("2")Integer noTwo) {
        this.noTwo = noTwo;
    }

    @Autowired void setFloatOne(@Value("5.0")float floatOne) {
        this.floatOne = floatOne;
    }

    @Autowired void setFloatTwo(@Value("6.0")Float floatTwo) {
        this.floatTwo = floatTwo;
    }

    @Autowired void setDoubleOne(@Value("7.0")double doubleOne) {
        this.doubleOne = doubleOne;
    }

    @Autowired void setDoubleTwo(@Value("8.0")Double doubleTwo) {
        this.doubleTwo = doubleTwo;
    }

    @Autowired void setLongOne(@Value("3")long longOne) {
        this.longOne = longOne;
    }

    @Autowired void setLongTwo(@Value("4")Long longTwo) {
        this.longTwo = longTwo;
    }

    @Autowired void setBoolOne(@Value("true")boolean boolOne) {
        this.boolOne = boolOne;
    }

    @Autowired void setBoolTwo(@Value("false")Boolean boolTwo) {
        this.boolTwo = boolTwo;
    }

    @Autowired void setCharOne(@Value("1")char charOne) {
        this.charOne = charOne;
    }

    @Autowired void setCharTwo(@Value("A")Character charTwo) {
        this.charTwo = charTwo;
    }

    @Autowired void setDate(@Value("1977-10-16 00:23") Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MultipleTypesBean{" +
                "\n noOne=" + noOne +
                "\n, noTwo=" + noTwo +
                "\n, longOne=" + longOne +
                "\n, longTwo=" + longTwo +
                "\n, floatOne=" + floatOne +
                "\n, floatTwo=" + floatTwo +
                "\n, doubleOne=" + doubleOne +
                "\n, doubleTwo=" + doubleTwo +
                "\n, boolOne=" + boolOne +
                "\n, boolTwo=" + boolTwo +
                "\n, charOne=" + charOne +
                "\n, charTwo=" + charTwo +
                "\n, date=" + date +
                '}';
    }
}
