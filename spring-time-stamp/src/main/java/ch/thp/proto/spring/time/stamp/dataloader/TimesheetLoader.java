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
package ch.thp.proto.spring.time.stamp.dataloader;

import ch.thp.proto.spring.time.infra.dataloader.DataLoader;
import ch.thp.proto.spring.time.stamp.domain.Timesheet;
import ch.thp.proto.spring.time.stamp.domain.TimesheetEntry;
import ch.thp.proto.spring.time.stamp.domain.TimesheetId;
import static ch.thp.proto.spring.time.user.dataloader.UserLoader.USER_ID_HEISENBERG;
import static ch.thp.proto.spring.time.user.dataloader.UserLoader.USER_ID_NED;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thierry
 */
@Component
public class TimesheetLoader implements DataLoader {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void load() {
        Set<TimesheetEntry> entriesForNed = new HashSet<>();
        entriesForNed.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(1), Duration.ofHours(4),
                "read something about genealogy. made a comment about the strange color of geoffreys baratheons hair."));
        entriesForNed.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(1), Duration.ofHours(2),
                "inspected the wall. still white"));
        entriesForNed.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(1), Duration.ofHours(2),
                "lost my head about something"));
        Timesheet sheetNed = new Timesheet(new TimesheetId(), USER_ID_NED, 24d, 0.8d, LocalDate.now().minusDays(1), entriesForNed);

        Set<TimesheetEntry> entriesForHeisenberg = new HashSet<>();
        entriesForHeisenberg.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(1), Duration.ofHours(8),
                "still cooking, what are you expecting?"));
        entriesForHeisenberg.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(2), Duration.ofHours(8),
                "still cooking"));
        entriesForHeisenberg.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(3), Duration.ofHours(8),
                "cooking"));
        Timesheet sheetHeisenberg = new Timesheet(new TimesheetId(), USER_ID_HEISENBERG, 100, 1.0d, LocalDate.now().minusDays(3), entriesForHeisenberg);

        Set<TimesheetEntry> entriesForDon = new HashSet<>();
        entriesForDon.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(1), Duration.ofHours(8),
                "made a witty comment about the usefullness of my minions"));
        entriesForDon.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(2), Duration.ofHours(4),
                "fighting with betty"));
        entriesForDon.add(new TimesheetEntry(UUID.randomUUID().toString(), LocalDate.now().minusDays(2), Duration.ofHours(10),
                "made overtime. a lot"));
        Timesheet sheetDon = new Timesheet(new TimesheetId(), USER_ID_HEISENBERG, 100, 1.0d, LocalDate.now().minusDays(2), entriesForDon);

        em.persist(sheetNed);
        em.persist(sheetHeisenberg);
        em.persist(sheetDon);
    }
}
