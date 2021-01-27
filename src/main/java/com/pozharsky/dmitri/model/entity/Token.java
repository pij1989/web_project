package com.pozharsky.dmitri.model.entity;

import java.time.LocalDateTime;

public class Token extends Entity {
    private long id;
    private String tokenValue;
    private LocalDateTime timeCreate;
    private LocalDateTime timeExpire;

    public Token() {
    }

    public Token(String tokenValue, LocalDateTime timeCreate, LocalDateTime timeExpire) {
        this.tokenValue = tokenValue;
        this.timeCreate = timeCreate;
        this.timeExpire = timeExpire;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public LocalDateTime getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    public LocalDateTime getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(LocalDateTime timeExpire) {
        this.timeExpire = timeExpire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (id != token.id) return false;
        if (tokenValue != null ? !tokenValue.equals(token.tokenValue) : token.tokenValue != null) return false;
        if (timeCreate != null ? !timeCreate.equals(token.timeCreate) : token.timeCreate != null) return false;
        return timeExpire != null ? timeExpire.equals(token.timeExpire) : token.timeExpire == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (tokenValue != null ? tokenValue.hashCode() : 0);
        result = 31 * result + (timeCreate != null ? timeCreate.hashCode() : 0);
        result = 31 * result + (timeExpire != null ? timeExpire.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Token{");
        sb.append("id=").append(id);
        sb.append(", tokenValue='").append(tokenValue).append('\'');
        sb.append(", timeCreate=").append(timeCreate);
        sb.append(", timeExpire=").append(timeExpire);
        sb.append('}');
        return sb.toString();
    }
}
