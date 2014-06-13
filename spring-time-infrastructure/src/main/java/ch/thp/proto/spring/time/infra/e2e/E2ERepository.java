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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author caleb
 */

public interface E2ERepository extends JpaRepository<E2ETestEntity,Integer> {

    @Query("select e from E2ETestEntity e where e.aName = ?1 ")
    public E2ETestEntity getByName( String name);
}
