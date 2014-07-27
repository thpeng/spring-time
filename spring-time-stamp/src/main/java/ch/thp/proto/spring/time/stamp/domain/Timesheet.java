/*
 * Copyright 2013 Thierry.
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
package ch.thp.proto.spring.time.stamp.domain;

import ch.thp.proto.spring.time.user.domain.User;
import ch.thp.proto.spring.time.user.domain.UserId;
import java.io.Serializable;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 *
 * @author Thierry
 */
@Entity
@Data
public class Timesheet implements Serializable {

    @Id
    private String uuId;
    
    @Embedded
    @AttributeOverride(name="uuId", column = @Column(name="USER_ID"))
    @ManyToOne(targetEntity = User.class)
    private UserId userId;
    
    private double saldoGleitzeit; 
    
    private double saldoFerien; 
    
    @OneToMany
    private List<TimesheetEntry> timesheetEntries; 

}
