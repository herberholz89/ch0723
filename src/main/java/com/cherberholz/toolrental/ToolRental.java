package com.cherberholz.toolrental;



import java.text.ParseException;

public class ToolRental {
    /**
     * Upon running the ToolRental program the main function receives expected input, validates input, and then generates
     *      the rental agreement
     *
     * @param args   the args provided to the program upon runtime include the
     *               tool code, rental day count, discount percent, and checkout date
     *               EX: "JAKR" "7/28/23" "5" "50"
     */
    public static void main(String[] args) {
        try {
            String toolCode = args[0];
            String checkoutDate = args[1];
            int rentalDays = Integer.parseInt(args[2]);
            int discount = Integer.parseInt(args[3]);

            validateUserInput(rentalDays, discount);

            RentalAgreement rentalAgreement = new RentalAgreement(toolCode, checkoutDate, rentalDays, discount);
            rentalAgreement.checkout();

        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("No enum constant")) {
                System.out.println("Error: Tool Code not found - " + args[0]);
            } else {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (ParseException e) {
            System.out.println("Checkout Date - Incorrect Format: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * validateUserInput is responsible for making sure user input follows program guidelines
     *
     * @param rentalDays   input stating how many days the tool will be rented
     * @param discount     input stating the given discount percentage
     * @throws Exception   in the event one of the inputs are not valid an exception is thrown
     *
     */
    private static void validateUserInput(int rentalDays, int discount) throws Exception {
        if (rentalDays < 1) {
            throw new Exception("Rental day count must be 1 or greater");
        }

        if (discount < 0 || discount > 100) {
            throw new Exception("Discount percent must be within 0-100");
        }
    }
}