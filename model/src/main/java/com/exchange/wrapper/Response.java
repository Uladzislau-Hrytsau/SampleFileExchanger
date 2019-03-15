package com.exchange.wrapper;

import com.exchange.dao.Pagination;

import java.util.List;
import java.util.Objects;

/**
 * The type Response.
 *
 * @param <T> the type parameter
 */
public class Response<T> {

    private List<T> data;
    private Pagination pagination;

    /**
     * Instantiates a new Response.
     */
    public Response() {
    }

    /**
     * Instantiates a new Response.
     *
     * @param data       the data
     * @param pagination the pagination
     */
    public Response(List<T> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * Gets pagination.
     *
     * @return the pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Sets pagination.
     *
     * @param pagination the pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response<?> response = (Response<?>) o;
        return Objects.equals(data, response.data) &&
                Objects.equals(pagination, response.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, pagination);
    }

    @Override
    public String toString() {
        return "Response{" +
                "data=" + data +
                ", pagination=" + pagination +
                '}';
    }
}
