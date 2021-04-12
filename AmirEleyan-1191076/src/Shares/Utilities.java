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

    public static LinkedStacks<Buying> buyingLinkedStacks = new LinkedStacks<>();
    public static LinkedQueues<Buying> buyingLinkedQueues = new LinkedQueues<>();
    public static LinkedStacks<Buying> tempBuyingStacks;
    public static LinkedQueues<Buying> tempBuyingQueue;
    public static LinkedList<DailyPrice> dailyPriceLinkedList = new LinkedList<>();
    public static float totalCapital = 0;
    public static String report ="";

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
                GUI.Message.displayMassage("Warning", "  There are No records in file " + fileName + "  ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens

                while (input.hasNext()) { // read line of data
                    try {
                        String temp = input.nextLine();
                        if (list instanceof LinkedList) {
                            dailyPriceLinkedList.insertAtLast(new DailyPrice(temp));
                        } else {
                            buyingLinkedQueues.enqueue(new Buying(temp));
                            buyingLinkedStacks.push(new Buying(temp));
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

    public static void purchasing(Buying buying, Object list) {
        if (list instanceof LinkedQueues)
            buyingLinkedQueues.enqueue(buying);
        else if (list instanceof LinkedStacks)
            buyingLinkedStacks.push(buying);
    }

    /**
     * Return current data as string
     */
    public static String buyingDate(Date buyingDate) {
        return buyingDate.getDate() + "/" + (buyingDate.getMonth() + 1) + "/" + (buyingDate.getYear() + 1900);
    }

    /**
     * To check the value of the entered numberOfShares if it is correct or not
     */
    public static boolean isNumber(String txt) {

        /* To check the entered number of shares, that it consists of
           only digits
         */
        if (txt.matches("\\d+") && Integer.parseInt(txt) > 0)
            return true;
        return false;
    }

    public static void main(String[] arg) {
       /* Utilities.readPurchaseDataFromAFile("shares.txt", new Object());
        Utilities.readPurchaseDataFromAFile("dailyPrice.txt", dailyPriceLinkedList);

        Utilities.buyingLinkedQueues.displayQueue();
        Utilities.buyingLinkedStacks.displayStacks();
        Utilities.dailyPriceLinkedList.printList();*/
        Date date = new Date();
        System.out.println(Utilities.buyingDate(new Date()));
    }

}
