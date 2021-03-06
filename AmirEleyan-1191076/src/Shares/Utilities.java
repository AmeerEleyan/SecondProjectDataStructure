/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 10/4/2021  3:52 AM
 */
package Shares;

import lists.LinkedList;
import lists.LinkedQueues;
import lists.LinkedStacks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public final class Utilities {

    public static LinkedStacks<Buying> buyingStacks = new LinkedStacks<>();
    public static LinkedQueues<Buying> buyingQueues = new LinkedQueues<>();
    public static LinkedList<DailyPrice> dailyPriceLinkedList = new LinkedList<>();
    public static float totalCapital = 0;
    public static String report = "";

    // to prevent create obj from this class
    private Utilities() {
    }

    //************************************************************************************************

    /**
     * Methode to read data from file iteratively
     */
    public static void readPurchaseDataFromAFile(String fileName, Object list) {
        File file = new File(fileName); // instance of file
        try {
            Scanner input = new Scanner(file); // instance of scanner for read data from file
            if (file.length() == 0) {
                // no data in file
                GUI.Message.displayMassage("Warning", "  There are No records in file " + fileName + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens

                while (input.hasNext()) { // read line of data
                    try {
                        String temp = input.nextLine();
                        if (list instanceof LinkedList) {
                            dailyPriceLinkedList.insertAtLast(new DailyPrice(temp));
                        } else {
                            buyingQueues.enqueue(new Buying(temp));
                            buyingStacks.push(new Buying(temp));
                        }

                        line++; // increment the line by one

                    } catch (Exception ex) {
                        // the record in the file has a problem
                        // e.g. he does not have a company or The data arrangement is not in the right place
                        GUI.Message.displayMassage("Warning", " Invalid format in line # " + line + " in file " + fileName + "  ");
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            GUI.Message.displayMassage("Error", " The system can NOT find the file " + fileName + "  ");
        }
    }

    /**
     * Return current data as string
     */
    public static String buyingDate(Date buyingDate) {
        return buyingDate.getDate() + "/" + (buyingDate.getMonth() + 1) + "/" + (buyingDate.getYear() + 1900);
    }

    /**
     * To check the value of the entered numberOfShares if contain only digits or not
     */
    public static boolean isNumber(String number) {
        /* To check the entered number of shares, that it consists of
           only digits
         */
        return (number.matches("\\d+") && Integer.parseInt(number) > 0);
    }

    /**
     * To check the value of the entered company name that if contain only char ot not
     */
    public static boolean isCompanyName(String companyN) {
        return companyN.matches("[a-zA-Z]+");
    }

    /**
     * To check the value of the entered daily price that if fraction ot not
     */
    public static boolean isDailyPrice(String dailyPrice) {
        try {
            Float.parseFloat(dailyPrice);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
