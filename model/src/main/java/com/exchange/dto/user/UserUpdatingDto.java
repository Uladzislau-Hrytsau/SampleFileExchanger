package com.exchange.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The type User updating dto.
 */
public class UserUpdatingDto {

    private Long id;
    private String password;
    private Boolean gender;
    private String information;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * Instantiates a new User updating dto.
     */
    public UserUpdatingDto() {
    }

    /**
     * Instantiates a new User updating dto.
     *
     * @param id          the id
     * @param password    the password
     * @param gender      the gender
     * @param information the information
     * @param birthDate   the birth date
     */
    public UserUpdatingDto(
            final Long id,
            final String password,
            final Boolean gender,
            final String information,
            final LocalDate birthDate) {
        this.id = id;
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
    public int hashCode() {
        return Objects.hash(id, password, gender, information, birthDate);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserUpdatingDto that = (UserUpdatingDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(password, that.password) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(information, that.information) &&
                Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public String toString() {
        return "UserUpdatingDto{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", information='" + information + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
