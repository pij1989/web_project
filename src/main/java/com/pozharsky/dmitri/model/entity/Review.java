package com.pozharsky.dmitri.model.entity;

import java.time.LocalDateTime;

/**
 * The type Review.
 *
 * @author Dmitri Pozharsky
 */
public class Review extends Entity {
    private long id;
    private String comment;
    private int rating;
    private LocalDateTime creatingTime;
    private User user;
    private Product product;

    /**
     * Instantiates a new Review.
     */
    public Review() {
    }

    /**
     * Instantiates a new Review.
     *
     * @param comment      the comment
     * @param rating       the rating
     * @param creatingTime the creating time
     * @param user         the user
     * @param product      the product
     */
    public Review(String comment, int rating, LocalDateTime creatingTime, User user, Product product) {
        this.comment = comment;
        this.rating = rating;
        this.creatingTime = creatingTime;
        this.user = user;
        this.product = product;
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
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets rating.
     *
     * @param rating the rating
     */
    public void setRating(int rating) {
        this.rating = rating;
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
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
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
