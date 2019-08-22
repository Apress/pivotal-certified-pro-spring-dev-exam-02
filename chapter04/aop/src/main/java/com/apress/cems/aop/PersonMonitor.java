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
package com.apress.cems.aop;

import com.apress.cems.aop.service.PersonService;
import com.apress.cems.dao.Person;
import com.apress.cems.ex.UnexpectedException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Aspect
@Component
public class PersonMonitor {
    private static final Logger logger = LoggerFactory.getLogger(PersonMonitor.class);
    private static long findByIdCount = 0;

    //@Before("execution(public * com.apress.cems.repos.*.JdbcPersonRepo+.findById(..))")
    @Before("execution(public * com.apress.cems.repos.*.JdbcPersonRepo+.findById(..)) && within(com.apress.*) ")
    public void beforeFindById(JoinPoint joinPoint) {
        var methodName = joinPoint.getSignature().getName();
        logger.info("[beforeFindById]: ---> Method {} is about to be called", methodName);
    }


    @Before("execution(@com.apress.cems.repos.ApressRepo * com.apress.cems.repos.*.*Repo+.*(..))")
    //@Before("execution(public * com.apress.cems.repos.*.PersonRepo+.findById(..)) && @annotation(com.apress.cems.repos.ApressRepo))")
    //@Before("@annotation(com.apress.cems.repos.ApressRepo)")
    public void beforeFindByIdWithMethodAnnotation(JoinPoint joinPoint) {
        var methodName = joinPoint.getSignature().getName();
        logger.info("[beforeFindByIdWithMethodAnnotation]: ---> Method {} is about to be called", methodName);
    }

    //@Before("execution(* com.apress.cems.*.*PersonRepo+.findBy*(..)) || execution (* com.apress.cems.aop.service.*Service+.findBy*(..)))")
    @Before("com.apress.cems.aop.PointcutContainer.repoFind() || com.apress.cems.aop.PointcutContainer.serviceFind()")
    public void beforeFind(JoinPoint joinPoint) {
        var className = joinPoint.getSignature().getDeclaringTypeName();
        var methodName = joinPoint.getSignature().getName();
        logger.info("[beforeFind]: ---> Method {}.{}  is about to be called", className, methodName);
    }

    @After("com.apress.cems.aop.PointcutContainer.repoFind() || com.apress.cems.aop.PointcutContainer.serviceFind()")
    public void afterFind(JoinPoint joinPoint) {
        ++findByIdCount;
        var methodName = joinPoint.getSignature().getName();
        logger.info("[afterFind]: ---> Method {}  was called {}  times", methodName, findByIdCount);
    }

    private static final String[] SPECIAL_CHARS = new String[]{"$", "#", "&", "%"};

    @Before("com.apress.cems.aop.PointcutContainer.beforeSavePointcut(person,service)")
    public void beforeSave(Person person, PersonService service) {
        logger.info("[beforeSave]: ---> Target object {}", service.getClass());
        person.setFirstName("Sample");
        if (StringUtils.indexOfAny(person.getFirstName(), SPECIAL_CHARS) != -1 ||
                StringUtils.indexOfAny(person.getLastName(), SPECIAL_CHARS) != -1) {
            throw new IllegalArgumentException("Text contains weird characters!");
        }
    }

    @AfterReturning(value = "execution (* com.apress.cems.aop.service.*Service+.save(..))", returning="result")
    public void afterServiceSave(JoinPoint joinPoint, Person result) {
        logger.info("[afterServiceSave]: ---> Target object {}",  joinPoint.getTarget().getClass());
        logger.info("[afterServiceSave]: ---> Was person saved? {}", (result != null));
    }

    @AfterThrowing(value = "execution(public * com.apress.cems.repos.*.JdbcPersonRepo+.update(..))", throwing="e")
    public void afterBadUpdate(JoinPoint joinPoint, Exception e) {
        var className = joinPoint.getSignature().getDeclaringTypeName();
        var methodName = joinPoint.getSignature().getName();
        if(e instanceof IllegalArgumentException) {
            logger.info("[afterBadUpdate]: ---> Update method {}.{} failed because of bad data.", className, methodName);
        } else {
            throw new UnexpectedException(" Ooops!", e);
        }
    }

    @Around("com.apress.cems.aop.PointcutContainer.repoFind() || com.apress.cems.aop.PointcutContainer.serviceFind()")
    public Object aroundFind(ProceedingJoinPoint joinPoint) throws Throwable {
        var methodName = joinPoint.getSignature().getName();
        logger.info("[aroundFind]: ---> Intercepting call of {}", methodName);
        long t1 = System.currentTimeMillis();
        try {
            //put a pause here so we can register an execution time
            Thread.sleep(1000L);
            var obj = joinPoint.proceed();
            return obj != null ? obj : Optional.empty();
        } finally {
            long t2 = System.currentTimeMillis();
            logger.info("[aroundFind]: ---> Execution of {} took {} ", methodName, (t2 - t1) / 1000 + " ms.");
        }
    }

}
