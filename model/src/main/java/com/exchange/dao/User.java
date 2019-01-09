package com.exchange.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Uladzislau Hrytsau on 20.11.18.
 */
public class User {

    private Long userId;

    private String login;

    private String password;

    private Boolean gender;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;

    private String information;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param login the login
     */
    public User(String login) {
        this.login = login;
    }

    /**
     * Instantiates a new User.
     *
     * @param login    the login
     * @param password the password
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId   the user id
     * @param login    the login
     * @param password the password
     */
    public User(Long userId, String login, String password) {
        this(login, password);
        this.userId = userId;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId   the user id
     * @param login    the login
     * @param password the password
     * @param gender   the gender
     */
    public User(Long userId, String login, String password, Boolean gender) {
        this(userId, login, password);
        this.gender = gender;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId    the user id
     * @param login     the login
     * @param password  the password
     * @param gender    the gender
     * @param birthDate the birth date
     */
    public User(Long userId, String login, String password, Boolean gender, LocalDate birthDate) {
        this(userId, login, password, gender);
        this.birthDate = birthDate;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId      the user id
     * @param login       the login
     * @param password    the password
     * @param gender      the gender
     * @param information the information
     */
    public User(Long userId, String login, String password, Boolean gender, String information) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.information = information;
    }

    /**
     * Instantiates a new User.
     *
     * @param userId      the user id
     * @param login       the login
     * @param password    the password
     * @param gender      the gender
     * @param birthDate   the birth date
     * @param information the information
     */
    public User(Long userId, String login, String password, Boolean gender, LocalDate birthDate, String information) {
        this(userId, login, password, gender, birthDate);
        this.information = information;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
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
    public void setPassword(String password) {
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
    public void setGender(Boolean gender) {
        this.gender = gender;
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
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(information, user.information);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, gender, birthDate, information);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", information='" + information + '\'' +
                '}';
    }
}
