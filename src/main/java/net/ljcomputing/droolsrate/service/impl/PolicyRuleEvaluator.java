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
package net.ljcomputing.droolsrate.service.impl;

import net.ljcomputing.droolsrate.model.RuleResults;
import net.ljcomputing.droolsrate.service.RuleEvaluator;
import net.ljcomputing.insurancexml.domain.Policy;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyRuleEvaluator implements RuleEvaluator<Policy> {
    @Autowired private KieSession kieSession;

    @Override
    public RuleResults evaluate(Policy policy) {
        RuleResults results = new RuleResults();

        kieSession.setGlobal("results", results);
        kieSession.insert(policy);
        kieSession.fireAllRules();
        kieSession.dispose();

        return results;
    }
}
