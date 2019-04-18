package com.exchange.service.validation;

import com.exchange.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

/**
 * The type Common validator mock test.
 */
@RunWith(MockitoJUnitRunner.class)
@PropertySource("classpath:message.properties")
public class CommonValidatorMockTest {

    private static final Long CORRECT_IDENTIFIER = 1L;
    private static final Long INCORRECT_IDENTIFIER = -1L;
    private static final String CORRECT_STRING = "CORRECT_STRING";
    private static final Integer CORRECT_PAGE = 1;
    private static final Integer INCORRECT_PAGE = -1;
    private static final Integer CORRECT_SIZE = 10;
    private static final Integer INCORRECT_SIZE = -10;
    private static final LocalDate CORRECT_DATE = LocalDate.of(1000, 10, 10);

    @InjectMocks
    private CommonValidator commonValidator;

    /**
     * Is valid identifier with correct identifier then true returned.
     */
    @Test
    public void isValidIdentifierWithCorrectIdentifierThenTrueReturned() {
        Boolean actualValidatingResult = commonValidator.isValidIdentifier(CORRECT_IDENTIFIER);
        Assert.assertTrue(actualValidatingResult);
    }

    /**
     * Is valid identifier with incorrect identifier then false returned.
     */
    @Test
    public void isValidIdentifierWithIncorrectIdentifierThenFalseReturned() {
        Boolean actualValidatingResult = commonValidator.isValidIdentifier(INCORRECT_IDENTIFIER);
        Assert.assertFalse(actualValidatingResult);
    }

    /**
     * Is valid identifier with null identifier then false returned.
     */
    @Test
    public void isValidIdentifierWithNullIdentifierThenFalseReturned() {
        Boolean actualValidatingResult = commonValidator.isValidIdentifier(null);
        Assert.assertFalse(actualValidatingResult);
    }

    /**
     * Is valid string with correct string then true returned.
     */
    @Test
    public void isValidStringWithCorrectStringThenTrueReturned() {
        Boolean actualValidatingResult = commonValidator.isValidString(CORRECT_STRING);
        Assert.assertTrue(actualValidatingResult);
    }

    /**
     * Is valid string with empty string then false returned.
     */
    @Test
    public void isValidStringWithEmptyStringThenFalseReturned() {
        Boolean actualValidatingResult = commonValidator.isValidString("");
        Assert.assertFalse(actualValidatingResult);
    }

    /**
     * Is valid string with null string then empty returned.
     */
    @Test
    public void isValidStringWithNullStringThenEmptyReturned() {
        Boolean actualValidatingResult = commonValidator.isValidString(null);
        Assert.assertFalse(actualValidatingResult);
    }

    /**
     * Validate page and size with correct page and size then correct.
     */
    @Test
    public void validatePageAndSizeWithCorrectPageAndSizeThenCorrect() {
        commonValidator.validatePageAndSize(CORRECT_PAGE, CORRECT_SIZE);
    }

    /**
     * Validate page and size with incorrect page and correct size then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validatePageAndSizeWithIncorrectPageAndCorrectSizeThenThrowValidationException() {
        commonValidator.validatePageAndSize(INCORRECT_PAGE, CORRECT_SIZE);
    }

    /**
     * Validate page and size with correct page and incorrect size then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validatePageAndSizeWithCorrectPageAndIncorrectSizeThenThrowValidationException() {
        commonValidator.validatePageAndSize(CORRECT_PAGE, INCORRECT_SIZE);
    }

    /**
     * Validate page and size with incorrect page and size then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validatePageAndSizeWithIncorrectPageAndSizeThenThrowValidationException() {
        commonValidator.validatePageAndSize(INCORRECT_PAGE, INCORRECT_SIZE);
    }

    /**
     * Validate page and size with null page and size then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validatePageAndSizeWithNullPageAndSizeThenThrowValidationException() {
        commonValidator.validatePageAndSize(null, null);
    }

    /**
     * Validate date with correct date thencorrect.
     */
    @Test
    public void validateDateWithCorrectDateThencorrect() {
        LocalDate dateBeforeValidating = LocalDate.of(
                CORRECT_DATE.getYear(),
                CORRECT_DATE.getMonth(),
                CORRECT_DATE.getDayOfMonth());
        dateBeforeValidating = commonValidator.validateDate(dateBeforeValidating);
        Assert.assertEquals(CORRECT_DATE, dateBeforeValidating);
    }

    /**
     * Validate date with incorrect date then correct.
     */
    @Test
    public void validateDateWithIncorrectDateThenCorrect() {
        LocalDate dateBeforeValidating = null;
        dateBeforeValidating = commonValidator.validateDate(dateBeforeValidating);
        Assert.assertEquals(LocalDate.now(), dateBeforeValidating);
    }

}
