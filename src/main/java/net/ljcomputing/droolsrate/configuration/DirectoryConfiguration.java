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
import java.nio.file.Paths;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Directory configurations. */
@Configuration
// @Slf4j
public class DirectoryConfiguration {
    /**
     * Output directory bean.
     *
     * @return {@link java.nio.file.Path Path} of output directory
     */
    @Bean
    public Path outputDirectory() {
        String homeDir = System.getProperty("user.home");
        Path outPath = Paths.get(homeDir, "out");
        return outPath;
    }
}
