package com.pozharsky.dmitri.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order extends Entity {

    public enum StatusType {
        NEW,
        PROCESSING,
        DELIVERED,
        CANCELED
    }

    private long id;
    private BigDecimal cost;
    private LocalDateTime creatingTime;
    private StatusType statusType;
    private long userId;

    public Order() {
    }

    public Order(BigDecimal cost, LocalDateTime creatingTime, StatusType statusType, long userId) {
        this.cost = cost;
        this.creatingTime = creatingTime;
        this.statusType = statusType;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public LocalDateTime getCreatingTime() {
        return creatingTime;
    }

    public void setCreatingTime(LocalDateTime creatingTime) {
        this.creatingTime = creatingTime;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public long getUserId() {
        return userId;
    }

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
