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
package ch.thp.proto.spring.time.web.resources;

import ch.thp.proto.spring.time.stamp.TimesheetEntryService;
import ch.thp.proto.spring.time.stamp.TimesheetService;
import ch.thp.proto.spring.time.stamp.domain.Timesheet;
import ch.thp.proto.spring.time.stamp.domain.TimesheetEntry;
import ch.thp.proto.spring.time.stamp.domain.TimesheetId;
import ch.thp.proto.spring.time.user.domain.UserId;
import java.time.LocalDate;
import java.util.Set;
import javax.inject.Inject;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Thierry
 */
@Controller
@RequestMapping("secure/timesheet")

public class TimesheetController {

    @Inject
    private TimesheetService sheetService;
    
    @Inject
    private TimesheetEntryService entryService;

    @RequestMapping( produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody TimesheetOnlyModel getTimeSheetForUserId(@RequestParam(value = "user", required = true) String userId) {
        return new TimesheetOnlyModel(sheetService.getTimesheetForUserId(new UserId(userId)));
    }
    
    @RequestMapping(value = "{id}/entry", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody Set<TimesheetEntry> getSheetEntries(@PathVariable(value = "id") String timesheetId) {
        return entryService.getEntryForTimesheetId(new TimesheetId(timesheetId));
    }

    /**
     * Timesheet without the Entry Set. 
     */
    @Data
    private static class TimesheetOnlyModel {

        private TimesheetId timesheetId;
        private UserId userId;
        private double saldoGleitzeit;
        private double pensum;
        private LocalDate dateOfJoiningTheCompany;

        public TimesheetOnlyModel(Timesheet ts) {
            this.timesheetId = ts.getTimesheetId();
            this.userId = ts.getUserId();
            this.saldoGleitzeit = ts.getSaldoGleitzeit();
            this.pensum = ts.getPensum();
            this.dateOfJoiningTheCompany = ts.getDateOfJoiningTheCompany();
        }
    }
}
