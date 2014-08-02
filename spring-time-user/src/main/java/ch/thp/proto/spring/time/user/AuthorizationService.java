/*
 * Copyright 2014 Thierry.
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
package ch.thp.proto.spring.time.user;

import ch.thp.proto.spring.time.user.domain.UserId;
import ch.thp.proto.spring.time.user.domain.UserRepository;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * additional Service to make more complex authorization checks. 
 * @author Thierry
 */
@Service
public class AuthorizationService {

    @Inject
    private UserRepository repo;

    public boolean isPrincipalSameAsUser(String principalName, UserId id) {
        return repo.findOne(id).getLoginId().equals(principalName);
    }
}
