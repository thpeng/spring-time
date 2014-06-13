/*
 * Copyright 2014 caleb.
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
package ch.thp.proto.spring.time.infra.e2e;

import java.time.LocalDate;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author thierry
 */
@Component
public class E2ELoader {

    
    @Transactional
    public void populateDatabase() {
        E2ETestEntity entityOne = new E2ETestEntity("trololo", LocalDate.now());
    }

}
