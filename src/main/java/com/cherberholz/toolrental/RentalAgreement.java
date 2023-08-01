package com.cherberholz.toolrental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RentalAgreement {
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yy");
    private final Tool tool;
    private final Date checkoutDate;
    private final int rentalDays;
    private final int discount;
    private final Date dueDate;
    private final int chargeDays;
    private final double preDiscountCharge;
    private final double discountAmount;
    private final double finalCharge;

    public RentalAgreement(String code, String checkoutDate, int rentalDays, int discount) throws ParseException {
        tool = new Tool(code);
        this.checkoutDate = simpleDateFormat.parse(checkoutDate);
        this.rentalDays = rentalDays;
        this.discount = discount;
        dueDate = calculateDueDate();
        chargeDays = calculateChargeDays();
        preDiscountCharge = roundValue((double) chargeDays * tool.getToolFees().getDailyCharge());
        discountAmount = roundValue(preDiscountCharge * ((double) this.discount / 100.0f));
        finalCharge = roundValue(preDiscountCharge - discountAmount);
    }

    /**
     * checkout will list all the generated values from the rental agreement
     *
     */
    public void checkout() {
        System.out.println("Tool code: " + tool.getToolCode());
        System.out.println("Tool type: " + tool.getType());
        System.out.println("Tool brand: " + tool.getBrand());
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Check out date: " + simpleDateFormat.format(checkoutDate));
        System.out.println("Due date: " + simpleDateFormat.format(dueDate));
        System.out.println("Daily rental charge: $" + tool.getToolFees().getDailyCharge());
        System.out.println("Charge days: " + chargeDays);
        System.out.println("Pre-discount charge: $" + preDiscountCharge);
        System.out.println("Discount percent: " + discount + "%");
        System.out.println("Discount amount: $" + discountAmount);
        System.out.println("Final charge: $" + finalCharge);
    }

    /**
     * calculateDueDate main purpose is to create a Date value specifying the due date of the tool
     *
     */
    private Date calculateDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkoutDate);
        calendar.add(Calendar.DATE, rentalDays);
        return calendar.getTime();
    }

    /**
     * calculateChargeDays will provide the number of days that can be charged the daily rental fee.
     *                     this is done by checking if the tool has fees for renting on weekends or holidays
     *                     and adding up the number of valid days
     *
     */
    private int calculateChargeDays() {
        int count = rentalDays;
        boolean weekendCharge = tool.getToolFees().isWeekendCharge();
        boolean holidayCharge = tool.getToolFees().isHolidayCharge();

        // if not charging for holidays, then remove from days charged
        if (!holidayCharge) {
            count -= calculateHolidays();
        }

        // if not charging for weekends, then remove from days charged
        if (!weekendCharge) {
            count -= calculateWeekends();
        }

        return count;
    }

    /**
     * calculateHolidays establishes the checkout date and due date of the tool. It then goes through each year
     *                   specified within the range of rental days and check if a holiday is observed if within
     *                   that month. Returns the number of holidays found within the range of rental days. This value
     *                   will be removed from the number of chargeable days.
     *
     */
    private int calculateHolidays() {
        int holidayCount = 0;
        Calendar checkoutCalendar = Calendar.getInstance();
        Calendar dueDateCalendar = Calendar.getInstance();
        checkoutCalendar.setTime(checkoutDate);
        dueDateCalendar.setTime(dueDate);
        int startYear = checkoutCalendar.get(Calendar.YEAR);
        int endYear = dueDateCalendar.get(Calendar.YEAR);
        int checkoutMonth = checkoutCalendar.get(Calendar.MONTH);
        int dueDateMonth = dueDateCalendar.get(Calendar.MONTH);

        // covers scenario where rental days may go into the new year
        for (int i = startYear; i <= endYear; ++i) {
            // set the observed holidays and see if before/after checkoutDate and dueDate
            if (checkoutMonth == Calendar.JULY || dueDateMonth == Calendar.JULY) {
                Date fourthOfJuly = getFourthOfJuly(i);
                if (fourthOfJuly.compareTo(checkoutDate) >= 0 && fourthOfJuly.compareTo(dueDate) <= 0) {
                    ++holidayCount;
                }
            }
            if (checkoutMonth == Calendar.SEPTEMBER || dueDateMonth == Calendar.SEPTEMBER) {
                Date laborDay = getLaborDay(i);
                if (laborDay.compareTo(checkoutDate) >= 0 && laborDay.compareTo(dueDate) <= 0) {
                    ++holidayCount;
                }
            }
        }

        return holidayCount;
    }

    /**
     * calculateWeekends loops through the range of rental days and records number of weekends encountered
     *
     */
    private int calculateWeekends() {
        int weekendCount = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkoutDate);

        while (calendar.getTime().compareTo(dueDate) <= 0) {
            int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfTheWeek == Calendar.SATURDAY || dayOfTheWeek == Calendar.SUNDAY) {
                ++weekendCount;
            }
            calendar.add(Calendar.DATE, 1);
        }

        return weekendCount;
    }

    /**
     * getFourthOfJuly creates a calendar instance of the day and then checks to see if the holiday falls on
     *                 a weekend. If this is the case then if Saturday the holiday will be observed on
     *                 Friday, July 3rd and if Sunday it will be observed on Monday, July 5th
     *
     * @param year   input providing the year that the fourth of July is to be observed in
     *
     */
    private Date getFourthOfJuly(int year) {
        Calendar fourthOfJuly = Calendar.getInstance();
        fourthOfJuly.set(year, Calendar.JULY, 4);
        int dayOfWeek = fourthOfJuly.get(Calendar.DAY_OF_WEEK);
        // if holiday is on weekend then set observed holiday to nearest weekday
        if (dayOfWeek == Calendar.SUNDAY) {
            fourthOfJuly.clear();
            fourthOfJuly.set(year, Calendar.JULY, 5);
        } else if (dayOfWeek == Calendar.SATURDAY) {
            fourthOfJuly.clear();
            fourthOfJuly.set(year, Calendar.JULY, 3);
        }

        return fourthOfJuly.getTime();
    }

    /**
     * getLaborDay creates a calendar instance of the holiday. Labor day is always the 1st Monday of September
     *
     * @param year   input providing the year that Labor Day is to be observed in
     *
     */
    private Date getLaborDay(int year) {
        Calendar laborDay = Calendar.getInstance();
        laborDay.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        laborDay.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        laborDay.set(Calendar.MONTH, Calendar.SEPTEMBER);
        laborDay.set(Calendar.YEAR, year);
        return laborDay.getTime();
    }

    /**
     * roundValue takes an un-rounded number and rounds upward if greater than half with a precision of two decimal places
     *
     * @param originalValue   un-rounded number
     *
     */
    private double roundValue(double originalValue) {
        BigDecimal roundedValue = BigDecimal.valueOf(originalValue);
        return roundedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
