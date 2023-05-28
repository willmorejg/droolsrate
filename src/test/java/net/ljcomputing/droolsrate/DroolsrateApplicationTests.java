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

import java.io.StringWriter;
import java.time.LocalDate;
import javax.xml.transform.stream.StreamResult;
import net.ljcomputing.droolsrate.configuration.DroolsConfiguration;
import net.ljcomputing.droolsrate.model.RuleResults;
import net.ljcomputing.droolsrate.service.RuleEvaluator;
import net.ljcomputing.insurancexml.domain.Address;
import net.ljcomputing.insurancexml.domain.AddressType;
import net.ljcomputing.insurancexml.domain.Addresses;
import net.ljcomputing.insurancexml.domain.Coverage;
import net.ljcomputing.insurancexml.domain.Driver;
import net.ljcomputing.insurancexml.domain.Drivers;
import net.ljcomputing.insurancexml.domain.Insured;
import net.ljcomputing.insurancexml.domain.Policy;
import net.ljcomputing.insurancexml.domain.Risk;
import net.ljcomputing.insurancexml.domain.RiskType;
import net.ljcomputing.insurancexml.domain.Risks;
import net.ljcomputing.insurancexml.domain.USState;
import net.ljcomputing.insurancexml.domain.Vehicle;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/** Drools rate application tests. */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class DroolsrateApplicationTests {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(DroolsrateApplicationTests.class);

    /** Drools configuration. */
    @Autowired private DroolsConfiguration droolsConfiguration;

    /** Kie container. */
    @Autowired private KieContainer kieContainer;

    @Autowired private Jaxb2Marshaller policyMarshaller;

    @Autowired private RuleEvaluator<Policy> policyRuleEvaluator;

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

    private Policy getPolicy() {
        Coverage coverageA = new Coverage();
        coverageA.setName("Coverage A");
        coverageA.setCode("CVRA");
        coverageA.setAmount("100000");

        Coverage coverageB = new Coverage();
        coverageB.setName("Coverage B");
        coverageB.setCode("CVRB");
        coverageB.setAmount("50000");

        Coverage coverageC = new Coverage();
        coverageC.setName("Coverage C");
        coverageC.setCode("CVRC");
        coverageC.setAmount("25000");

        Address physicalAddr = new Address();
        physicalAddr.setStreet1("1 Salem Road");
        physicalAddr.setCity("Smithville");
        physicalAddr.setState(USState.NJ);
        physicalAddr.setZipCode("09190");

        Address billingAddr = new Address();
        billingAddr.setType(AddressType.BILLING);
        billingAddr.setStreet1("1 Salem Road");
        billingAddr.setCity("Smithville");
        billingAddr.setState(USState.NJ);
        billingAddr.setZipCode("09190");

        Addresses addresses = new Addresses();
        addresses.getAddresses().add(physicalAddr);
        addresses.getAddresses().add(billingAddr);

        Risk dpRisk = new Risk();
        dpRisk.setName("DP Risk");
        dpRisk.setRiskType(RiskType.DWELLING_PROPERTY);
        dpRisk.setAddress(physicalAddr);

        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Subaru");
        vehicle.setModel("Outback");
        vehicle.setYear(2000);
        vehicle.setVin("5GAKRDED0CJ396612");

        Driver driver = new Driver();
        driver.setGivenName("James");
        driver.setSurname("Willmore");
        driver.setBirthdate(LocalDate.of(1968, 1, 22));
        driver.setDlNumber("DL123 45678 90000");
        driver.setDlState(USState.NJ);

        Drivers drivers = new Drivers();
        drivers.getDrivers().add(driver);

        Risk paRisk = new Risk();
        paRisk.setName("PA Risk");
        paRisk.setRiskType(RiskType.PERSONAL_AUTO);
        paRisk.setDrivers(drivers);
        paRisk.setVehicle(vehicle);
        paRisk.getCoverages().add(coverageA);

        Risks risks = new Risks();
        risks.getRisks().add(paRisk);
        risks.getRisks().add(dpRisk);

        Insured insured = new Insured();
        insured.setGivenName("James");
        insured.setSurname("Willmore");
        insured.setBirthdate(LocalDate.of((2023 - 17), 1, 22));
        insured.setAddresses(addresses);

        Policy policy = new Policy();
        policy.setInsured(insured);
        policy.setRisks(risks);
        policy.getCoverages().add(coverageA);
        policy.getCoverages().add(coverageB);
        policy.getCoverages().add(coverageC);

        return policy;
    }

    /** Test basic configuration and starting of Spring container. */
    @Test
    @Order(1)
    void contextLoads() {
        final String testName = "contextLoads";
        logStart(testName);
        assertTrue(true);
        logEnd(testName);
    }

    /** Test Drools configuration. */
    @Test
    @Order(2)
    void testDroolsConfiguration() {
        final String testName = "testDroolsConfiguration";
        logStart(testName);
        assertNotNull(droolsConfiguration);
        logEnd(testName);
    }

    /** Test Kie container. */
    @Test
    @Order(3)
    void testKieContainer() {
        final String testName = "testKieContainer";
        logStart(testName);
        assertNotNull(kieContainer);
        logEnd(testName);
    }

    /** Test Policy rule evaluator. */
    @Test
    @Order(10)
    void testPolicyRuleEvaluator() {
        final String testName = "testPolicyRuleEvaluator";
        logStart(testName);
        assertNotNull(policyRuleEvaluator);
        StringWriter sw = new StringWriter();
        Policy policy = getPolicy();
        policyMarshaller.marshal(policy, new StreamResult(sw));
        log.debug("policy: {}", sw.toString());
        RuleResults results = policyRuleEvaluator.evaluate(policy);
        log.debug("results: {}", results);
        logEnd(testName);
    }
}
