
import database.Account.AccountEntity;
import database.Account.AccountQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.List;

public class savings extends overView {

    public void display(Stage primaryStage, ObservableList<database> customer) {
        String FirstName = customer.get(0).getFname();
        String LastName = customer.get(0).getLname();
        String ssn = customer.get(0).getSSN();

        List<AccountEntity> acc = new AccountQuery().getBySSN(ssn).getByType("Savings").execute();
        double Balance = acc.get(0).getBALANCE();

        //customerFound.getBySSN(ssn).execute();
        //name label
        Label greeting = new Label("Hello, ");
        Label Fname = new Label(FirstName);
        Label Lname = new Label(" " + LastName);
        greeting.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Fname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Lname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //title:checking
        Label title = new Label("Saving Account");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //account overview: balance
        Text overview = new Text("Balance:");
        overview.setFont(Font.font(null, 20));
        overview.setFill(Color.WHITE);
        Text balance = new Text(("$" + Balance));
        balance.setTextAlignment(TextAlignment.LEFT);
        balance.setFont(Font.font(null, 20));
        balance.setFill(Color.WHITE);
        //buttons for home, quite, and transfer money
        Button home = new Button("Home");
        //home button will go back to the overview page
        home.setOnAction(e -> {
            overViewAcc = new overView();
            overViewAcc.display(primaryStage, customer);
        });
        home.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button transfer = new Button("Transfer");
        //transfer button will go to the input of amount transferring page
        transfer.setOnAction(e -> {
            transfer newTransfer = new transfer();
            newTransfer.display(primaryStage, customer, 2);
        });
        transfer.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        Button deposit = new Button("Deposit");
        //deposit button will lead to deposit amount page
        deposit.setOnAction(e -> {
            depositCash = new deposit();
            depositCash.display(primaryStage, customer, 2);
        });
        deposit.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        Button withdraw = new Button("Withdraw");
        //withdraw button will lead to withdraw amount page
        withdraw.setOnAction(e -> {
            withDrawCash = new withDraw();
            withDrawCash.display(primaryStage, customer, 2);
        });
        withdraw.setStyle("-fx-background-color: blue; -fx-text-fill: black");

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(withdraw, transfer, deposit);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        HBox action2 = new HBox();
        action2.getChildren().addAll(quit, home);
        action2.setSpacing(50);
        action2.setAlignment(Pos.CENTER);
        GridPane accBalance = new GridPane();
        accBalance.add(overview, 0, 0);
        accBalance.add(balance, 1, 0);
        accBalance.setHgap(25);
        accBalance.setAlignment(Pos.CENTER);
        GridPane name = new GridPane();
        name.add(greeting, 0, 0);
        name.add(Fname, 1, 0);
        name.add(Lname, 2, 0);
        name.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        if (teller || manager) {
            pane.getChildren().addAll(name, title, accBalance, action, action2);
        } else {
            pane.getChildren().addAll(name, title, accBalance, transfer, action2);
        }
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }
}
