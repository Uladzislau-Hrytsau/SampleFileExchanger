package com.exchange.dao;

import java.util.Objects;

/**
 * The type Pagination.
 */
public class Pagination {

    private Long count;

    /**
     * Instantiates a new Pagination.
     */
    public Pagination() {
    }

    /**
     * Instantiates a new Pagination.
     *
     * @param count the count
     */
    public Pagination(Long count) {
        this.count = count;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public Long getCount() {
        return count;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagination that = (Pagination) o;
        return Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "count=" + count +
                '}';
    }
}
