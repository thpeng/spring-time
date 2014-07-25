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

package ch.thp.proto.spring.time.user.dataloader;

import ch.thp.proto.spring.time.infra.dataloader.DataLoader;
import ch.thp.proto.spring.time.user.domain.User;
import ch.thp.proto.spring.time.user.domain.UserId;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author caleb
 */
@Component
public class UserLoader implements DataLoader{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void load() {
        User ned = new User(new UserId(),"ned", "Eddard", "stark", "stark@winterfell.cold");
        User heisenberg = new User(new UserId(),"heisenberg", "Walter", "White", "walter@breaking.bad");
        User don = new User(new UserId(), "don", "Donald", "Draper", "don@sterlingcooper.com");

        em.persist(ned);
        em.persist(heisenberg);
        em.persist(don);
    }

    
    
    
}
