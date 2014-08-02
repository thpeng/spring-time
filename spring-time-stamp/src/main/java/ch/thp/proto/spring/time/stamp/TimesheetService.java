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

import ch.thp.proto.spring.time.stamp.domain.Timesheet;
import ch.thp.proto.spring.time.stamp.domain.TimesheetEntry;
import ch.thp.proto.spring.time.stamp.domain.TimesheetId;
import ch.thp.proto.spring.time.stamp.domain.TimesheetRepository;
import ch.thp.proto.spring.time.user.domain.UserId;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author Thierry
 */
@Service
public class TimesheetService {
    
    @Inject
    private TimesheetRepository repo; 
    
    //for non trivial authorization checks we need to use a authorization service
    //Example, if you need to load the user from the database to perform the check. 
    @PreAuthorize("@authorizationService.isPrincipalSameAsUser(principal.username, #id)")
    public Timesheet getTimesheetForUserId(UserId id){
        return repo.getByUserId(id);
    }
    
    @PostAuthorize("@authorizationService.isPrincipalSameAsUser(principal.username, returnObject.userId)")
    public Timesheet getTimesheetByTimesheetId(TimesheetId id){
        return repo.findOne(id);
    }
}
