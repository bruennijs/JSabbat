/*
 * Copyright 2002-2016 the original author or authors.
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
package spring.security.provisioning;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.security.core.userdetails.memory.UserAttributeEditor;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;

/**
 * returns instance from list without copying it
 * see org.springframework.security.provisioning.InMemoryUserDetailsManager
 *
 * @author Oliver Brüntje
 * @since 3.1
 */
public class InMemoryUserDetailsManager implements UserDetailsManager {
    protected final Log logger = LogFactory.getLog(getClass());

    private final Map<String, UserDetails> users = new HashMap<String, UserDetails>();

    private AuthenticationManager authenticationManager;
    private Function<UserDetails, UserDetails> userCreator;

    public InMemoryUserDetailsManager() {
    }

    public InMemoryUserDetailsManager(Collection<? extends UserDetails> users, Function<UserDetails, UserDetails> userCreator) {
        this.userCreator = userCreator;
        for (UserDetails user : users) {
            createUser(user);
        }
    }

    public InMemoryUserDetailsManager(Properties users) {
        Enumeration<?> names = users.propertyNames();
        UserAttributeEditor editor = new UserAttributeEditor();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            editor.setAsText(users.getProperty(name));
            UserAttribute attr = (UserAttribute) editor.getValue();
            UserDetails user = new User(name, attr.getPassword(), attr.isEnabled(), true,
                    true, true, attr.getAuthorities());
            createUser(user);
        }
    }

    public void createUser(UserDetails user) {
        Assert.isTrue(!userExists(user.getUsername()), "user should not exist");

        users.put(user.getUsername().toLowerCase(), userCreator.apply(user));
    }

    public void deleteUser(String username) {
        users.remove(username.toLowerCase());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    public void updateUser(UserDetails user) {
        Assert.isTrue(userExists(user.getUsername()), "user should exist");

        users.put(user.getUsername().toLowerCase(), userCreator.apply(user));
    }

    public boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserDetails user = users.get(username.toLowerCase());

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return userCreator.apply(user);
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}
