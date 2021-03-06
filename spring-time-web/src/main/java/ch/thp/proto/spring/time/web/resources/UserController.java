/*
 * Copyright 2014 thierry.
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

import ch.thp.proto.spring.time.user.UserService;
import ch.thp.proto.spring.time.user.domain.User;
import ch.thp.proto.spring.time.user.domain.UserId;
import com.google.common.collect.Collections2;
import com.wordnik.swagger.annotations.ApiOperation;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author thierry
 */
@Controller
@RequestMapping("secure/user")
public class UserController {

    @Inject
    private UserService service;

    //you may describe your endpoint with this annotation (feature from swagger)
    @ApiOperation(value = "gets the current user with the associates authorities")
    @RequestMapping(value = "current", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody UserWithAuthoritiesModel getCurrentUserWithAuthorities(Principal princ) {
        //not really beautiful, but we cannot obtain the authorities like the principal 
        return new UserWithAuthoritiesModel(service.getUserByLoginId(princ.getName()), SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUser() {
        return service.getAllUser();
    }
    
    @RequestMapping(value = "autocomplete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody Set<User> getUsersByFamilyOrGivenName(@RequestParam(required = true, value = "nameQuery") String nameQuery) {
        return service.getUsersByFamilyOrGivenName(nameQuery);
    }

    @RequestMapping(value= "{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody User getOneUser(@PathVariable("id") String userId) {
        return service.getuser(new UserId(userId));
    }

    @RequestMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public @ResponseBody
    User updateUser(@RequestBody() User user, @PathVariable("id") String userId) {
        //userId is not really needed, but we break the $resource semantics if not included
        //and its necessary for example with the delete (no body allowed with this operation)
        return service.updateUser(user);
    }

    @Data
    static class UserWithAuthoritiesModel implements Serializable {
        User user;
        Collection<String> roles;

        private UserWithAuthoritiesModel(User userByLoginId, Collection<? extends GrantedAuthority> authorities) {
            user = userByLoginId;
            roles = Collections2.transform(authorities, (GrantedAuthority input) -> input.getAuthority());
        }
    }
}
