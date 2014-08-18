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
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
public class TimesheetController {

    @Inject
    private TimesheetService sheetService;
    
    @Inject
    private TimesheetEntryService entryService;

    @RequestMapping( produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody TimesheetOnlyModel getTimeSheetForUserId(@RequestParam(value = "user", required = true) String userId) {
        return new TimesheetOnlyModel(sheetService.getTimesheetForUserId(new UserId(userId)));
    }
    
    @RequestMapping(value = "{tsid}/entry", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody Set<TimesheetEntry> getSheetEntries(@PathVariable(value = "tsid") String timesheetId) {
        return entryService.getEntryForTimesheetId(new TimesheetId(timesheetId));
    }
    
    @RequestMapping(value = "{tsid}/entry/{entryid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public @ResponseBody TimesheetEntry updateEntry(@PathVariable(value = "tsid") String timesheetId, @RequestBody() TimesheetEntry entry) {
        return entryService.updateEntry(new TimesheetId(timesheetId), entry);
    }
    
    @RequestMapping(value = "{tsid}/entry/{entryid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody TimesheetEntry getSingleEntry(@PathVariable(value = "tsid") String timesheetId, @PathVariable(value = "entryid") String entryId) {
        return entryService.getSingleEntry(new TimesheetId(timesheetId), entryId);
    }
    
    
    @RequestMapping(value = "{tsid}/entry/{entryid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public @ResponseBody TimesheetEntry createEntry(@PathVariable(value = "tsid") String timesheetId,@RequestBody() TimesheetEntry entry) {
        return entryService.createEntry(new TimesheetId(timesheetId), entry);
    }
    
    @RequestMapping(value = "{tsid}/entry/{entryid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEntry(@PathVariable(value = "tsid") String timesheetId, @PathVariable("entryid") String entryId) {
        try {
            entryService.deleteEntry(new TimesheetId(timesheetId), entryId);
        } catch (EmptyResultDataAccessException exc) {
            log.debug("no entry found with id " + entryId);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);        
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
