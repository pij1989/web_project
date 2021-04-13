package com.pozharsky.dmitri.model.entity;

import java.time.LocalDateTime;

/**
 * The type Token.
 *
 * @author Dmitri Pozharsky
 */
public class Token extends Entity {
    private long id;
    private String tokenValue;
    private LocalDateTime timeCreate;
    private LocalDateTime timeExpire;
    private long userId;

    /**
     * Instantiates a new Token.
     */
    public Token() {
    }

    /**
     * Instantiates a new Token.
     *
     * @param tokenValue the token value
     * @param timeCreate the time create
     * @param timeExpire the time expire
     */
    public Token(String tokenValue, LocalDateTime timeCreate, LocalDateTime timeExpire) {
        this.tokenValue = tokenValue;
        this.timeCreate = timeCreate;
        this.timeExpire = timeExpire;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets token value.
     *
     * @return the token value
     */
    public String getTokenValue() {
        return tokenValue;
    }

    /**
     * Sets token value.
     *
     * @param tokenValue the token value
     */
    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    /**
     * Gets time create.
     *
     * @return the time create
     */
    public LocalDateTime getTimeCreate() {
        return timeCreate;
    }

    /**
     * Sets time create.
     *
     * @param timeCreate the time create
     */
    public void setTimeCreate(LocalDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    /**
     * Gets time expire.
     *
     * @return the time expire
     */
    public LocalDateTime getTimeExpire() {
        return timeExpire;
    }

    /**
     * Sets time expire.
     *
     * @param timeExpire the time expire
     */
    public void setTimeExpire(LocalDateTime timeExpire) {
        this.timeExpire = timeExpire;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (id != token.id) return false;
        if (userId != token.userId) return false;
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
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Token{");
        sb.append("id=").append(id);
        sb.append(", tokenValue='").append(tokenValue).append('\'');
        sb.append(", timeCreate=").append(timeCreate);
        sb.append(", timeExpire=").append(timeExpire);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
