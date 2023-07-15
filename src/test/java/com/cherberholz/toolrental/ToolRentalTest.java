package com.cherberholz.toolrental;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ToolRentalTest {
    @Test
    public void mainTest() {
        // TODO to start off create tests to verify rental day count is >= 1 and discount is between 0-100 percent
        String[] testArgument = {"test", "test2"};
        ToolRental.main(testArgument);

        assertEquals(testArgument[0], "test");
    }
}
