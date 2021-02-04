package com.pozharsky.dmitri.model.service;

public interface EmailService {
    void sendActivationEmail(String sendToEmail, String token);
}
