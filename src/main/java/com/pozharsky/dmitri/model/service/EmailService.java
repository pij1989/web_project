package com.pozharsky.dmitri.model.service;

import com.pozharsky.dmitri.exception.ServiceException;

public interface EmailService {
    void sendActivationEmail(String sendToEmail, String token) throws ServiceException;
}
