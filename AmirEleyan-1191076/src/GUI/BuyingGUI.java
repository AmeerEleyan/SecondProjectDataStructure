/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 10/4/2021  6:37 PM
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
import lists.Node;

import java.util.Date;

public class BuyingGUI {
    private static TextField txtNumberOfShares, txtDate;
    private static ComboBox<String> chxCompanyName;
    private static Label lblShares, lblCompany, lblDate;
    private static Button btBuy, btClose;

    public static void Buy(LinkedList<DailyPrice> linkedList) {

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
        window.setTitle("Buying shares");

        // gridPane for arrange labels and testFields
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: #ffffff;");
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(15);
        pane.setHgap(8);
        pane.setPadding(new Insets(5, 5, 5, 5));

        lblShares = new Label("\nNumber of Shares:   ");
        lblShares.setStyle(styleLbl);


        lblCompany = new Label("Company Name:");
        lblCompany.setStyle(styleLbl);

        chxCompanyName = new ComboBox<>();
        chxCompanyName.setPromptText("\nSelect the company:");
        chxCompanyName.setEditable(false);
        chxCompanyName.setPadding(new Insets(0, 0, 5, 0));
        chxCompanyName.setMinWidth(180);
        chxCompanyName.setMinHeight(0);
        chxCompanyName.setStyle("-fx-background-color: #ffffff; -fx-border-width: 0px0px2px0px; -fx-border-color:#000000;" +
                "-fx-font-weight: BOLd;-fx-font-size:15;");

        // text filed to get the value of the numberOfShares
        txtNumberOfShares = new TextField();
        txtNumberOfShares.setPromptText("Enter # shares");
        txtNumberOfShares.setMaxWidth(chxCompanyName.getMinWidth() + 15);
        txtNumberOfShares.setStyle(styleTxt);

        // set values of the comboBox
        Node<DailyPrice> current = linkedList.getHead();
        while (current != null) {
            chxCompanyName.getItems().add(current.getData().getCompanyName());
            current = current.getNext();

        }

        lblDate = new Label("Date:    ");
        lblDate.setStyle(styleLbl);

        txtDate = new TextField();
        txtDate.setEditable(false);
        txtDate.setMaxWidth(chxCompanyName.getMinWidth() + 15);
        txtDate.setStyle(styleTxt);
        txtDate.setText(Utilities.buyingDate(new Date()));

        pane.add(lblCompany, 0, 0);
        pane.add(chxCompanyName, 1, 0);

        pane.add(lblShares, 0, 1);
        pane.add(txtNumberOfShares, 1, 1);

        pane.add(lblDate, 0, 2);
        pane.add(txtDate, 1, 2);

        // button for close the window
        btClose = new Button("Close");
        btClose.setMinWidth(80);
        btClose.setStyle(styleBt);
        btClose.setOnMouseEntered(e -> btClose.setStyle(styleHoverBt));
        btClose.setOnMouseExited(e -> btClose.setStyle(styleBt));
        btClose.setOnAction(e -> window.close());

        btBuy = new Button("Buy");
        btBuy.setMinWidth(80);
        btBuy.setStyle(styleBt);
        btBuy.setOnMouseEntered(e -> btBuy.setStyle(styleHoverBt));
        btBuy.setOnMouseExited(e -> btBuy.setStyle(styleBt));

        btBuy.setOnAction(e -> {
            if (chxCompanyName.getValue() == null) {
                Message.displayMassage("Warning", " Please select the company ");
            } else if (txtNumberOfShares.getText().trim().isEmpty()) {
                Message.displayMassage("Warning", " Please enter the number of shares ");
            } else if (!Utilities.isNumber(txtNumberOfShares.getText().trim())) {
                Message.displayMassage("Error", " The number of shares is invalid ");
                txtNumberOfShares.clear();
            } else {
                DailyPrice searchCompany = Utilities.dailyPriceLinkedList
                        .search(new DailyPrice(chxCompanyName.getValue().trim(), 0));

                Date currentDate = new Date();
                Buying buying = new Buying();

                buying.setNumberOfShares(Integer.parseInt(txtNumberOfShares.getText().trim()));
                buying.setSharesBuyingPrice(searchCompany.getSharesSalePrice());
                buying.setCompanyName(searchCompany.getCompanyName());
                buying.setStringDate(currentDate.getDate(),
                        currentDate.getMonth() + 1, currentDate.getYear() + 1900);

                Utilities.buyingLinkedQueues.enqueue(buying);
                Utilities.buyingLinkedStacks.push(buying);

                Interfaces.updateTable(Utilities.buyingLinkedQueues);

                Message.displayMassage("Success", (txtNumberOfShares.getText().trim() +
                        " shares of " + chxCompanyName.getValue().trim() + " Company were purchased successfully "));
                txtNumberOfShares.clear();
            }

        });


        // HBox for button
        HBox hBox = new HBox(60);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.getChildren().addAll(btBuy, btClose);

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

}
