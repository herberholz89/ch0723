package com.cherberholz.toolrental;



// When considering how best to structure the Tool class, I initially considered using Polymorphism,
// this would allow me to have a parent tool class which houses the three main attributes
// and then create child classes based off the tool type which can store specific financial attributes.
// These values could be accessed by overriding methods, returning values based off the class child type.
// I decided against this approach because each tool type class would have the same variables and methods
// and would lead to excessive code if we ever decide to expand our tool list
// ---
// Instead I selected to use Encapsulation where I can use a single ToolFee class within the
// tool class which can be initialized based on the input type. This separates financial attributes from
// the tool attributes and makes it easier to update and troubleshoot.

public class Tool {
    private String code;
    private String type;
    private String brand;
    private ToolFees toolFees;

    // TODO no reason for outside classes to set values in Tool currently, look into changing getter/setter situation
    public Tool(String code) {
        this.setCode(code);
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    public ToolFees getToolFees() { return toolFees; }

    public void setToolFees(ToolFees toolFees) { this.toolFees = toolFees; }

    public static class ToolFees {
        private float dailyCharges;
        private boolean weekdayCharge;
        private boolean weekendCharge;
        private boolean holidayCharge;

        public ToolFees(String type) {

        }

        public float getDailyCharges() { return dailyCharges; }

        public void setDailyCharges(float dailyCharges) { this.dailyCharges = dailyCharges; }

        public boolean isWeekdayCharge() { return weekdayCharge; }

        public void setWeekdayCharge(boolean weekdayCharge) { this.weekdayCharge = weekdayCharge; }

        public boolean isWeekendCharge() { return weekendCharge; }

        public void setWeekendCharge(boolean weekendCharge) { this.weekendCharge = weekendCharge; }

        public  boolean isHolidayCharge() { return holidayCharge; }

        public void setHolidayCharge(boolean holidayCharge) { this.holidayCharge = holidayCharge; }
    }
}
