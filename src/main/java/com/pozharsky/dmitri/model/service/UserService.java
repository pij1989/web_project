package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;
import com.pozharsky.dmitri.model.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Interface provides actions on user.
 *
 * @author Dmitri Pozharsky
 */
public interface UserService {
    Optional<User> loginUser(String email, String password, AtomicInteger blockingCount) throws ServiceException;

    boolean registrationUser(Map<String, String> userForm) throws ServiceException;

    boolean changeUserStatus(long id, User.StatusType statusType) throws ServiceException;

    Optional<User> findUserById(long id) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;

    List<User> findAllUsers() throws ServiceException;

    boolean blockUser(String email, AtomicInteger blockingCount) throws ServiceException;

    boolean changeUserPassword(String email, String oldPassword, String newPassword) throws ServiceException;

    boolean createUserByAdmin(Map<String, String> userForm) throws ServiceException;
}
