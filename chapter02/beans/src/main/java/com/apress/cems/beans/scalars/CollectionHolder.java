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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Component
public class CollectionHolder {
    private List<SimpleBean> simpleBeanList2;

    private Set<SimpleBean> simpleBeanSet2;

    private Map<String, SimpleBean> simpleBeanMap2;

    @Autowired @Qualifier("simpleBeanList2")
    public void setSimpleBeanList2(List<SimpleBean> simpleBeanList2) {
        this.simpleBeanList2 = simpleBeanList2;
    }

    @Autowired @Qualifier("simpleBeanSet2")
    public void setSimpleBeanSet2(Set<SimpleBean> simpleBeanSet2) {
        this.simpleBeanSet2 = simpleBeanSet2;
    }

    @Autowired @Qualifier("simpleBeanMap2")
    public void setSimpleBeanMap2(Map<String, SimpleBean> simpleBeanMap2) {
        this.simpleBeanMap2 = simpleBeanMap2;
    }

    /**
     * This method was implemented just to verify the collections injected into beans of this type
     * @return
     */
    @Override
    public String toString() {
        return "CollectionHolder{" +
                "simpleBeanList=" + simpleBeanList2.size() +
                ", simpleBeanSet=" + simpleBeanSet2.size() +
                ", simpleBeanMap=" + simpleBeanMap2.size() +
                '}';
    }
}
