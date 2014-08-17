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

package ch.thp.proto.spring.time.stamp;

import ch.thp.proto.spring.time.stamp.domain.TimesheetId;
import ch.thp.proto.spring.time.stamp.domain.TimesheetRepository;
import ch.thp.proto.spring.time.user.AuthorizationService;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thierry
 */
@Service
public class TimesheetAuthorizationService {
    
    @Inject
    private AuthorizationService authService; 
    
    @Inject
    private TimesheetRepository tsRepository; 
    
    public boolean isPrincipalOwnerOfTimesheet(String principal, TimesheetId id){
        return authService.isPrincipalSameAsUser(principal,tsRepository.findOne(id).getUserId());
    }
}
