package com.duboscq.nicolas.mynews;

import com.duboscq.nicolas.mynews.utils.DateUtility;

import org.junit.Test;
import static org.junit.Assert.*;


public class DateUtilityUnitTest {

    @Test
    public void convertingDateTestWithCorrectDateOutput(){
        String testDate;
        testDate="2018-01-07";
        assertEquals("07/01/2018", DateUtility.convertingDate(testDate));
    }

    @Test
    public void convertingSearchDateTest(){
        String testDate;
        testDate="17/01/2018";
        assertEquals("20180117", DateUtility.convertingSearchDate(testDate));
    }
}