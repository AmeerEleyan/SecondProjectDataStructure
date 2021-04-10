package Shares;

import lists.LinkedList;
import lists.LinkedQueues;
import lists.LinkedStacks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public final class Utilities {

    private static final LinkedStacks<Buying> buyingLinkedStacks = new LinkedStacks<>();
    private static final LinkedQueues<Buying> buyingLinkedQueues = new LinkedQueues<>();
    private static final LinkedList<DailyPrice> dailyPriceLinkedList = new LinkedList<>();
    private LinkedStacks<Buying> tempBuyingStacks;
    private LinkedQueues<Buying> tempBuyingQueue;

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
                // Massage.displayMassage("Warning", "  There are No records in file " + fileName + "  "); // no data in file
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
                        // e.g. he does not have a grade or The data arrangement is not in the right place
                        //    Massage.displayMassage("Warning", " Error reading in student info in line # " + line + " in file " + fileName + "  ");
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            //  Massage.displayMassage("Error", " The system can NOT find the file " + fileName + "  ");
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
    private static String buyingDate(Date buyingDate) {
        return buyingDate.getDate() + "/" + (buyingDate.getMonth() + 1) + "/" + (buyingDate.getYear() + 1900);
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
