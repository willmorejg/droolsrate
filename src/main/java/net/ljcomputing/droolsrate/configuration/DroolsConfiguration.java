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

import java.util.Arrays;
import org.drools.io.InputStreamResource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/** Drools Configuration. */
@Configuration
public class DroolsConfiguration {
    /** Kie Services. */
    private static final KieServices kieServices = KieServices.Factory.get();

    /** Drools rules (.drl) directory. */
    @Autowired private Resource[] rulesDirectory;

    /**
     * {@link org.kie.api.runtime.KieContainer KieContainer} bean.
     *
     * @return {@link org.kie.api.runtime.KieContainer KieContainer}
     */
    @Bean
    public KieContainer kieContainer() {
        System.out.println("OK");
        System.out.println("rulesDirectory: " + Arrays.toString(rulesDirectory));
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        for (Resource resource : rulesDirectory) {
            System.out.println("  -->> adding " + resource.toString());
            try {
                kieFileSystem.write(
                        resource.getFilename(), new InputStreamResource(resource.getInputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}
