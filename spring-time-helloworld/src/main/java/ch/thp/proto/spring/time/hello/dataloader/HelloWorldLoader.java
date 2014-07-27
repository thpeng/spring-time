/*
 * Copyright 2014 thpeng.
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
package ch.thp.proto.spring.time.hello.dataloader;

import ch.thp.proto.spring.time.hello.domain.HelloWorld;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ch.thp.proto.spring.time.infra.dataloader.DataLoader;

/**
 *
 * @author thierry
 */
@Component
@Lazy(false)
public class HelloWorldLoader implements DataLoader {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void load() {
        HelloWorld entityOne = new HelloWorld("one", LocalDate.now());
        HelloWorld entityTwo = new HelloWorld("two", LocalDate.now());
        em.persist(entityOne);
        em.persist(entityTwo);
    }

}
