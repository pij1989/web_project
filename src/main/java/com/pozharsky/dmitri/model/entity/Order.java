package com.pozharsky.dmitri.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Order.
 *
 * @author Dmitri Pozharsky
 */
public class Order extends Entity {

    /**
     * The enum Status type.
     */
    public enum StatusType {
        /**
         * New status type.
         */
        NEW,
        /**
         * Processing status type.
         */
        PROCESSING,
        /**
         * Delivered status type.
         */
        DELIVERED,
        /**
         * Canceled status type.
         */
        CANCELED
    }

    private long id;
    private BigDecimal cost;
    private LocalDateTime creatingTime;
    private StatusType statusType;
    private long userId;

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param cost         the cost
     * @param creatingTime the creating time
     * @param statusType   the status type
     * @param userId       the user id
     */
    public Order(BigDecimal cost, LocalDateTime creatingTime, StatusType statusType, long userId) {
        this.cost = cost;
        this.creatingTime = creatingTime;
        this.statusType = statusType;
        this.userId = userId;
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
     * Gets cost.
     *
     * @return the cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Sets cost.
     *
     * @param cost the cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Gets creating time.
     *
     * @return the creating time
     */
    public LocalDateTime getCreatingTime() {
        return creatingTime;
    }

    /**
     * Sets creating time.
     *
     * @param creatingTime the creating time
     */
    public void setCreatingTime(LocalDateTime creatingTime) {
        this.creatingTime = creatingTime;
    }

    /**
     * Gets status type.
     *
     * @return the status type
     */
    public StatusType getStatusType() {
        return statusType;
    }

    /**
     * Sets status type.
     *
     * @param statusType the status type
     */
    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
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

        Order order = (Order) o;

        if (id != order.id) return false;
        if (userId != order.userId) return false;
        if (cost != null ? !cost.equals(order.cost) : order.cost != null) return false;
        if (creatingTime != null ? !creatingTime.equals(order.creatingTime) : order.creatingTime != null) return false;
        return statusType == order.statusType;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (creatingTime != null ? creatingTime.hashCode() : 0);
        result = 31 * result + (statusType != null ? statusType.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", cost=").append(cost);
        sb.append(", creatingTime=").append(creatingTime);
        sb.append(", statusType=").append(statusType);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
