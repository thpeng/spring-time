/*
 * Copyright 2014 caleb.
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

import ch.thp.proto.spring.time.infra.e2e.E2ELoader;
import ch.thp.proto.spring.time.infra.e2e.E2ERepository;
import ch.thp.proto.spring.time.infra.e2e.E2ETestEntity;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author caleb
 */
@Controller
@RequestMapping("hello")
public class HelloWorldController {

    @Inject
    private E2ELoader loader;

    @Inject
    private E2ERepository repo;

    @RequestMapping(value = "sayhi", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    String sayHello() {
       
        return "oh, hi!";
    }

    @RequestMapping(headers = "Content-Type=application/json",value = "sayhitodb", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody E2ETestEntity sayHiDB() {
         loader.populateDatabase();
        return repo.getByName("trololo");
    }
}
