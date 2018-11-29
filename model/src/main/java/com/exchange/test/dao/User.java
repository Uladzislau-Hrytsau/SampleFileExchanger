package com.exchange.test.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Uladzislau Hrytsau on 20.11.18.
 */
public class User {

    private Long userId;

    private String login;

    private String password;

    private Boolean gender;

    private LocalDate birthDate;

    private String information;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Long userId, String login, String password) {
        this(login, password);
        this.userId = userId;
    }

    public User(Long userId, String login, String password, Boolean gender) {
        this(userId, login, password);
        this.gender = gender;
    }

    public User(Long userId, String login, String password, Boolean gender, LocalDate birthDate) {
        this(userId, login, password, gender);
        this.birthDate = birthDate;
    }

    public User(Long userId, String login, String password, Boolean gender, String information) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.gender = gender;
        this.information = information;
    }

    public User(Long userId, String login, String password, Boolean gender, LocalDate birthDate, String information) {
        this(userId, login, password, gender, birthDate);
        this.information = information;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getInformation() {
        return information;
    }

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
