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
    // using enum due to low amount of tool codes provided.
    // *** If this gets significantly larger we will need to consider storing within a database

    private enum ToolCode {
        CHNS,
        LADW,
        JAKD,
        JAKR
    }

    private final ToolCode toolCode;
    private String type;
    private String brand;
    private final ToolFees toolFees;

    public Tool(String code) {
        this.toolCode = ToolCode.valueOf(code); // if string does not match enum value then exception is thrown
        this.setTypeAndBrand();
        this.toolFees = new ToolFees(this.type);
    }

    public ToolCode getToolCode() { return toolCode; }

    public String getType() { return type; }

    /**
     * setTypeAndBrand sets the type and brand of the tool based off the tool code provided as input
     *
     */
    public void setTypeAndBrand() {
        switch (this.toolCode) {
            case CHNS -> {
                this.type = "Chainsaw";
                this.brand = "Stihl";
            }
            case LADW -> {
                this.type = "Ladder";
                this.brand = "Werner";
            }
            case JAKD -> {
                this.type = "Jackhammer";
                this.brand = "DeWalt";
            }
            case JAKR -> {
                this.type = "Jackhammer";
                this.brand = "Ridgid";
            }
        }
    }

    public String getBrand() { return brand; }

    public ToolFees getToolFees() { return toolFees; }

    public static class ToolFees {
        private double dailyCharge;
        private boolean weekendCharge;
        private boolean holidayCharge;

        public ToolFees(String type) {
            this.setTotalFeeValues(type);
        }

        /**
         * setTotalFeeValues takes the type of tool and specifies dailyCharge, weekendCharge, and holidayCharge values
         *
         * @param type   input stating the type of tool.
         *
         */
        private void setTotalFeeValues(String type) {
            switch (type) {
                case "Ladder" -> {
                    this.dailyCharge = 1.99;
                    this.weekendCharge = true;
                    this.holidayCharge = false;
                }
                case "Chainsaw" -> {
                    this.dailyCharge = 1.49;
                    this.weekendCharge = false;
                    this.holidayCharge = true;
                }
                case "Jackhammer" -> {
                    this.dailyCharge = 2.99;
                    this.weekendCharge = false;
                    this.holidayCharge = false;
                }
            }
        }

        public double getDailyCharge() { return dailyCharge; }

        public boolean isWeekendCharge() { return weekendCharge; }

        public  boolean isHolidayCharge() { return holidayCharge; }
    }
}
