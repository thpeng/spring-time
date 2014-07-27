/*
 * Copyright 2014 thpeng.
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
package ch.thp.proto.spring.time.hello.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;


/**
 * simple entity with a database generated id, a string and a date.  
 * @author thierry
 */
@Data
@NoArgsConstructor()
@RequiredArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"aName"},name = "unique_aname")})
public class HelloWorld {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String aName;

    @NonNull
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDate")
    private LocalDate aBirthDay;

}
