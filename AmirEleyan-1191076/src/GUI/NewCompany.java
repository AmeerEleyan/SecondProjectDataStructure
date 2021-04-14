package GUI;

import Shares.DailyPrice;
import Shares.Utilities;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class NewCompany {
    private static Label lblCompanyN, lblDailyPrice;
    private static TextField txtCompanyN, txtDailyPrice;
    private static Button btAdd, btClose;

    public static void addNewCompany() {

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

        lblCompanyN = new Label("Company Name:");
        lblCompanyN.setStyle(styleLbl);

        txtCompanyN = new TextField();
        txtCompanyN.setPromptText("Enter company name");
        txtCompanyN.setMinWidth(200);
        txtCompanyN.setStyle(styleTxt);

        lblDailyPrice = new Label("Daily Price:");
        lblDailyPrice.setStyle(styleLbl);

        txtDailyPrice = new TextField();
        txtDailyPrice.setPromptText("Enter daily price");
        txtDailyPrice.setMinWidth(200);
        txtDailyPrice.setStyle(styleTxt);

        pane.add(lblCompanyN, 0, 0);
        pane.add(txtCompanyN, 1, 0);

        pane.add(lblDailyPrice, 1, 0);
        pane.add(txtDailyPrice, 1, 1);

        btAdd = new Button("Add");
        btAdd.setMinWidth(80);
        btAdd.setStyle(styleBt);
        btAdd.setOnMouseEntered(e -> btAdd.setStyle(styleHoverBt));
        btAdd.setOnMouseExited(e -> btAdd.setStyle(styleBt));

        btAdd.setOnAction(e -> {
            if (!txtDailyPrice.getText().trim().isEmpty() && !txtCompanyN.getText().trim().isEmpty()) {
                if (Utilities.isCompanyName(txtCompanyN.getText().trim())) {

                    if (Utilities.isDailyPrice(txtDailyPrice.getText().trim())) {
                        float dailyPrice = Float.parseFloat(txtDailyPrice.getText());
                        Utilities.dailyPriceLinkedList.insertAtLast(new DailyPrice(txtCompanyN.getText(), dailyPrice));

                    } else {
                        Message.displayMassage("Warning", " The daily price is invalid ");
                    }
                } else {
                    Message.displayMassage("Warning", " The company name is invalid ");
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
        hBox.getChildren().addAll(btAdd, btClose);

        // VBox
        VBox vBox = new VBox(35);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setStyle("-fx-background-color: #ffffff;");

        vBox.getChildren().addAll(pane, hBox);

        window.setScene(new Scene(vBox));
        window.setMinWidth(320);
        window.setMinHeight(250);
        window.setResizable(false);
        window.show();


    }
}
