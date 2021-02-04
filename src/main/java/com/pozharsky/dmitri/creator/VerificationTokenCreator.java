package com.pozharsky.dmitri.creator;

import com.pozharsky.dmitri.model.entity.Token;

import java.time.LocalDateTime;
import java.util.UUID;

public final class VerificationTokenCreator {
    private static final long EXPIRE_TIME = 15L;

    private VerificationTokenCreator() {
    }

    public static Token createVerificationToken() {
        String tokenValue = UUID.randomUUID().toString();
        LocalDateTime timeCreate = LocalDateTime.now();
        LocalDateTime timeExpire = timeCreate.plusMinutes(EXPIRE_TIME);
        return new Token(tokenValue, timeCreate, timeExpire);
    }
}
