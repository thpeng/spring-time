/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.thp.proto.spring.time.stamp.domain;

import ch.thp.proto.spring.time.user.domain.UserId;
import java.time.Duration;
import java.time.LocalDate;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Thierry
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class TimesheetEntry {

    private String uuId;
    private UserId userId;
    private Duration duration;
    private LocalDate entryDate;
    private String description;

}
