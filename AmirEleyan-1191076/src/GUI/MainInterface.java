package GUI;

import Shares.Buying;
import Shares.DailyPrice;
import Shares.Utilities;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lists.LinkedList;
import lists.Node;

public class MainInterface extends Application {
    private static TextField txtTotalShares, txtTotalCompany;
    private Label lblTotalShares, lblTotalCompany;
    private Button btBuy, btSell, btReport, btNewCompany;
    private static TableView<Buying> buyingTableView;
    private static TableView<DailyPrice> dailyPriceTable;


    // Style for buttons
    String styleBt = "-fx-background-color: #ffffff;" + "-fx-font-size:18;-fx-border-width: 1.5; -fx-border-color: #000000;" +
            "-fx-text-fill: #000000; -fx-font-family: 'Times New Roman'; ";

    // Style for hover buttons
    String styleHoverBt = "-fx-background-color: #000000; " + "-fx-font-size:18;-fx-border-width: 1.5; -fx-border-color: #000000;" +
            "-fx-text-fill: #ffffff; -fx-font-family: 'Times New Roman'; ";

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Shares");
        stage.setResizable(false);
        Utilities.readPurchaseDataFromAFile("dailyPrice.txt", Utilities.dailyPriceLinkedList);
        Utilities.readPurchaseDataFromAFile("shares.txt", new Buying());
        stage.setScene(new Scene(allComponents()));
        updateTable(Utilities.buyingQueues);
        updateTable(Utilities.dailyPriceLinkedList);
        stage.setOnCloseRequest(e -> {
            FileUpdate.update("shares.txt", Utilities.buyingQueues);
            FileUpdate.update("dailyPrice.txt", Utilities.dailyPriceLinkedList);
        });
        stage.show();
    }

    //************************************************************************************************

    /**
     * Table view to display dailyPrice
     */
    public static TableView<DailyPrice> dailyPriceTable() {

        dailyPriceTable = new TableView<>();
        dailyPriceTable.setEditable(false);
        dailyPriceTable.setMinWidth(250);
        dailyPriceTable.setMinHeight(420);
        dailyPriceTable.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");


        TableColumn<DailyPrice, String> companyName = new TableColumn<>("Company");
        companyName.setMinWidth(150);
        companyName.setSortable(false);
        companyName.setResizable(false);
        companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        TableColumn<DailyPrice, Float> priceShare = new TableColumn<>("Perice");
        priceShare.setMinWidth(100);
        priceShare.setSortable(false);
        priceShare.setResizable(false);
        priceShare.setCellValueFactory(new PropertyValueFactory<>("sharesSalePrice"));

        dailyPriceTable.getColumns().addAll(companyName, priceShare);
        return dailyPriceTable;
    }

    public static TableView<Buying> buyingTable() {

        buyingTableView = new TableView<>();
        buyingTableView.setEditable(false);
        buyingTableView.setMinWidth(760);
        buyingTableView.setMinHeight(420);
        buyingTableView.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:2; -fx-font-family:" +
                "'Times New Roman'; -fx-font-size:17; -fx-text-fill: #000000; -fx-font-weight: BOLd; ");

        TableColumn<Buying, Integer> sharesColumn = new TableColumn<>("#Shares");
        sharesColumn.setMinWidth(180);
        sharesColumn.setSortable(false);
        sharesColumn.setResizable(false);
        sharesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfShares"));

        TableColumn<Buying, Float> priceColumn = new TableColumn<>("Buying Price");
        priceColumn.setMinWidth(180);
        priceColumn.setSortable(false);
        priceColumn.setResizable(false);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("sharesBuyingPrice"));

        TableColumn<Buying, String> companyColumn = new TableColumn<>("Company");
        companyColumn.setMinWidth(200);
        companyColumn.setSortable(false);
        companyColumn.setResizable(false);
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        TableColumn<Buying, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(200);
        dateColumn.setSortable(false);
        dateColumn.setResizable(false);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("stringDate"));


        buyingTableView.getColumns().addAll(sharesColumn, priceColumn, companyColumn, dateColumn);
        return buyingTableView;
    }

    /**
     * to view data in table view
     */
    public static void updateTable(Object list) {

        if (list instanceof LinkedList) {
            if (!Utilities.dailyPriceLinkedList.isEmpty()) {
                dailyPriceTable.getItems().clear(); // clear data from table
                Node<DailyPrice> curr = Utilities.dailyPriceLinkedList.getHead();
                int count = 0;
                while (curr != null) {
                    dailyPriceTable.getItems().add(curr.getData()); // upload data to the table
                    count++;
                    curr = curr.getNext();
                }
                txtTotalCompany.setText(count + "");
            } else {
                txtTotalCompany.clear();
                dailyPriceTable.getItems().clear(); // clear data from table
            }
        } else {
            if (!Utilities.buyingQueues.isEmpty()) {
                buyingTableView.getItems().clear(); // clear data from table
                Node<Buying> curr = Utilities.buyingQueues.getFirst();
                int count = 0;
                while (curr != null) {
                    buyingTableView.getItems().add(curr.getData()); // upload data to the table
                    count += curr.getData().getNumberOfShares();
                    curr = curr.getNext();
                }
                txtTotalShares.setText(count + "");
            } else {
                txtTotalShares.clear();
                buyingTableView.getItems().clear(); // clear data from table
            }
        }

    }

    public VBox vBoxDailyPrice() {
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-background-color: #ffffff");

        Label lblDailyPrice = new Label("Daily Price");
        lblDailyPrice.setAlignment(Pos.CENTER);
        lblDailyPrice.setStyle("-fx-background-color: #ffffff; " + "-fx-font-weight: BOLd;-fx-font-size:25; -fx-text-fill: #000000;");
        lblDailyPrice.setMaxWidth(260);

        // Label for total shares
        lblTotalCompany = new Label("Total Company");
        lblTotalCompany.setStyle("-fx-text-fill:#000000; -fx-background-color:#ffffff;" +
                "-fx-font-weight: BOLd; -fx-font-size:15;");

        // Text filed to display the total number
        txtTotalCompany = new TextField();
        txtTotalCompany.setMaxWidth(60);
        txtTotalCompany.setEditable(false);
        txtTotalCompany.setStyle("-fx-background-color:#ffffff; -fx-font-size:15;" +
                " -fx-border-width: 0px0px2px0px; -fx-border-color: #000000;" +
                " -fx-text-fill:#000000;  -fx-font-weight: BOLd;");

        // HBox to display total number
        HBox hBox = new HBox(15);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.getChildren().addAll(lblTotalCompany, txtTotalCompany);

        // To Download lblTotalNumber node down
        hBox.setMargin(lblTotalCompany, new Insets(15, 0, 0, 0));

        // To raise the txtTotalNumber node up
        hBox.setMargin(txtTotalCompany, new Insets(0, 0, 10, 0));

        vBox.getChildren().addAll(lblDailyPrice, dailyPriceTable(), hBox);

        return vBox;
    }

    public VBox vBoxMyShares() {

        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-background-color: #ffffff");

        Label lblMyShares = new Label("My Shares");
        lblMyShares.setAlignment(Pos.CENTER);
        lblMyShares.setStyle("-fx-background-color: #ffffff; " + "-fx-font-weight: BOLd;-fx-font-size:25; -fx-text-fill: #000000;");
        lblMyShares.setMaxWidth(260);

        // Label for total shares
        lblTotalShares = new Label("Total Shares");
        lblTotalShares.setStyle("-fx-text-fill:#000000; -fx-background-color:#ffffff;" +
                "-fx-font-weight: BOLd; -fx-font-size:15;");

        // Text filed to display the total number
        txtTotalShares = new TextField();
        txtTotalShares.setMaxWidth(125);
        txtTotalShares.setEditable(false);
        txtTotalShares.setStyle("-fx-background-color:#ffffff; -fx-font-size:15;" +
                " -fx-border-width: 0px0px2px0px; -fx-border-color: #000000;" +
                " -fx-text-fill:#000000;  -fx-font-weight: BOLd;");

        // HBox to display total number
        HBox hBox = new HBox(15);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        hBox.setStyle("-fx-background-color: #ffffff;");
        hBox.getChildren().addAll(lblTotalShares, txtTotalShares);

        // To Download lblTotalNumber node down
        hBox.setMargin(lblTotalShares, new Insets(15, 0, 0, 0));

        // To raise the txtTotalNumber node up
        hBox.setMargin(txtTotalShares, new Insets(0, 0, 10, 0));

        vBox.getChildren().addAll(lblMyShares, buyingTable(), hBox);

        return vBox;
    }

    // all buttons and functions
    public VBox functions() {

        VBox vBox = new VBox(25);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #ffffff");

        // button for buy new shares
        btBuy = new Button("Buy Shares");
        btBuy.setMinWidth(275);
        btBuy.setMinHeight(40);
        btBuy.setStyle(styleBt);
        btBuy.setOnMouseEntered(e -> {
            btBuy.setStyle(styleHoverBt);
        });
        // To change the design of the button when the mouse arrow is removed from it
        btBuy.setOnMouseExited(e -> {
            btBuy.setStyle(styleBt);
        });
        btBuy.setOnAction(e -> {
            BuyingGUI.Buy(Utilities.dailyPriceLinkedList);
        });

        // button for display capital
        btReport = new Button("Report");
        btReport.setMinWidth(275);
        btReport.setMinHeight(40);
        btReport.setStyle(styleBt);
        btReport.setOnMouseEntered(e -> {
            btReport.setStyle(styleHoverBt);
        });
        btReport.setOnMouseExited(e -> {
            btReport.setStyle(styleBt);
        });
        btReport.setOnAction(e -> {
            if (!Utilities.report.isEmpty()) {
                String res;
                if (Utilities.totalCapital < 0) {// loos
                    res = " you lost " + (Utilities.totalCapital * -1);
                } else if (Utilities.totalCapital > 0) {// earned
                    res = " you earned " + Utilities.totalCapital;
                } else {//  same capital
                    res = "" + Utilities.totalCapital;
                }

                Details.viewDetails(Utilities.report + "\nTotal profit: " + res + "\n");
            } else {
                Message.displayMassage("", " You don't have any sales movement ");
            }

        });

        // button for sell current shares
        btSell = new Button("Sell Shares");
        btSell.setMinWidth(275);
        btSell.setMinHeight(40);
        btSell.setStyle(styleBt);
        btSell.setOnMouseEntered(e -> {
            btSell.setStyle(styleHoverBt);
        });
        btSell.setOnMouseExited(e -> {
            btSell.setStyle(styleBt);
        });
        btSell.setOnAction(e -> SellingGUI.Sell(Utilities.dailyPriceLinkedList));

        // button for add new company
        btNewCompany = new Button("New company");
        btNewCompany.setMinWidth(275);
        btNewCompany.setMinHeight(40);
        btNewCompany.setStyle(styleBt);
        btNewCompany.setOnMouseEntered(e -> {
            btNewCompany.setStyle(styleHoverBt);
        });
        btNewCompany.setOnMouseExited(e -> {
            btNewCompany.setStyle(styleBt);
        });
        btNewCompany.setOnAction(e -> NewCompany.addNewCompany());

        vBox.getChildren().addAll(btBuy, btSell, btReport, btNewCompany);
        
        return vBox;
    }

    // display all components
    public BorderPane allComponents() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setStyle("-fx-background-color: #ffffff;");

        pane.setRight(functions());
        pane.setCenter(vBoxMyShares());
        pane.setLeft(vBoxDailyPrice());

        return pane;
    }

}
