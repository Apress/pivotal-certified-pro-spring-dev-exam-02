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
package com.apress.cems;

import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class BeanManager {

    public static Function<ApplicationContext, String> asHtml = ctx -> {
        var sb = new StringBuilder("<html><body>");

        sb.append("Hello there dear developer, here are the beans you were looking for: <br>");

        //method that returns all the bean names in the context of the application
        Arrays.stream(ctx.getBeanDefinitionNames()).sorted().forEach(
                beanName -> sb.append("<br>").append(beanName)
        );
        sb.append("</body></htm>");
        return sb.toString();
    };
}
