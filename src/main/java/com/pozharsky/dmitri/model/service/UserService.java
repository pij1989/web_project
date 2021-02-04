package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.StatusType;
import com.pozharsky.dmitri.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<String> checkEmailAndPassword(String email, String password) throws ServiceException;

    Optional<User> loginUser(String email, String password) throws ServiceException;

    boolean registrationUser(String firstName, String lastName, String username, String email, String password) throws ServiceException;

    boolean changeUserStatus(long id, StatusType statusType) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;
}
