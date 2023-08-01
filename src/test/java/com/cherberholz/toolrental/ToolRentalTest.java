package com.cherberholz.toolrental;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class ToolRentalTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void test1() {
        String[] testArgument = {"JAKR", "9/3/15", "5", "101"};
        ToolRental.main(testArgument);

        assertEquals("Error: Discount percent must be within 0-100\n", outputStreamCaptor.toString());
    }

    @Test
    public void test2() {
        String[] testArgument = {"LADW", "7/2/20", "3", "10"};
        ToolRental.main(testArgument);

        String result =
            """
            Tool code: LADW
            Tool type: Ladder
            Tool brand: Werner
            Rental days: 3
            Check out date: 7/2/20
            Due date: 7/5/20
            Daily rental charge: $1.99
            Charge days: 2
            Pre-discount charge: $3.98
            Discount percent: 10%
            Discount amount: $0.4
            Final charge: $3.58
            """;

        assertEquals(result, outputStreamCaptor.toString());

    }

    @Test
    public void test3() {
        String[] testArgument = {"CHNS", "7/2/15", "5", "25"};
        ToolRental.main(testArgument);

        String result =
                """
                Tool code: CHNS
                Tool type: Chainsaw
                Tool brand: Stihl
                Rental days: 5
                Check out date: 7/2/15
                Due date: 7/7/15
                Daily rental charge: $1.49
                Charge days: 3
                Pre-discount charge: $4.47
                Discount percent: 25%
                Discount amount: $1.12
                Final charge: $3.35
                """;

        assertEquals(result, outputStreamCaptor.toString());

    }

    @Test
    public void test4() {
        String[] testArgument = {"JAKD", "9/3/15", "6", "0"};
        ToolRental.main(testArgument);

        String result =
                """
                Tool code: JAKD
                Tool type: Jackhammer
                Tool brand: DeWalt
                Rental days: 6
                Check out date: 9/3/15
                Due date: 9/9/15
                Daily rental charge: $2.99
                Charge days: 3
                Pre-discount charge: $8.97
                Discount percent: 0%
                Discount amount: $0.0
                Final charge: $8.97
                """;

        assertEquals(result, outputStreamCaptor.toString());

    }

    @Test
    public void test5() {
        String[] testArgument = {"JAKR", "7/2/15", "9", "0"};
        ToolRental.main(testArgument);

        String result =
                """
                Tool code: JAKR
                Tool type: Jackhammer
                Tool brand: Ridgid
                Rental days: 9
                Check out date: 7/2/15
                Due date: 7/11/15
                Daily rental charge: $2.99
                Charge days: 5
                Pre-discount charge: $14.95
                Discount percent: 0%
                Discount amount: $0.0
                Final charge: $14.95
                """;

        assertEquals(result, outputStreamCaptor.toString());

    }

    @Test
    public void test6() {
        String[] testArgument = {"JAKR", "7/2/20", "4", "50"};
        ToolRental.main(testArgument);

        String result =
                """
                Tool code: JAKR
                Tool type: Jackhammer
                Tool brand: Ridgid
                Rental days: 4
                Check out date: 7/2/20
                Due date: 7/6/20
                Daily rental charge: $2.99
                Charge days: 1
                Pre-discount charge: $2.99
                Discount percent: 50%
                Discount amount: $1.5
                Final charge: $1.49
                """;

        assertEquals(result, outputStreamCaptor.toString());

    }

    @Test
    public void validRentalDaysInput() {
        String[] testArgument = {"JAKR", "9/3/15", "5", "50"};
        ToolRental.main(testArgument);

        assertNotEquals("Error: Rental day count must be 1 or greater\n", outputStreamCaptor.toString());
    }

    @Test
    public void invalidRentalDaysInput() {
        String[] testArgument = {"JAKR", "9/3/15", "-5", "50"};
        ToolRental.main(testArgument);

        assertEquals("Error: Rental day count must be 1 or greater\n", outputStreamCaptor.toString());
    }

    @Test
    public void validDiscountInput() {
        String[] testArgument = {"JAKR", "9/3/15", "5", "50"};
        ToolRental.main(testArgument);

        assertNotEquals("Error: Discount percent must be within 0-100\n", outputStreamCaptor.toString());
    }

    @Test
    public void invalidDiscountInput() {
        String[] testArgument = {"JAKR", "9/3/15", "5", "101"};
        ToolRental.main(testArgument);

        assertEquals("Error: Discount percent must be within 0-100\n", outputStreamCaptor.toString());
    }
}
