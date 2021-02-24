package com.pozharsky.dmitri.model.creator;

import com.pozharsky.dmitri.model.entity.RoleType;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.User;

import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

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
        RoleType roleType;
        StatusType statusType;
        if (role == null) {
            roleType = RoleType.USER;
        } else {
            roleType = RoleType.valueOf(role);
        }
        if (status == null) {
            statusType = StatusType.WAIT_ACTIVE;
        } else {
            statusType = StatusType.valueOf(status);
        }
        return new User(firstName, lastName, username, email, roleType, statusType);
    }
}
