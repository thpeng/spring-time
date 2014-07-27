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

import ch.thp.proto.spring.time.user.domain.User;
import ch.thp.proto.spring.time.user.domain.UserId;
import ch.thp.proto.spring.time.user.domain.UserRepository;
import java.util.List;
import javax.inject.Inject;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * Service, including authorization checks. always use a service between the repo and your
 * controller. Else you cannot add the security constraints.
 * 
 * @author Thierry
 */
@Service
public class UserService {   
    
    @Inject
    private UserRepository repo; 
    
    @PreAuthorize("#id == principal.username or hasRole('ROLE_ADMIN')")
    public User getUserByLoginId(String id){
        return repo.getByLoginId(id);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> getAllUser() {
        return repo.findAll(); 
    }
    @PreAuthorize("#timeUser.loginId == principal.username or hasRole('ROLE_ADMIN')")
    public User updateUser(User timeUser) {
        return repo.save(timeUser); 
    }
    @PostAuthorize("returnObject.loginId == principal.username or hasRole('ROLE_ADMIN')")
    public User getuser(UserId id) {
        return repo.findOne(id); 
    }
}
