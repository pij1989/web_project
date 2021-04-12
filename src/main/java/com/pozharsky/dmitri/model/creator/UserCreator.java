package com.pozharsky.dmitri.model.creator;

import com.pozharsky.dmitri.model.entity.User;

import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

/**
 * Creator is used to create an User object.
 *
 * @author Dmitri Pozharsky
 */
public class UserCreator {

    private UserCreator() {
    }

    public static User createUser(Map<String, String> userForm) {
        String firstName = userForm.get(FIRST_NAME);
        String lastName = userForm.get(LAST_NAME);
        String username = userForm.get(USERNAME);
        String email = userForm.get(EMAIL);
        String role = userForm.get(ROLE);
        String status = userForm.get(STATUS);
        User.RoleType roleType;
        User.StatusType statusType;
        if (role == null) {
            roleType = User.RoleType.USER;
        } else {
            roleType = User.RoleType.valueOf(role);
        }
        if (status == null) {
            statusType = User.StatusType.WAIT_ACTIVE;
        } else {
            statusType = User.StatusType.valueOf(status);
        }
        return new User(firstName, lastName, username, email, roleType, statusType);
    }
}
