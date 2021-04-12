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
                if (Utilities.buyingLinkedQueues.isEmpty()) {
                    Message.displayMassage("Warning", " You do not currently have any shares to sell ");
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

    private static String loss(float lost, String buyingDate, String companyName) {
        return "* On " + Utilities.buyingDate(new Date()) + " you lost $ " + lost + " in the shares you\nbought on " + buyingDate + " from " + companyName + "\n\n";
    }

    private static String sameCapital(int numberOfShares, String buyingDate, String companyName) {
        return "* On " + Utilities.buyingDate(new Date()) + " you sold " + numberOfShares + " shares in the same capital\nwhich you bought from"
                + companyName + " company on " + buyingDate + "\n\n";
    }

    private static String earned(float profit, String buyingDate, String companyName) {
        return "* On " + Utilities.buyingDate(new Date()) + " you earned  $ " + profit + " in the shares you\nbought on " + buyingDate + " from " + companyName + " company\n\n";
    }

    private static void sellFromQueue(DailyPrice searchCompany, int numberOfShares) {

        Node<Buying> first = Utilities.buyingLinkedQueues.getFirst();
        float total = 0;
        Utilities.tempBuyingQueue = new LinkedQueues<>();
        float dailyPrice = searchCompany.getSharesSalePrice();
        String details = "";

        while (first != null) {

            int sharesBuying = first.getData().getNumberOfShares();
            String companyN = first.getData().getCompanyName();
            float priceBuying = first.getData().getSharesBuyingPrice();
            String strDate = first.getData().getStringDate();

            if (companyN.equals(searchCompany.getCompanyName())) {
                if (numberOfShares >= sharesBuying) {
                    Utilities.buyingLinkedQueues.dequeue();
                    float temp = (sharesBuying * (dailyPrice - priceBuying));
                    if (temp < 0) {//loss
                        details += loss(temp * -1, strDate, companyN);
                    } else if (temp == 0) { // no change on the capital
                        details += sameCapital(numberOfShares, strDate, companyN);
                    } else {// earned
                        details += earned(temp, strDate, companyN);
                    }
                    numberOfShares = numberOfShares - first.getData().getNumberOfShares();
                    total += temp;
                } else {
                    first.getData().setNumberOfShares(sharesBuying - numberOfShares);
                    float temp = (numberOfShares * (dailyPrice - priceBuying));
                    if (temp < 0) {//loss
                        details += loss(temp * -1, strDate, companyN);
                    } else if (temp == 0) { // no change on the capital
                        details += sameCapital(numberOfShares, strDate, companyN);
                    } else {// earned
                        details += earned(temp, strDate, companyN);
                    }
                    total += temp;
                    break;
                }

            }
            if (numberOfShares > 0) {
                Utilities.tempBuyingQueue.enqueue(Utilities.buyingLinkedQueues.dequeue());
                first = Utilities.buyingLinkedQueues.getFirst();
            } else break;

        }
        /*if (numberOfShares > 0) {
            details += ("You do not have enough shares of company ") + (first.getData().getCompanyName()) +
                    (" to sell them.") + (" So, ") + (numberOfShares) + (" remain unsold\n");
        }*/
        details += "At " + Utilities.buyingDate(new Date()) + " Total capital: " + ((total < 0) ? (" you lost " + total * -1) : " you earned " + total);
        Utilities.tempBuyingQueue.merge(Utilities.buyingLinkedQueues.getFirst());
        Utilities.buyingLinkedQueues = Utilities.tempBuyingQueue;
        Utilities.totalCapital += total;
        Utilities.buyingLinkedStacks.fillFromQueue(Utilities.buyingLinkedQueues.getFirst());
        Interfaces.uploadListToTable(Utilities.buyingLinkedQueues);
        Details.viewDetails(details);
        txtNumberOfShares.clear();

    }

    private static void sellFromStacks(DailyPrice searchCompany, int numberOfShares) {
        Node<Buying> first = Utilities.buyingLinkedStacks.getTopItem();
        float total = 0;
        Utilities.tempBuyingStacks = new LinkedStacks<>();
        float dailyPrice = searchCompany.getSharesSalePrice();
        String details = "";

        while (first != null) {

            int sharesBuying = first.getData().getNumberOfShares();
            String companyN = first.getData().getCompanyName();
            float priceBuying = first.getData().getSharesBuyingPrice();
            String strDate = first.getData().getStringDate();

            if (companyN.equals(searchCompany.getCompanyName())) {
                if (numberOfShares >= sharesBuying) {
                    Utilities.buyingLinkedStacks.pop();
                    float temp = (sharesBuying * (dailyPrice - priceBuying));
                    if (temp < 0) {//loss
                        details += loss(temp * -1, strDate, companyN);
                    } else if (temp == 0) { // no change on the capital
                        details += sameCapital(numberOfShares, strDate, companyN);
                    } else {// earned
                        details += earned(temp, strDate, companyN);
                    }
                    numberOfShares = numberOfShares - first.getData().getNumberOfShares();
                    total += temp;
                } else {
                    first.getData().setNumberOfShares(sharesBuying - numberOfShares);
                    float temp = (numberOfShares * (dailyPrice - priceBuying));
                    if (temp < 0) {//loss
                        details += loss(temp * -1, strDate, companyN);
                    } else if (temp == 0) { // no change on the capital
                        details += sameCapital(numberOfShares, strDate, companyN);
                    } else {// earned
                        details += earned(temp, strDate, companyN);
                    }
                    total += temp;
                    break;
                }

            }
            if (numberOfShares > 0) {
                Utilities.tempBuyingStacks.push(Utilities.buyingLinkedStacks.pop());
                first = Utilities.buyingLinkedStacks.getTopItem();
            } else break;

        }
        /*if (numberOfShares > 0) {
            details += ("You do not have enough shares of company ") + (first.getData().getCompanyName()) +
                    (" to sell them.") + (" So, ") + (numberOfShares) + (" remain unsold\n");
        }*/
        details += "At " + Utilities.buyingDate(new Date()) + " Total capital: " + ((total < 0) ? (" you lost " + total * -1) : " you earned " + total);
        Utilities.buyingLinkedStacks.append(Utilities.tempBuyingStacks.getTopItem());
        Utilities.totalCapital += total;
        Utilities.buyingLinkedQueues.fillFromStacks(Utilities.buyingLinkedStacks.getTopItem());
        Interfaces.uploadListToTable(Utilities.buyingLinkedQueues);
        Details.viewDetails(details);
        txtNumberOfShares.clear();
    }
}
