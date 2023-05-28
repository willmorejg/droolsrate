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

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import net.ljcomputing.droolsrate.listener.DebugAgendaEventListenerImpl;
import net.ljcomputing.droolsrate.listener.RuleRuntimeEventListenerImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
@Slf4j
public class DroolsConfiguration {
    /** Classpath location of Drools (.drl) files. */
    @Value("${application.drlFilesLocation}")
    private String drlFilesLocation;

    /** Classpath of drl files. */
    @Value("${application.drlClasspath}")
    private String drlClasspath;

    @Bean
    public KieFileSystem kieFileSystem() throws IOException {
        final KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();

        for (final Resource file : getRuleFiles()) {
            log.info("  adding Drools file -> {}", file);
            kieFileSystem.write(
                    ResourceFactory.newClassPathResource(
                            drlClasspath + file.getFilename(), "UTF-8"));
        }

        return kieFileSystem;
    }

    private Resource[] getRuleFiles() throws IOException {
        final ResourcePatternResolver resourcePatternResolver =
                new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources(drlFilesLocation);
    }

    @Bean
    public KieContainer kieContainer() throws IOException {
        final KieRepository kieRepository = getKieServices().getRepository();

        kieRepository.addKieModule(
                new KieModule() {
                    public ReleaseId getReleaseId() {
                        return kieRepository.getDefaultReleaseId();
                    }
                });

        final KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();

        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
    }

    @Bean
    public KieSession kieSession() throws IOException {
        KieSession kieSession = kieContainer().newKieSession();

        kieSession.addEventListener(new DebugAgendaEventListenerImpl());
        kieSession.addEventListener(new RuleRuntimeEventListenerImpl());

        return kieSession;
    }

    @Bean
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }
}
