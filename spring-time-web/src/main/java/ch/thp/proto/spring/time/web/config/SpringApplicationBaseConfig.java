/*
 * Copyright Error: on line 4, column 29 in Templates/Licenses/license-apache20.txt
 The string doesn't match the expected date/time format. The string to parse was: "02.05.2014". The expected format was: "MMM d, yyyy". caleb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.thp.proto.spring.time.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Base spring configuration. Will load all other configurations
 *
 * @author thpeng
 */
@Configuration
//@ComponentScan(basePackages = "ch.thp.proto",excludeFilters={@ComponentScan.Filter(value = Controller.class), @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE ,value = SpringMVCRestConfig.class)})
@ComponentScan(basePackages = "ch.thp.proto")
public class SpringApplicationBaseConfig {

}
