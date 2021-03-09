package com.pozharsky.dmitri.model.entity;

import java.time.LocalDateTime;

public class Review extends Entity {
    private long id;
    private String comment;
    private int rating;
    private LocalDateTime creatingTime;
    private long userId;
    private long productId;

    public Review() {
    }

    public Review(String comment, int rating, LocalDateTime creatingTime, long userId, long productId) {
        this.comment = comment;
        this.rating = rating;
        this.creatingTime = creatingTime;
        this.userId = userId;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatingTime() {
        return creatingTime;
    }

    public void setCreatingTime(LocalDateTime creatingTime) {
        this.creatingTime = creatingTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != review.id) return false;
        if (rating != review.rating) return false;
        if (userId != review.userId) return false;
        if (productId != review.productId) return false;
        if (comment != null ? !comment.equals(review.comment) : review.comment != null) return false;
        return creatingTime != null ? creatingTime.equals(review.creatingTime) : review.creatingTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (creatingTime != null ? creatingTime.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Review{");
        sb.append("id=").append(id);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", rating=").append(rating);
        sb.append(", creatingTime=").append(creatingTime);
        sb.append(", userId=").append(userId);
        sb.append(", productId=").append(productId);
        sb.append('}');
        return sb.toString();
    }
}
