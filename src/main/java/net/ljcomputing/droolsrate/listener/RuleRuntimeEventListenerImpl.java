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
package net.ljcomputing.droolsrate.listener;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

@Slf4j
public class RuleRuntimeEventListenerImpl implements RuleRuntimeEventListener {
    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        log.debug("objectInserted: {}", event);
    }

    @Override
    public void objectUpdated(ObjectUpdatedEvent event) {
        log.debug("objectUpdated: {}", event);
    }

    @Override
    public void objectDeleted(ObjectDeletedEvent event) {
        log.debug("objectDeleted: {}", event);
    }
}
