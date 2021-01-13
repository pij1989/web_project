package com.pozharsky.dmitri.service;

import com.pozharsky.dmitri.exception.ServiceException;

import java.util.List;

public interface UserService {
    List<String> checkEmailAndPassword(String email, String password) throws ServiceException;

    boolean loginUser(String email, String password) throws ServiceException;

    boolean registrationUser(String firstName, String lastName, String email, String password) throws ServiceException;
}
