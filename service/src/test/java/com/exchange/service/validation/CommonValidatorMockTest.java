package com.exchange.service.validation;

import com.exchange.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

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

    @Test
    public void isValidIdentifier_correctIdentifier_trueReturned() {
        Boolean actualValidatingResult = commonValidator.isValidIdentifier(CORRECT_IDENTIFIER);
        Assert.assertTrue(actualValidatingResult);
    }

    @Test
    public void isValidIdentifier_incorrectIdentifier_falseReturned() {
        Boolean actualValidatingResult = commonValidator.isValidIdentifier(INCORRECT_IDENTIFIER);
        Assert.assertFalse(actualValidatingResult);
    }

    @Test
    public void isValidIdentifier_nullIdentifier_falseReturned() {
        Boolean actualValidatingResult = commonValidator.isValidIdentifier(null);
        Assert.assertFalse(actualValidatingResult);
    }

    @Test
    public void isValidString_correctString_trueReturned() {
        Boolean actualValidatingResult = commonValidator.isValidString(CORRECT_STRING);
        Assert.assertTrue(actualValidatingResult);
    }

    @Test
    public void isValidString_emptyString_falseReturned() {
        Boolean actualValidatingResult = commonValidator.isValidString("");
        Assert.assertFalse(actualValidatingResult);
    }

    @Test
    public void isValidString_nullString_emptyReturned() {
        Boolean actualValidatingResult = commonValidator.isValidString(null);
        Assert.assertFalse(actualValidatingResult);
    }

    @Test
    public void validatePageAndSize_correctPageAndSize_correct() {
        commonValidator.validatePageAndSize(CORRECT_PAGE, CORRECT_SIZE);
    }

    @Test(expected = ValidationException.class)
    public void validatePageAndSize_incorrectPageAndCorrectSize_correct() {
        commonValidator.validatePageAndSize(INCORRECT_PAGE, CORRECT_SIZE);
    }

    @Test(expected = ValidationException.class)
    public void validatePageAndSize_correctPageAndIncorrectSize_correct() {
        commonValidator.validatePageAndSize(CORRECT_PAGE, INCORRECT_SIZE);
    }

    @Test(expected = ValidationException.class)
    public void validatePageAndSize_incorrectPageAndSize_correct() {
        commonValidator.validatePageAndSize(INCORRECT_PAGE, INCORRECT_SIZE);
    }

    @Test(expected = ValidationException.class)
    public void validatePageAndSize_nullPageAndSize_correct() {
        commonValidator.validatePageAndSize(null, null);
    }

    @Test
    public void validateDate_correctDate_correct() {
        LocalDate dateBeforeValidating = LocalDate.of(
                CORRECT_DATE.getYear(),
                CORRECT_DATE.getMonth(),
                CORRECT_DATE.getDayOfMonth());
        dateBeforeValidating = commonValidator.validateDate(dateBeforeValidating);
        Assert.assertEquals(CORRECT_DATE, dateBeforeValidating);
    }

    @Test
    public void validateDate_incorrectDate_correct() {
        LocalDate dateBeforeValidating = null;
        dateBeforeValidating = commonValidator.validateDate(dateBeforeValidating);
        Assert.assertEquals(LocalDate.now(), dateBeforeValidating);
    }

}
