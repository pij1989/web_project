package com.pozharsky.dmitri.model.entity;

public class OrderProduct extends Entity {
    private long id;
    private int amount;
    private Product product;
    private Order order;


    public OrderProduct() {
    }

    public OrderProduct(int amount, Product product, Order order) {
        this.amount = amount;
        this.product = product;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProduct that = (OrderProduct) o;

        if (id != that.id) return false;
        if (amount != that.amount) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        return order != null ? order.equals(that.order) : that.order == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + amount;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderProduct{");
        sb.append("id=").append(id);
        sb.append(", amount=").append(amount);
        sb.append(", product=").append(product);
        sb.append(", order=").append(order);
        sb.append('}');
        return sb.toString();
    }
}
