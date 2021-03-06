/**
 * @author: Amir Eleyan
 * ID: 1191076
 * At: 12/4/2021  4:32 AM
 */
package GUI;

import Shares.Buying;
import Shares.DailyPrice;
import Shares.Utilities;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lists.LinkedList;
import lists.LinkedQueues;
import lists.LinkedStacks;
import lists.Node;

import java.util.Date;

public class SellingGUI {
    private static TextField txtNumberOfShares;
    private static ComboBox<String> chxCompanyName;
    private static Label lblShares, lblCompany, lblAccountingPrinciple;
    private static Button btSell, btClose;
    private static ComboBox<String> accountingPrinciple;

    public static void Sell(LinkedList<DailyPrice> linkedList) {

        // style for labels
        String styleLbl = "-fx-text-fill:#000000; -fx-background-color:#ffffff;-fx-font-weight: BOLd; -fx-font-size:15; ";

        //style for textFields
        String styleTxt = "-fx-background-color: #ffffff; -fx-border-width: 0px0px2px0px;" +
                " -fx-border-color: #000000; -fx-font-size:16; -fx-text-fill: #000000;";

        // Style for buttons
        String styleBt = "-fx-background-color: #ffffff;" + "-fx-font-size:18;-fx-border-width: 1; -fx-border-color: #000000;" +
                "-fx-text-fill: #000000; -fx-font-family: 'Times New Roman'; ";

        // Style for hover buttons
        String styleHoverBt = "-fx-background-color: #000000; " + "-fx-font-size:18;-fx-border-width: 1; -fx-border-color: #000000;" +
                "-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman'; ";

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Selling Shares");

        // gridPane for arrange labels and testFields
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #ffffff;");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(15);
        pane.setHgap(8);
        pane.setPadding(new Insets(5, 5, 5, 5));


        lblAccountingPrinciple = new Label("Accounting Principle:");
        lblAccountingPrinciple.setStyle(styleLbl);


        accountingPrinciple = new ComboBox<>();
        accountingPrinciple.getItems().addAll("Sell old shares first", "Sell new shares first");
        accountingPrinciple.setPromptText("Select Accounting Principle: ");
        accountingPrinciple.setEditable(false);
        accountingPrinciple.setStyle("-fx-background-color: #ffffff; -fx-border-width: 0px0px2px0px;" +
                " -fx-border-color: #000000;-fx-font-weight: BOLd;-fx-font-size:15;");

        lblShares = new Label("\nNumber of Shares:   ");
        lblShares.setStyle(styleLbl);


        lblCompany = new Label("Company Name:");
        lblCompany.setStyle(styleLbl);


        chxCompanyName = new ComboBox<>();
        chxCompanyName.setPromptText("\nSelect the company:");
        chxCompanyName.setEditable(false);
        chxCompanyName.setMinWidth(250);
        chxCompanyName.setStyle("-fx-background-color: #ffffff; -fx-border-width: 0px0px2px0px; -fx-border-color:#000000;" +
                "-fx-font-weight: BOLd;-fx-font-size:15;");

        // text filed to get the value of the numberOfShares
        txtNumberOfShares = new TextField();
        txtNumberOfShares.setPromptText("Enter # shares");
        txtNumberOfShares.setMinWidth(200);
        txtNumberOfShares.setStyle(styleTxt);

        // set values of the comboBox
        Node<DailyPrice> current = linkedList.getHead();
        while (current != null) {
            chxCompanyName.getItems().add(current.getData().getCompanyName());
            current = current.getNext();

        }
        pane.add(lblAccountingPrinciple, 0, 0);
        pane.add(accountingPrinciple, 1, 0);

        pane.add(lblShares, 0, 1);
        pane.add(txtNumberOfShares, 1, 1);

        pane.add(lblCompany, 0, 2);
        pane.add(chxCompanyName, 1, 2);

        btSell = new Button("Sell");
        btSell.setMinWidth(80);
        btSell.setStyle(styleBt);
        btSell.setOnMouseEntered(e -> btSell.setStyle(styleHoverBt));
        btSell.setOnMouseExited(e -> btSell.setStyle(styleBt));

        btSell.setOnAction(e -> {
            if (accountingPrinciple.getValue() == null) {
                Message.displayMassage("Warning", " Please select the account ");

            } else if (txtNumberOfShares.getText().trim().isEmpty()) {
                Message.displayMassage("Warning", " Please enter the number of shares ");

            } else if (!Utilities.isNumber(txtNumberOfShares.getText().trim())) {
                Message.displayMassage("Error", " The number of shares is invalid ");
                txtNumberOfShares.clear();

            } else if (chxCompanyName.getValue() == null) {
                Message.displayMassage("Warning", " Please select the company ");

            } else {
                if (Utilities.buyingQueues.isEmpty()) {
                    Message.displayMassage("Warning", " You do not currently have any shares to sell ");
                    txtNumberOfShares.clear();
                } else {

                    DailyPrice searchCompany = Utilities.dailyPriceLinkedList
                            .search(new DailyPrice(chxCompanyName.getValue().trim(), 0));

                    int numberOfShares = Integer.parseInt(txtNumberOfShares.getText().trim());

                    // queue
                    if (accountingPrinciple.getValue().equals("Sell old shares first")) {
                        sellFromQueue(searchCompany, numberOfShares);
                    } // stacks
                    else {
                        sellFromStacks(searchCompany, numberOfShares);
                    }

                }
            }

        });

        // button for close the window
        btClose = new Button("Close");
        btClose.setMinWidth(80);
        btClose.setStyle(styleBt);
        btClose.setOnMouseEntered(e -> btClose.setStyle(styleHoverBt));
        btClose.setOnMouseExited(e -> btClose.setStyle(styleBt));
        btClose.setOnAction(e -> window.close());


        // HBox for button
        HBox hBox = new HBox(60);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.getChildren().addAll(btSell, btClose);

        // VBox
        VBox vBox = new VBox(35);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setStyle("-fx-background-color: #ffffff;");

        vBox.getChildren().addAll(pane, hBox);

        window.setScene(new Scene(vBox));
        window.setMinWidth(380);
        window.setMinHeight(290);
        window.setResizable(false);
        window.show();
    }

    // Return string that represent lost process
    private static String loss(float lost, String buyingDate, String companyName) {
        return "* On " + Utilities.buyingDate(new Date()) + " you lost $ " + lost + " in the shares you\nbought on " + buyingDate + " from " + companyName + " company\n\n";
    }

    // Return string that represent no loss no gain
    private static String sameCapital(int numberOfShares, String buyingDate, String companyName) {
        return "* On " + Utilities.buyingDate(new Date()) + " you sold " + numberOfShares + " shares in the same capital\nwhich you bought from"
                + companyName + " company on " + buyingDate + "\n\n";
    }

    // Return string that represent gain process
    private static String earned(float profit, String buyingDate, String companyName) {
        return "* On " + Utilities.buyingDate(new Date()) + " you earned  $ " + profit + " in the shares you\nbought on " + buyingDate + " from " + companyName + " company\n\n";
    }

    /**
     * Sell shares from queue
     */
    private static void sellFromQueue(DailyPrice searchCompany, int numberOfShares) {

        Node<Buying> current = Utilities.buyingQueues.getFirst();
        float totalCapital = 0;
        LinkedQueues<Buying> tempBuyingQueue = new LinkedQueues<>();
        float dailyPrice = searchCompany.getSharesSalePrice();
        String details = "";
        boolean flag = true;//to check if he bought shares from this company or not

        while (current != null) {
            // current  = 130, x, 4.5, 15/4/2021
            int sharesBuying = current.getData().getNumberOfShares();// 130
            String companyN = current.getData().getCompanyName();// x
            float priceBuying = current.getData().getSharesBuyingPrice(); // 4.5
            String strDate = current.getData().getStringDate();// 15/4/2021

            if (companyN.equals(searchCompany.getCompanyName())) {
                // The number of shares required for sale is >= than the available shares in current node
                if (numberOfShares >= sharesBuying) {

                    current = current.getNext();
                    Utilities.buyingQueues.dequeue();// sell shares in this node

                    // calculate gain or lost
                    float gain_lost = (sharesBuying * (dailyPrice - priceBuying));

                    if (gain_lost < 0) {//loss
                        details += loss(gain_lost * -1, strDate, companyN);
                    } else if (gain_lost == 0) { // no change on the capital
                        details += sameCapital(sharesBuying, strDate, companyN);
                    } else {// earned
                        details += earned(gain_lost, strDate, companyN);
                    }

                    // Reducing the number of shares required for sale
                    numberOfShares = numberOfShares - sharesBuying;

                    // add capital for this selling to the total capital in this movement
                    totalCapital += gain_lost;

                    flag = false;

                } else { // The number of shares required for sale is less than the available shares

                    current.getData().setNumberOfShares(sharesBuying - numberOfShares);// update data (# buying shares)
                    float gain_lost = (numberOfShares * (dailyPrice - priceBuying));// calculate gain or lost

                    if (gain_lost < 0) {//loss
                        details += loss(gain_lost * -1, strDate, companyN);
                    } else if (gain_lost == 0) { // no change on the capital
                        details += sameCapital(numberOfShares, strDate, companyN);
                    } else {// earned
                        details += earned(gain_lost, strDate, companyN);
                    }

                    // I sold all shares that required to sell
                    numberOfShares = 0;

                    // add capital for this selling to the total capital in this movement
                    totalCapital += gain_lost;
                    flag = false;
                    break;
                }

            } else if (numberOfShares > 0) {// There are still shares to be sold them
                tempBuyingQueue.enqueue(Utilities.buyingQueues.dequeue());
                current = Utilities.buyingQueues.getFirst();
            } else
                break;

        }//end while

        if (!tempBuyingQueue.isEmpty()) { // update queue
            tempBuyingQueue.merge(Utilities.buyingQueues);
            Utilities.buyingQueues = tempBuyingQueue;
        }

        if (flag) {// There are no shares that have been purchased from this company
            Message.displayMassage("Warning", (" You don't have shares from ") + (searchCompany.getCompanyName()) +
                    (" company to sell them.\n"));
        } else {
            details += "At " + Utilities.buyingDate(new Date()) + " Total capital: ";

            if (totalCapital < 0) {
                details += " you lost $ " + (totalCapital * -1);
            } else if (totalCapital == 0) {
                details += "" + totalCapital;
            } else {
                details += " you earned $ " + totalCapital;
            }

            Utilities.totalCapital += totalCapital;//Capital gains and losses
            Utilities.report += details + "\n______________________________________________\n";
            Utilities.buyingStacks.fillFromQueue(Utilities.buyingQueues.getFirst()); // update stacks

            // update data in the table view
            MainInterface.updateTable(Utilities.buyingQueues);

            // display details to this movement selling
            Details.viewDetails(details);

            // The number of shares required for sale is more than the available shares
            if (numberOfShares > 0)
                Message.displayMassage("Warning", " You do not have enough shares to sell.\n So there are " + numberOfShares + " shares not sold ");
        }
        txtNumberOfShares.clear();
    }

    /**
     * Sell shares from stacks
     */
    private static void sellFromStacks(DailyPrice searchCompany, int numberOfShares) {
        Node<Buying> current = Utilities.buyingStacks.getTopItem();
        float totalCapital = 0;
        LinkedStacks<Buying> tempBuyingStacks = new LinkedStacks<>();
        float dailyPrice = searchCompany.getSharesSalePrice();
        String details = "";
        boolean flag = true;//to check if he bought shares from this company or not

        while (current != null) {
            // current  = 130, x, 4.5, 15/4/2021
            int sharesBuying = current.getData().getNumberOfShares();// 130
            String companyN = current.getData().getCompanyName();// x
            float priceBuying = current.getData().getSharesBuyingPrice();// 4.5
            String strDate = current.getData().getStringDate();// 15/4/2021

            if (companyN.equals(searchCompany.getCompanyName())) {
                // The number of shares required for sale is >= than the available shares in current node
                if (numberOfShares >= sharesBuying) {

                    current = current.getNext();
                    Utilities.buyingStacks.pop(); // sell shares in this node

                    // calculate gain or lost
                    float gain_lost = (sharesBuying * (dailyPrice - priceBuying));

                    if (gain_lost < 0) {//loss
                        details += loss(gain_lost * -1, strDate, companyN);
                    } else if (gain_lost == 0) { // no change on the capital
                        details += sameCapital(sharesBuying, strDate, companyN);
                    } else {// earned
                        details += earned(gain_lost, strDate, companyN);
                    }

                    // Reducing the number of shares required for sale
                    numberOfShares = numberOfShares - sharesBuying;

                    // add capital for this selling to the total capital in this movement
                    totalCapital += gain_lost;
                    flag = false;

                } else { // The number of shares required for sale is less than the available shares

                    current.getData().setNumberOfShares(sharesBuying - numberOfShares);// update data (# buying shares)
                    float gain_lost = (numberOfShares * (dailyPrice - priceBuying));// calculate gain or lost

                    if (gain_lost < 0) {//loss
                        details += loss(gain_lost * -1, strDate, companyN);
                    } else if (gain_lost == 0) { // no change on the capital
                        details += sameCapital(numberOfShares, strDate, companyN);
                    } else {// earned
                        details += earned(gain_lost, strDate, companyN);
                    }

                    // I sold all shares that required to sell
                    numberOfShares = 0;

                    // add capital for this selling to the total capital in this movement
                    totalCapital += gain_lost;
                    flag = false;
                    break;
                }
            } else if (numberOfShares > 0) { // There are still shares to be sold them
                tempBuyingStacks.push(Utilities.buyingStacks.pop());
                current = Utilities.buyingStacks.getTopItem();
            } else
                break;

        }//end while

        Utilities.buyingStacks.append(tempBuyingStacks.getTopItem()); // update stacks

        if (flag) { // There are no shares that have been purchased from this company
            Message.displayMassage("Warning", (" You don't have shares from ") + (searchCompany.getCompanyName()) +
                    (" company to sell them.\n"));
        } else {

            details += "At " + Utilities.buyingDate(new Date()) + " Total capital: ";

            if (totalCapital < 0) { // loss in this selling
                details += " you lost $ " + (totalCapital * -1);
            } else if (totalCapital == 0) { // no loss no earned
                details += "" + totalCapital;
            } else { // earned in this selling
                details += " you earned $ " + totalCapital;
            }

            Utilities.totalCapital += totalCapital;//Capital gains and losses
            Utilities.report += details + "\n______________________________________________\n";
            Utilities.buyingQueues.fillFromStacks(Utilities.buyingStacks.getTopItem()); // update queue list depend on the stacks
            MainInterface.updateTable(Utilities.buyingQueues);// update data in table view after sell
            Details.viewDetails(details);// display details of this movement selling

            if (numberOfShares > 0)// The number of shares required for sale is more than the available shares
                Message.displayMassage("Warning", " You do not have enough shares to sell.\n So there are " + numberOfShares + " shares not sold ");
        }
        txtNumberOfShares.clear();
    }
}
