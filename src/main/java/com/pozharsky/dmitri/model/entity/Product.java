package com.pozharsky.dmitri.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * The type Product.
 *
 * @author Dmitri Pozharsky
 */
public class Product extends Entity {
    private long id;
    private String name;
    private BigDecimal price;
    private int amount;
    private boolean status;
    private String description;
    private byte[] image;
    private LocalDateTime creatingTime;
    private long categoryId;

    /**
     * Instantiates a new Product.
     */
    public Product() {
    }

    /**
     * Instantiates a new Product.
     *
     * @param name         the name
     * @param price        the price
     * @param amount       the amount
     * @param status       the status
     * @param description  the description
     * @param image        the image
     * @param creatingTime the creating time
     * @param categoryId   the category id
     */
    public Product(String name, BigDecimal price, int amount, boolean status, String description, byte[] image, LocalDateTime creatingTime, long categoryId) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.status = status;
        this.description = description;
        this.image = image;
        this.creatingTime = creatingTime;
        this.categoryId = categoryId;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets amount.
     *
     * @param amount the amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Is status boolean.
     *
     * @return the boolean
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get image byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(byte[] image) {
        this.image = image;
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
     * Gets category id.
     *
     * @return the category id
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets category id.
     *
     * @param categoryId the category id
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (amount != product.amount) return false;
        if (status != product.status) return false;
        if (categoryId != product.categoryId) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (!Arrays.equals(image, product.image)) return false;
        return creatingTime != null ? creatingTime.equals(product.creatingTime) : product.creatingTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(image);
        result = 31 * result + (creatingTime != null ? creatingTime.hashCode() : 0);
        result = 31 * result + (int) (categoryId ^ (categoryId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", amount=").append(amount);
        sb.append(", status=").append(status);
        sb.append(", description='").append(description).append('\'');
        sb.append(", image=").append(Arrays.toString(image));
        sb.append(", creatingTime=").append(creatingTime);
        sb.append(", categoryId=").append(categoryId);
        sb.append('}');
        return sb.toString();
    }
}
