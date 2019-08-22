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

import com.apress.cems.beans.ci.SimpleBean;
import com.apress.cems.beans.ci.SimpleBeanImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;

import java.util.*;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = {"com.apress.cems.beans.scalars"} )
public class AppConvertersCfg {

    @Autowired StringToLocalDate stringToLocalDateConverter;

    @Autowired StringToDate stringToDate;

    @Bean
    ConversionService conversionService(ConversionServiceFactoryBean factory){
        return factory.getObject();
    }

    @Bean
    ConversionServiceFactoryBean conversionServiceFactoryBean() {
        var conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        conversionServiceFactoryBean.setConverters(Set.of(stringToLocalDateConverter, stringToDate));
        return conversionServiceFactoryBean;
    }

    @Bean
    List<SimpleBean> simpleBeanList(){
        return new ArrayList<>();
    }

    @Bean
    Set<SimpleBean> simpleBeanSet(){
        return new HashSet<>();
    }

    @Bean
    Map<String, SimpleBean> simpleBeanMap(){
        return new HashMap<>();
    }

    @Bean
    SimpleBean simpleBean(){
        return new SimpleBeanImpl();
    }

    @Bean
    List<SimpleBean> simpleBeanList2(){
        return List.of(simpleBean());
    }

    @Bean
    Set<SimpleBean>  simpleBeanSet2(){
        return Set.of(simpleBean());
    }

    @Bean
    Map<String, SimpleBean> simpleBeanMap2(){
        return Map.of("simpleBean", simpleBean());
    }
}
