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
package ch.thp.proto.spring.time.user.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.AllArgsConstructor;

/**
 * typesafe identity of user, may be used between aggregates
 * @author Thierry
 */
@Data
@AllArgsConstructor
@Embeddable
public class UserId implements Serializable{

    @Column(name="ID")
    private String uuId;
    
    public UserId()
    {
        uuId = UUID.randomUUID().toString();
    }
}
