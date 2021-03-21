package com.pozharsky.dmitri.model.entity;

import java.util.List;

public class Page<T extends Entity> {
    private int number;
    private int amountEntity;
    private List<T> entities;

    public Page() {
    }

    public Page(int number, int amountEntity, List<T> entities) {
        this.number = number;
        this.amountEntity = amountEntity;
        this.entities = entities;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAmountEntity() {
        return amountEntity;
    }

    public void setAmountEntity(int amountEntity) {
        this.amountEntity = amountEntity;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page<?> page = (Page<?>) o;

        if (number != page.number) return false;
        if (amountEntity != page.amountEntity) return false;
        return entities != null ? entities.equals(page.entities) : page.entities == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + amountEntity;
        result = 31 * result + (entities != null ? entities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Page{");
        sb.append("number=").append(number);
        sb.append(", amountEntity=").append(amountEntity);
        sb.append(", entities=").append(entities);
        sb.append('}');
        return sb.toString();
    }
}
