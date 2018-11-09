import database.Account.AccountEntity;
import database.Account.AccountQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.List;

public class loan extends overView {
    public void display(Stage primaryStage, ObservableList<database> customer) {
        //name label
        String FirstName = customer.get(0).getFname();
        String LastName = customer.get(0).getLname();
        String ssn = customer.get(0).getSSN();

        List<AccountEntity> acc = new AccountQuery().getBySSN(ssn).getByType("Loan").execute();
        String OverAllBalance = String.valueOf(acc.get(0).getBALANCE());
        Label greeting = new Label("Hello, ");
        Label Fname = new Label(FirstName);
        Label Lname = new Label(" " + LastName);
        greeting.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Fname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Lname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //title:checking
        Label title = new Label("Loan");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        //Payment Due Date
        Text dueD = new Text("Payment due Date:");
        dueD.setFont(Font.font(null, 20));
        dueD.setFill(Color.WHITE);
        Text dueday = new Text("08/09/2018");
        dueday.setFont(Font.font(null, 20));
        dueday.setFill(Color.WHITE);
        //Minimum Payment
        Text MinP = new Text("Minimum Payment:");
        MinP.setFont(Font.font(null, 20));
        MinP.setFill(Color.WHITE);
        Text MinPayment = new Text("$$");
        MinPayment.setFont(Font.font(null, 20));
        MinPayment.setFill(Color.WHITE);
        //account overview: balance
        Text balanceOA = new Text("Balance Overall:");
        balanceOA.setFont(Font.font(null, 20));
        balanceOA.setFill(Color.WHITE);
        Text OVBalance = new Text(OverAllBalance);
        OVBalance.setTextAlignment(TextAlignment.LEFT);
        OVBalance.setFont(Font.font(null, 20));
        OVBalance.setFill(Color.WHITE);
        Text interestR = new Text("Interest Rate:");
        interestR.setFont(Font.font(null, 20));
        interestR.setFill(Color.WHITE);
        Text interestRate = new Text("0.25");
        interestRate.setTextAlignment(TextAlignment.LEFT);
        interestRate.setFont(Font.font(null, 20));
        interestRate.setFill(Color.WHITE);
        //edit interest rate
        Label update = new Label("Update Interest Rate: ");
        update.setStyle("-fx-text-fill: red; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        TextField updateIR = new TextField();
        updateIR.setPrefWidth(50);
        //buttons for home, quite, and transfer money
        Button home = new Button("Home");
        home.setOnAction(e -> overViewAcc.display(primaryStage, customer));
        home.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button quit = new Button("Quit");
        quit.setOnAction(e -> {
            try {
                start(primaryStage);
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button pay = new Button("Pay");
        pay.setOnAction(e -> {
            try {
                //makePayment(primaryStage, 1);
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
        pay.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button save = new Button("Save");
        save.setOnAction(e -> interestRate.setText(updateIR.getText()));
        save.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        //create pane
        HBox action = new HBox();

        if (teller || manager) action.getChildren().addAll(home, quit, save);
        else action.getChildren().addAll(pay, home, quit);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        ;//name grid
        GridPane name = new GridPane();
        name.add(greeting, 0, 0);
        name.add(Fname, 1, 0);
        name.add(Lname, 2, 0);
        name.setAlignment(Pos.CENTER);
        GridPane info = new GridPane();
        info.add(dueD, 0, 0);
        info.add(dueday, 1, 0);
        info.add(MinP, 0, 1);
        info.add(MinPayment, 1, 1);
        info.add(balanceOA, 0, 2);
        info.add(OVBalance, 1, 2);
        info.add(interestR, 0, 3);
        info.add(interestRate, 1, 3);
        info.setHgap(25);
        info.setVgap(25);
        info.setAlignment(Pos.CENTER);
        GridPane IR = new GridPane();
        IR.add(update, 0, 0);
        IR.add(updateIR, 1, 0);
        IR.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        if (teller || manager) pane.getChildren().addAll(name, title, info, IR, action);
        else pane.getChildren().addAll(name, title, info, action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }
}