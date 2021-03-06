/*
 * Copyright 2014 thierry.
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
package ch.thp.proto.spring.time.user.domain;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author thierry
 */
public interface UserRepository extends JpaRepository<User, UserId> {

    @Query("select u from User u where u.loginId = ?1 ")
    public User getByLoginId(String loginId);
    
//    @Query("select distinct u from User u where u.familiyName like ?1 or u.givenName like ?1 ")
    public Set<User> findByFamilyNameContainingIgnoreCaseOrGivenNameContainingIgnoreCase(String familyName, String givenName);
}
