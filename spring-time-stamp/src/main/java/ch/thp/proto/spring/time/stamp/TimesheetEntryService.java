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

import ch.thp.proto.spring.time.stamp.domain.TimesheetEntry;
import ch.thp.proto.spring.time.stamp.domain.TimesheetId;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thierry
 */
@Service
public class TimesheetEntryService {
    
    @Inject
    private TimesheetService service; 
    
    //authorization done by the timesheetservice 
    @Transactional
    public Set<TimesheetEntry> getEntryForTimesheetId(TimesheetId id){
        Set<TimesheetEntry> entries = service.getTimesheetByTimesheetId(id).getTimesheetEntries(); 
        //to circumvent the lazyloading exception we have to initialize the bag inside of the transaction
        entries.size();
        //OSIV does not work with the javaconfig setup, and the hibernate4 module for jackson has still the same issue. 
        //transactional on controller is also not a solution because the jackons library serialization comes after the commit
        return entries;
    }
}
