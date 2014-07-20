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
package ch.thp.proto.spring.time.web.resources;

import ch.thp.proto.spring.time.hello.domain.HelloWorld;
import ch.thp.proto.spring.time.hello.domain.HelloWorldRepository;
import java.time.LocalDate;
import javax.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author thpeng
 */
@Controller
@RequestMapping("hello")
public class HelloWorldController {

    @Inject
    private HelloWorldRepository repo;

    @RequestMapping(value = "sayhi", produces = "text/plain", method = RequestMethod.GET)
    public @ResponseBody
    String sayHello() {
        return "oh, hi!";
    }

    @RequestMapping(value = "sayhitodb/{name}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    HelloWorld sayHiDB(@PathVariable("name") String name) {
        return repo.getByName(name);
    }
    @RequestMapping(value = "sayhitodb",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public @ResponseBody HelloWorld create(@RequestBody() HelloWorldModel helloWorld) {
        return repo.save(new HelloWorld(helloWorld.getAName(), helloWorld.getABirthDay()));
    }
    
    @RequestMapping(value = "sayhitodb", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public @ResponseBody
    HelloWorld update( @RequestBody() HelloWorld helloWorld) {
        return repo.save(helloWorld);
    }
    
    @RequestMapping(value = "sayhitodb", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public @ResponseBody
    void delete( @RequestBody() HelloWorld helloWorld) {
        repo.delete(helloWorld);
    }

    @RequestMapping(value = "secure/sayhi", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    String sayHelloSecure() {

        return "oh, hi basic auth!";
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HelloWorldModel
    {
        private String aName; 
        private LocalDate aBirthDay; 
    }
}
