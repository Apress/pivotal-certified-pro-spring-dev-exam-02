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

import com.apress.cems.web.config.WebInitializer;
import org.eclipse.jetty.annotations.ClassInheritanceHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.annotations.AnnotationConfiguration.ClassInheritanceMap;

import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */

//@Service
class JettyServer {
    private Server server;

    private String name;

    JettyServer(String name) {
        this.name = name;
    }

    //@PostConstruct
    void start() throws Exception {
        server = new Server();

        Handler contextHandler = createServletContextHandler(name);
        server.setHandler(contextHandler);

        server.setAttribute("org.mortbay.jetty.Request.maxFormContentSize", 0);
        server.setStopAtShutdown(true);

        ServerConnector connector = new ServerConnector(server);
        connector.setHost("0.0.0.0");
        connector.setPort(8080);
        connector.setIdleTimeout(30000);
        server.setConnectors(new Connector[] { connector });

        // Start the server
        server.start();
        server.join();
    }

    //@PreDestroy
    void stop() throws Exception {
        server.stop();
    }

    private ServletContextHandler createServletContextHandler(String name) throws Exception {
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setErrorHandler(null);
        webAppContext.setContextPath("/" + name);

        webAppContext.setAttribute(AnnotationConfiguration.CLASS_INHERITANCE_MAP, createClassMap());

        //webAppContext.addServlet(new ServletHolder(new DispatcherServlet(getContext())), "/*");
        //webAppContext.addEventListener(new ContextLoaderListener(getContext()));
        webAppContext.setBaseResource(Resource.newClassPathResource("webapp"));

        webAppContext.setParentLoaderPriority(false);
        webAppContext.setConfigurations(new Configuration[]{new AnnotationConfiguration()});

        return webAppContext;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.apress.cems.web.config");
        return context;
    }


    private ClassInheritanceMap createClassMap() {
        ClassInheritanceMap classMap = new ClassInheritanceMap();
        Set<String> impl = ConcurrentHashMap.newKeySet();
        impl.add(WebInitializer.class.getName());
        classMap.put(WebApplicationInitializer.class.getName(), impl);
        return classMap;
    }
}
