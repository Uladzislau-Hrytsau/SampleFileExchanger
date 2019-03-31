package com.exchange.dao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The type User.
 */
public class User {

    private Long id;
    private String name;
    private String password;
    private Boolean gender;
    private String information;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param name     the name
     * @param password the password
     */
    public User(
            final String name,
            final String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     *
     * @param id          the id
     * @param name        the name
     * @param password    the password
     * @param gender      the gender
     * @param information the information
     */
    public User(
            final Long id,
            final String name,
            final String password,
            final Boolean gender,
            final String information) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.information = information;
    }

    /**
     * Instantiates a new User.
     *
     * @param id          the id
     * @param name        the name
     * @param password    the password
     * @param gender      the gender
     * @param information the information
     * @param birthDate   the birth date
     */
    public User(
            final Long id,
            final String name,
            final String password,
            final Boolean gender,
            final String information,
            final LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.information = information;
        this.birthDate = birthDate;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final Long id) {
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
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(final Boolean gender) {
        this.gender = gender;
    }

    /**
     * Gets information.
     *
     * @return the information
     */
    public String getInformation() {
        return information;
    }

    /**
     * Sets information.
     *
     * @param information the information
     */
    public void setInformation(final String information) {
        this.information = information;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     */
    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(information, user.information) &&
                Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, gender, information, birthDate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", information='" + information + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
