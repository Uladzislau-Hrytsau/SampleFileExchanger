package com.exchange.service;

import com.exchange.service.implementation.CommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * The type Common service mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class CommonServiceMockTest {

    private static final Integer CORRECT_SIZE = 10;
    private static final Integer CORRECT_PAGE = 10;
    private static final int[] CORRECT_BATCH_RESULT = {1, 1, 1};
    private static final int[] INCORRECT_BATCH_RESULT = {1, 1, 0};

    @InjectMocks
    private CommonService commonService;

    /**
     * Gets offset by size and page correct size and page correct offset.
     */
    @Test
    public void getOffsetBySizeAndPage_correctSizeAndPage_correctOffset() {
        Integer actualOffset = commonService.getOffsetBySizeAndPage(CORRECT_SIZE, CORRECT_PAGE);
        Integer expectedOffset = CORRECT_SIZE * (CORRECT_PAGE - 1);
        assertEquals(expectedOffset, actualOffset);
    }

    /**
     * Check batch result correct batch result true returned.
     */
    @Test
    public void checkBatchResult_correctBatchResult_trueReturned() {
        assertTrue(commonService.checkBatchResult(CORRECT_BATCH_RESULT));
    }

    /**
     * Check batch result incorrect batch result false returned.
     */
    @Test
    public void checkBatchResult_incorrectBatchResult_falseReturned() {
        assertFalse(commonService.checkBatchResult(INCORRECT_BATCH_RESULT));
    }

}
