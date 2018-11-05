import database.Customer.Customer;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Date;

public class overView extends BankingApplication {

    public overView(){

    }
    //method return the scene of account overview
    public void display(Stage primaryStage,ObservableList<database> customer) {
        String FirstName=customer.get(1).getFname();
        String LastName=customer.get(2).getLname();
        //create text with date
        Text date = new Text(new Date().toString());
        Label title = new Label("Home Page");
        title.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 30");
        title.setAlignment(Pos.CENTER);
        date.setStyle("-fx-fill: white; -fx-stroke: white");
        //create label for customer name
        Label greeting = new Label("Welcome, ");
        Label Fname = new Label(FirstName);
        Label Lname = new Label(" " + LastName);
        greeting.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Fname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Lname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //list all the accounts as hyperlinks along with checkboxes showing whether the accounts are available
        Text accounts = new Text("Accounts:");
        accounts.setFill(Color.WHITE);
        accounts.setFont(Font.font(null, 20));
        Text available = new Text("Availability");
        available.setFill(Color.WHITE);
        available.setFont(Font.font(null, 20));
        accounts.setStyle("-fx-text-fill: white");
        Hyperlink checking = new Hyperlink("Checking");
        //action of all hyperlinks is to go to the account page of the indicated account
        //checking.setOnAction(e -> checking(primaryStage));
        Hyperlink loan = new Hyperlink("Loan");
        //loan.setOnAction(e -> loan(primaryStage));
        //create checkbox for each account showing availability
        Hyperlink saving = new Hyperlink("Savings");
       // saving.setOnAction(e -> saving(primaryStage));
        Hyperlink credit = new Hyperlink("Credit Card");
        //credit.setOnAction(e -> CreditCard(primaryStage));
        CheckBox ch = new CheckBox();
        //each check box will only check if the boolean for that account is true
        ch.setSelected(haveChecking);
        ch.setDisable(true);
        CheckBox sa = new CheckBox();
        sa.setSelected(haveSaving);
        sa.setDisable(true);
        CheckBox cr = new CheckBox();
        cr.setSelected(haveCredit);
        cr.setDisable(true);
        CheckBox lo = new CheckBox();
        lo.setSelected(haveLoan);
        lo.setDisable(true);
        //create button for quit which will lead back to log in page
        Button quit = new Button("Quit");
        //quit button will go back to log in page and start over in log in lookup
        quit.setOnAction(e -> {
            try {
                start(primaryStage);
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        GridPane name = new GridPane();
        name.add(greeting, 0, 0);
        name.add(Fname, 1, 0);
        name.add(Lname, 2, 0);
        name.setAlignment(Pos.CENTER);
        HBox head = new HBox();
        head.getChildren().addAll(name, date);
        head.setSpacing(25);
        head.setAlignment(Pos.CENTER);
        //create pane to include all the elements
        GridPane pane = new GridPane();
        pane.add(accounts, 0, 0);
        //pane.add(available, 1,0);
        pane.add(checking, 0, 1);
        pane.add(ch, 1, 1);
        pane.add(saving, 0, 2);
        pane.add(sa, 1, 2);
        pane.add(credit, 0, 3);
        pane.add(cr, 1, 3);
        pane.add(loan, 0, 4);
        pane.add(lo, 1, 4);
        pane.setAlignment(Pos.CENTER);
        VBox mainPane = new VBox();
        mainPane.getChildren().addAll(head, title, pane, quit);
        //set some spacing between the elements
        pane.setVgap(25);
        pane.setHgap(150);
        mainPane.setStyle("-fx-background-color: black");
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(25);
        //add pane to scene
        scene = new Scene(mainPane, 700, 700);
        primaryStage.setScene(scene);
    }
}