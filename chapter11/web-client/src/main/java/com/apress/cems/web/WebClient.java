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
package com.apress.cems.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Controller
@SpringBootApplication
@EnableEurekaClient
public class WebClient {

    private static Logger logger = LoggerFactory.getLogger(WebClient.class);

    public static void main(String... args) throws IOException {
        // Look for configuration in  web-client.properties or web-client.yml
        System.setProperty("spring.config.name", "web-client");
        var ctx = SpringApplication.run(WebClient.class, args);
        assert (ctx != null);
        logger.info("Started ...");
        System.in.read();
        ctx.close();
    }

    @Bean @LoadBalanced RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    LoadBalancerClient loadBalancer;

    @GetMapping(value = {"/","/home"})
    public String home(Model model) {
        var sb = new StringBuilder();
        getUri(sb, "persons-service");
        getUri(sb, "detectives-service");
        model.addAttribute("message", sb.toString());
        return "home";
    }

    private void getUri(StringBuilder sb, String s) {
        URI uri =  null;
        try {
            var instance = loadBalancer.choose(s);
            uri = instance.getUri();
            logger.debug("Resolved serviceId '{}' to URL '{}'.", s, uri);
            sb.append("Found microservice: ").append(uri.toString()).append("; ");
        } catch (RuntimeException e) {
            logger.warn("Failed to resolve serviceId '{}'. Fallback to URL '{}'.", s, uri);
            sb.append("Not Found microservice ").append(s).append("; ");
        }
    }
}
