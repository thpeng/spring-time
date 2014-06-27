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
package ch.thp.proto.spring.time.web.config;

import java.io.IOException;
import static java.lang.StrictMath.log;
import javax.persistence.Basic;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import static javax.ws.rs.core.HttpHeaders.WWW_AUTHENTICATE;
import static javax.ws.rs.core.Response.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * TODO link:
 *
 * @author thpeng
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("ned").password("123").roles("USER").and()
                .withUser("arya").password("123").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/hello/secure/**")
                .authorizeRequests()
                .antMatchers("/hello/secure/**").hasRole("USER")
//                .antMatchers("/hello/sayhi/**", "/admin/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .addFilterBefore(
                    new BasicAuthenticationFilter(authenticationManager(), new BasicJsonEntryPoint()),
                    BasicAuthenticationFilter.class);
    }
    static class BasicJsonEntryPoint extends BasicAuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest req, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            response.addHeader( HttpHeaders.WWW_AUTHENTICATE, "/Basic realm='${getRealmName()}'");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
