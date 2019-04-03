package com.exchange.service;

import com.exchange.service.implementation.CommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CommonServiceMockTest {

    private static final Integer CORRECT_SIZE = 10;
    private static final Integer CORRECT_PAGE = 10;
    private static final int[] CORRECT_BATCH_RESULT = {1, 1, 1};
    private static final int[] INCORRECT_BATCH_RESULT = {1, 1, 0};

    @InjectMocks
    private CommonService commonService;

    @Test
    public void getOffsetBySizeAndPage_correctSizeAndPage_correctOffset() {
        Integer actualOffset = commonService.getOffsetBySizeAndPage(CORRECT_SIZE, CORRECT_PAGE);
        Integer expectedOffset = CORRECT_SIZE * (CORRECT_PAGE - 1);
        assertEquals(expectedOffset, actualOffset);
    }

    @Test
    public void checkBatchResult_correctBatchResult_trueReturned() {
        assertTrue(commonService.checkBatchResult(CORRECT_BATCH_RESULT));
    }

    @Test
    public void checkBatchResult_incorrectBatchResult_falseReturned() {
        assertFalse(commonService.checkBatchResult(INCORRECT_BATCH_RESULT));
    }

}
