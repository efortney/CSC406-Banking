
import javafx.stage.Stage;
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

import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.TextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author wzhang1
 */
public class transfer extends overView {
    int accountType;
    double AmountTransfered;
    String StringAmount;
    ObservableList<AccountEntity> savingsAcc, checkingAcc, loanAcc, CreditAcc;

    public void display(Stage primaryStage, ObservableList<database> customer, int account) {
        accountType = account;
        String ssn = customer.get(0).getSSN();

        List<AccountEntity> acc = new AccountQuery().getBySSN(ssn).execute();
        for (int i = 0; i < acc.size(); i++) {
            switch (acc.get(i).getTYPE()) {
                case "Savings":
                    savingsAcc = FXCollections.observableArrayList();
                    savingsAcc.add(acc.get(i));

                    break;
                case "Checking":
                    checkingAcc = FXCollections.observableArrayList();
                    checkingAcc.add(acc.get(i));

                    break;
                case "Loan":
                    loanAcc = FXCollections.observableArrayList();
                    loanAcc.add(acc.get(i));

                    break;
                case "Credit":
                    CreditAcc = FXCollections.observableArrayList();
                    CreditAcc.add(acc.get(i));

                    break;
                default:
                    accountType = 0;
                    break;
            }
        }
        double savingBalance = savingsAcc.get(0).getBALANCE();
        double checkingBalance = checkingAcc.get(0).getBALANCE();
        //everything is the same between both transfer except for the intro and function of the transfer button
        Text howMuch = new Text();
        howMuch.setFill(Color.WHITE);
        howMuch.setFont(Font.font(null, 20));
        TextField amount = new TextField();
        amount.setMaxSize(250, 250);
        amount.setAlignment(Pos.CENTER);
        Button submit = new Button("Submit");

        submit.setOnAction(e -> {
            StringAmount = amount.getText();
            AmountTransfered = Double.parseDouble(StringAmount);
            if ((checkingBalance - AmountTransfered) >= 0 && (savingBalance - AmountTransfered) >= 0 && AmountTransfered >= 0) {
                if (accountType == 1) {
                    savingsAcc.get(0).setBALANCE(savingBalance - AmountTransfered);
                    checkingAcc.get(0).setBALANCE(checkingBalance + AmountTransfered);
                } else {
                    savingsAcc.get(0).setBALANCE(savingBalance + AmountTransfered);
                    checkingAcc.get(0).setBALANCE(checkingBalance - AmountTransfered);
                }
                savingsAcc.get(0).update();
                checkingAcc.get(0).update();
                alertbox = new AlertBox();
                alertbox.display("Transfer Successful", "Transfer Successful", accountType, customer);
            } else {
                alertbox = new AlertBox();
                alertbox.display("Transfer unsuccessful", "Not enough balance", accountType, customer);
            }
        });

        if (accountType == 1) {
            howMuch.setText("How much do you want to tranfer from the savings account?");
            submit.setText("Submit");
            submit.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        } else if (accountType == 2) {
            howMuch.setText("How much do you want to tranfer from the checking account?");
            submit.setText("Submit");
            submit.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        }
        //create input amount textField

        //create buttons back and quit
        Button back = new Button("Back");
        back.setOnAction(e -> {
            if (accountType == 1) {
                checking checkingAcc = new checking();
                checkingAcc.display(primaryStage, customer);
            } else {
                savings savingsAcc = new savings();
                savingsAcc.display(primaryStage, customer);
            }
        });
        back.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
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

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(quit, back);
        action.setSpacing(100);
        action.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(howMuch, amount, submit, action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

}
