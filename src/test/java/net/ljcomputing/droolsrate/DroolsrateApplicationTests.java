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
package net.ljcomputing.droolsrate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.ljcomputing.droolsrate.configuration.DroolsConfiguration;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/** Drools rate application tests. */
@SpringBootTest
class DroolsrateApplicationTests {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(DroolsrateApplicationTests.class);

    /** Drools configuration. */
    @Autowired private DroolsConfiguration droolsConfiguration;

    /** Kie container. */
    @Autowired private KieContainer kieContainer;

    /**
     * Log start of test.
     *
     * @param testName
     */
    private void logStart(final String testName) {
        logMethodState(testName, true);
    }

    /**
     * Log end of test.
     *
     * @param testName
     */
    private void logEnd(final String testName) {
        logMethodState(testName, false);
    }

    /**
     * Log method start / end.
     *
     * @param testName
     * @param start
     */
    private void logMethodState(final String testName, final boolean start) {
        String symbols = "===============";
        String state = start ? "START" : " END ";
        log.info("{} {} - {} {}", symbols, testName, state, symbols);
    }

    /** Test basic configuration and starting of Spring container. */
    @Test
    void contextLoads() {
        final String testName = "contextLoads";
        logStart(testName);
        assertTrue(true);
        logEnd(testName);
    }

    /** Test Drools configuration. */
    @Test
    void testDroolsConfiguration() {
        final String testName = "testDroolsConfiguration";
        logStart(testName);
        assertNotNull(droolsConfiguration);
        logEnd(testName);
    }

    /** Test Kie container. */
    @Test
    void testKieContainer() {
        final String testName = "testKieContainer";
        logStart(testName);
        assertNotNull(kieContainer);
        logEnd(testName);
    }
}
