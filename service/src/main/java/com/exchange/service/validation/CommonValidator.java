package com.exchange.service.validation;

import com.exchange.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * The type Common validator.
 */
@Component
public class CommonValidator {

    @Value("${pagination.incorrectSize}")
    private String incorrectSize;

    @Value("${pagination.incorrectPage}")
    private String incorrectPage;

    /**
     * Is valid identifier boolean.
     *
     * @param identifier the identifier
     * @return the boolean
     */
    public Boolean isValidIdentifier(final Long identifier) {
        return identifier != null && identifier >= 0;
    }

    /**
     * Is valid string boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public Boolean isValidString(final String string) {
        return string != null && !string.isEmpty();
    }

    /**
     * Validate page and size.
     *
     * @param page the page
     * @param size the size
     */
    public void validatePageAndSize(
            final Integer page,
            final Integer size) {
        if (page == null || page <= 0) {
            throw new ValidationException(incorrectPage);
        }
        if (size == null || size <= 0) {
            throw new ValidationException(incorrectSize);
        }
    }

    /**
     * Validate date local date.
     *
     * @param localDate the local date
     * @return the local date
     */
    public LocalDate validateDate(final LocalDate localDate) {
        if (localDate == null) {
            return LocalDate.now();
        }
        return localDate;
    }

}
