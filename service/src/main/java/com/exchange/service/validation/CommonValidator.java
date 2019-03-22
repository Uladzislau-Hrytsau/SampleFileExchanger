package com.exchange.service.validation;

import org.springframework.stereotype.Component;

/**
 * The type Common validator.
 */
@Component
public class CommonValidator {

    /**
     * Is valid identifier boolean.
     *
     * @param identifier the identifier
     * @return the boolean
     */
    public Boolean isValidIdentifier(Long identifier) {
        return identifier != null && identifier >= 0;
    }

    /**
     * Is valid string boolean.
     *
     * @param string the string
     * @return the boolean
     */
    public Boolean isValidString(String string) {
        return string != null && !string.isEmpty();
    }

}
