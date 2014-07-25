/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.thp.proto.spring.time.stamp.domain;

import java.time.Duration;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Entity
public class TimesheetEntry {

    @Id
    private String uuId;
    
    private Duration duration;
    
    private LocalDate entryDate;
    
    private String description;

}
