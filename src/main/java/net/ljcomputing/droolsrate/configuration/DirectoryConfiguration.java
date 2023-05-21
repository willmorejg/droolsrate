/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.

James G Willmore - LJ Computing - (C) 2023
*/
package net.ljcomputing.droolsrate.configuration;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/** Directory configurations. */
@Configuration
public class DirectoryConfiguration {
    /** Classpath location of Drools (.drl) files. */
    @Value("${application.drlFilesLocation}")
    private String drlFilesLocation;

    /**
     * Output directory bean.
     *
     * @return {@link java.nio.file.Path Path} of output directory
     */
    @Bean
    public Path outputDirectory() {
        String homeDir = System.getProperty("user.home");
        Path outPath = Path.of(homeDir, "out");
        return outPath;
    }

    /**
     * The location of the Drools rules (.drl) files.
     * 
     * @return
     */
    @Bean
    public String drlFilesLocation() {
        return drlFilesLocation;
    }

    /**
     * Drools rule (.drl) files as {@link org.springframework.core.io.Resource Resources}.
     *
     * @return {@link org.springframework.core.io.Resource Resource} array of rule (.drl) files
     */
    @Bean
    public Resource[] rulesDirectory() {
        Resource[] result = null;

        try {
            ClassLoader cl =
                    Thread.currentThread().getContextClassLoader().getClass().getClassLoader();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
            System.out.println("drlFilesLocation: " + drlFilesLocation());
            result = resolver.getResources(drlFilesLocation());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
