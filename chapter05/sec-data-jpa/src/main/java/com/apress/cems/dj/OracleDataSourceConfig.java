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
package com.apress.cems.dj;

import com.apress.cems.ex.ConfigurationException;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
//@Configuration
public class OracleDataSourceConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", connectionProperties().getProperty("dialect"));
        hibernateProp.put("hibernate.hbm2ddl.auto", connectionProperties().getProperty("hbm2ddl"));

        hibernateProp.put("hibernate.format_sql", true);
        hibernateProp.put("hibernate.use_sql_comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        return hibernateProp;
    }

    @Bean("connectionProperties")
    Properties connectionProperties(){
        try {
            return PropertiesLoaderUtils.loadProperties(
                    new ClassPathResource("prod-database.properties"));
        } catch (IOException e) {
            throw new ConfigurationException("Could not retrieve connection properties!", e);
        }
    }

    @Bean
    public DataSource dataSource() {
        try {
            final Properties props = connectionProperties();
            OracleConnectionPoolDataSource ods = new OracleConnectionPoolDataSource();
            ods.setNetworkProtocol("tcp");
            ods.setDriverType(props.getProperty("driverType"));
            ods.setServerName(props.getProperty("serverName"));
            ods.setDatabaseName(props.getProperty("serviceName"));
            ods.setPortNumber(Integer.parseInt(props.getProperty("port")));
            ods.setUser(props.getProperty("user"));
            ods.setPassword(props.getProperty("password"));
            return ods;
        } catch (SQLException e) {
            throw new ConfigurationException("Could not configure Oracle database!", e);
        }
    }
}
