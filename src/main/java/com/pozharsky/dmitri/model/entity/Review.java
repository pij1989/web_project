package com.pozharsky.dmitri.model.entity;

import java.time.LocalDateTime;

public class Review extends Entity {
    private long id;
    private String comment;
    private int rating;
    private LocalDateTime creatingTime;
    private User user;
    private Product product;

    public Review() {
    }

    public Review(String comment, int rating, LocalDateTime creatingTime, User user, Product product) {
        this.comment = comment;
        this.rating = rating;
        this.creatingTime = creatingTime;
        this.user = user;
        this.product = product;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != review.id) return false;
        if (rating != review.rating) return false;
        if (comment != null ? !comment.equals(review.comment) : review.comment != null) return false;
        if (creatingTime != null ? !creatingTime.equals(review.creatingTime) : review.creatingTime != null)
            return false;
        if (user != null ? !user.equals(review.user) : review.user != null) return false;
        return product != null ? product.equals(review.product) : review.product == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (creatingTime != null ? creatingTime.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Review{");
        sb.append("id=").append(id);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", rating=").append(rating);
        sb.append(", creatingTime=").append(creatingTime);
        sb.append(", user=").append(user);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
}
