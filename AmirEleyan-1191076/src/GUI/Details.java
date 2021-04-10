package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Details {

    public static void viewDetails(String string) {
        // Style for buttons
        String styleBt = "-fx-background-color: #ffffff;" + "-fx-font-size:18;-fx-border-width: 1; -fx-border-color: #000000;" +
                "-fx-text-fill: #000000; -fx-font-family: 'Times New Roman'; ";

        // Style for hover buttons
        String styleHoverBt = "-fx-background-color: #000000; " + "-fx-font-size:18;-fx-border-width: 1; -fx-border-color: #000000;" +
                "-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman'; ";

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Details");

        // VBox
        VBox vBox = new VBox(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setStyle("-fx-background-color: #ffffff;");

        // button for close the window
        Button btClose = new Button("Close");
        btClose.setMinWidth(80);
        btClose.setStyle(styleBt);
        btClose.setOnMouseEntered(e -> btClose.setStyle(styleHoverBt));
        btClose.setOnMouseExited(e -> btClose.setStyle(styleBt));
        btClose.setOnAction(e -> window.close());

        TextArea taDetails = new TextArea(string);
        taDetails.setWrapText(true);
        taDetails.setPrefColumnCount(15);
        taDetails.setPrefHeight(160);
        taDetails.setPrefWidth(450);
        taDetails.setEditable(false);
        taDetails.setFont(new Font("Times New Roman", 16));
        taDetails.setStyle("-fx-highlight-fill:blue;  -fx-highlight-text-fill: #ffffff;" +
                "  -fx-text-fill: black; -fx-background-color: black;");

        vBox.getChildren().addAll(taDetails, btClose);

        window.setScene(new Scene(vBox));
        window.setMinWidth(450);
        window.setMinHeight(330);
        window.setResizable(false);
        window.show();
    }
}
