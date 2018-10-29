import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

import java.util.Date;

//Note:
//1.Some function have int value, that represent the type of account(1:checking,2:saving,3:Credit card,4:Loan)
public class BankingApplication extends Application {

    Scene scene; //the ever changing scene methods can change and return to the stage
    //boolean for all the users, it will be true for the one currently using the interface
    Boolean customer = false;
    Boolean teller = false;
    Boolean manager = true;
    //same boolean indication for accounts
    Boolean haveSaving = false;
    Boolean haveChecking = false;
    Boolean haveCredit = false;
    Boolean haveOC = false;
    Boolean haveLoan = false;
    //Customer information
    String SSN = "123456789";
    String FirstName = "Wei";
    String LastName = "Zhang";
    String Address = "1723 s 39th";
    String City = "saint joseph";
    String State = "MO";
    String ZipCode = "64507";
    String Balance = "1000";
    String password = "123456";
    TableView<database> availableCTable;
    Button submit;
    TextField fnameInput, lnameInput, addressInput, ssnInput, zipCodeInput, cityInput, stateInput;


    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) throws Exception {
        //title of the window
        primaryStage.setTitle("Griffon Bank");

        //set title of the first page
        //set title to yellow and the rest of the texts to white
        Text title = new Text("Welcome to Griffon Bank");
        title.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 50));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFill(Color.YELLOW);
        //create introduction
        Text intro = new Text("Your personal assistant for banking on the go!\nPlease enter your log in information:");
        intro.setStroke(Color.WHITE);
        intro.setFill(Color.WHITE);
        intro.setStyle("-fx-font-size: 25");
        intro.setTextAlignment(TextAlignment.CENTER);
        //create label user ID and password along with input text field
        Label username = new Label("User Name");
        username.setTextFill(Color.WHITE);
        Label password = new Label("Password");
        password.setTextFill(Color.WHITE);
        TextField unInput = new TextField();
        //check if the username is all number
        unInput.setPrefColumnCount(20);
        PasswordField pwInput = new PasswordField();
        pwInput.setPrefColumnCount(20);
        //create quit and log in button
        Button quit = new Button("Quit");
        //action of quit button is closing window and ending program
        quit.setOnAction(e -> {
            if (ConfirmBox.display("Close Program", "Are you ready to leave? ", 0)) primaryStage.close();
            else {
                try {
                    start(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        //quit will close the window
        Button enter = new Button("Enter");
        //action of enter button is inputing the log in info, retrieving the user info, indicating the next page: customer lookup or overview of accounts
        enter.setOnAction(e -> {
            boolean vaildUserName = isInt(unInput, unInput.getText());
            if ((manager || teller) && vaildUserName == true) lookUp(primaryStage);
            else if (customer && vaildUserName == true) overview(primaryStage);
            else {
                AlertBox.display("invalid user name", "Invalid user name", 0);
                pwInput.clear();
                unInput.clear();
            }
        });
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        enter.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create HBox for the user inputs and center the position
        GridPane inputPane = new GridPane();
        inputPane.setHgap(5);
        inputPane.setVgap(5);
        inputPane.add(username, 0, 0);
        inputPane.add(unInput, 1, 0);
        inputPane.add(password, 0, 1);
        inputPane.add(pwInput, 1, 1);
        inputPane.setAlignment(Pos.CENTER);
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(quit, enter);
        buttonPane.setSpacing(125);
        buttonPane.setAlignment(Pos.CENTER);
        inputPane.add(buttonPane, 1, 3);
        //create VBox to arrange all items
        VBox mainPane = new VBox();
        mainPane.setSpacing(20);
        mainPane.getChildren().addAll(title, intro, inputPane);
        //set VBox background to black and center the position
        mainPane.setStyle("-fx-background-color: black");
        mainPane.setAlignment(Pos.CENTER);
        //create scene and input the pane
        scene = new Scene(mainPane, 700, 700);
        primaryStage.setScene(scene);
        //show the scene
        primaryStage.show();
    }

    //method return the scene of account overview
    public void overview(Stage primaryStage) {
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
        checking.setOnAction(e -> checking(primaryStage));
        Hyperlink saving = new Hyperlink("Savings");
        saving.setOnAction(e -> saving(primaryStage));
        Hyperlink credit = new Hyperlink("Credit Card");
        credit.setOnAction(e -> CreditCard(primaryStage));
        Hyperlink loan = new Hyperlink("Loan");
        loan.setOnAction(e -> loan(primaryStage));
        //create checkbox for each account showing availability
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
        CheckBox ocs = new CheckBox();
        ocs.setSelected(haveOC);
        ocs.setDisable(true);
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

    //checking page
    public void checking(Stage primaryStage) {
        //name label
        Label greeting = new Label("Hello, ");
        Label Fname = new Label(FirstName);
        Label Lname = new Label(" " + LastName);
        greeting.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Fname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Lname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //title:checking
        Label title = new Label("Checking Account");
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
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                overview(primaryStage);
            }
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
        transfer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transfer(primaryStage, 1);
            }
        });
        transfer.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        Button deposit = new Button("Deposit");
        //deposit button will lead to deposit amount page
        deposit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        deposit.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        Button withdraw = new Button("Withdraw");
        //withdraw button will lead to withdraw amount page
        withdraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
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
        if (teller || manager) pane.getChildren().addAll(name, title, accBalance, action, action2);
        else pane.getChildren().addAll(name, title, accBalance, transfer, action2);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    //saving page
    public void saving(Stage primaryStage) {
        //name label
        Label greeting = new Label("Hello, ");
        Label Fname = new Label(FirstName);
        Label Lname = new Label(" " + LastName);
        greeting.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Fname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Lname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //title:checking
        Label title = new Label("Savings Account");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //account overview: balance
        Text overview = new Text("Balance:");
        overview.setFont(Font.font(null, 20));
        overview.setFill(Color.WHITE);
        Text balance = new Text("$" + Balance);
        balance.setTextAlignment(TextAlignment.LEFT);
        balance.setFont(Font.font(null, 20));
        balance.setFill(Color.WHITE);
        //buttons for home, quite, and transfer money
        Button home = new Button("Home");
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                overview(primaryStage);
            }
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
        transfer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transfer(primaryStage, 2);
            }
        });
        transfer.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        Button deposit = new Button("Deposit");
        deposit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        deposit.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        Button withdraw = new Button("Withdraw");
        withdraw.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
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
        VBox pane = new VBox();
        GridPane name = new GridPane();
        name.add(greeting, 0, 0);
        name.add(Fname, 1, 0);
        name.add(Lname, 2, 0);
        name.setAlignment(Pos.CENTER);
        if (teller || manager) pane.getChildren().addAll(name, title, accBalance, action, action2);
        else pane.getChildren().addAll(name, title, accBalance, transfer, action2);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    //credit card page
    public void CreditCard(Stage primaryStage) {
        //name label
        Label greeting = new Label("Hello, ");
        Label Fname = new Label(FirstName);
        Label Lname = new Label(" " + LastName);
        greeting.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Fname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Lname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //title:checking
        Label title = new Label("Credit Card");
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
        Text availableC = new Text("Available Credit:");
        availableC.setFont(Font.font(null, 20));
        availableC.setFill(Color.WHITE);
        Text limit = new Text("/$$$$");
        limit.setTextAlignment(TextAlignment.LEFT);
        limit.setFont(Font.font(null, 20));
        limit.setFill(Color.WHITE);
        Text available = new Text("$$$$");
        available.setTextAlignment(TextAlignment.LEFT);
        available.setFont(Font.font(null, 20));
        available.setFill(Color.WHITE);
        //History
        Text his = new Text("History:");
        his.setFont(Font.font(null, 20));
        his.setFill(Color.WHITE);
        TableView hisTable = new TableView();
        TableColumn date = new TableColumn("Date");
        date.setPrefWidth(150);
        TableColumn source = new TableColumn("Source");
        source.setPrefWidth(150);
        TableColumn amount = new TableColumn("Amount");
        amount.setPrefWidth(150);
        hisTable.getColumns().addAll(date, source, amount);
        ScrollPane hisBox = new ScrollPane();
        hisBox.setContent(hisTable);
        hisBox.setFitToWidth(true);
        hisBox.setPrefWidth(450);
        hisBox.setPrefHeight(150);
        //buttons for home, quite, and transfer money
        Button home = new Button("Home");
        home.setOnAction(e -> overview(primaryStage));
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
                makePayment(primaryStage, 1);
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
        pay.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(pay, home, quit);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        ;
        GridPane name = new GridPane();
        name.add(greeting, 0, 0);
        name.add(Fname, 1, 0);
        name.add(Lname, 2, 0);
        name.setAlignment(Pos.CENTER);
        GridPane Due = new GridPane();
        Due.add(dueD, 0, 0);
        Due.add(dueday, 1, 0);
        Due.add(MinP, 0, 1);
        Due.add(MinPayment, 1, 1);
        Due.setHgap(25);
        Due.setAlignment(Pos.CENTER);
        GridPane accBalance = new GridPane();
        accBalance.add(availableC, 0, 0);
        accBalance.add(available, 1, 0);
        accBalance.add(limit, 2, 0);
        accBalance.setAlignment(Pos.CENTER);
        GridPane history = new GridPane();
        history.add(his, 0, 0);
        history.add(hisBox, 0, 1);
        history.setAlignment(Pos.CENTER);

        VBox pane = new VBox();
        if (teller || manager) pane.getChildren().addAll(name, title, Due, accBalance, history, action);
        else pane.getChildren().addAll(name, title, Due, accBalance, history, action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    //loan page
    public void loan(Stage primaryStage) {
        //name label
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
        Text OVBalance = new Text("$$$$");
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
        home.setOnAction(e -> overview(primaryStage));
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
                makePayment(primaryStage, 1);
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

    //transfer page
    public void transfer(Stage primaryStage, int value) {
        //everything is the same between both transfer except for the intro and function of the transfer button
        Text howMuch = new Text();
        howMuch.setFill(Color.WHITE);
        howMuch.setFont(Font.font(null, 20));
        Button transfer = new Button();
        transfer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //some sort of function that calculate whether there is enough money
                //enough money to transfer will go the the success page, otherwise will go to transferDenied page
                transferSuccess(primaryStage, value);
            }
        });
        if (value == 1) {
            howMuch.setText("How much do you want to tranfer from the savings account?");
            transfer.setText("Transfer");
            transfer.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        } else if (value == 2) {
            howMuch.setText("How much do you want to tranfer from the checking account?");
            transfer.setText("Transfer");
            transfer.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        }
        //create input amount textField
        TextField amount = new TextField();
        amount.setMaxSize(250, 250);
        amount.setAlignment(Pos.CENTER);
        //create buttons back and quit
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (value == 1) checking(primaryStage);
                else saving(primaryStage);
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
        pane.getChildren().addAll(howMuch, amount, transfer, action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    //transfer successful page
    public void transferSuccess(Stage primaryStage, int value) {
        //create message text
        Text message = new Text("Transfer successful!");
        message.setFill(Color.YELLOW);
        message.setFont(Font.font(null, 25));
        Text checkingBalance = new Text("Checking balance:");
        checkingBalance.setFill(Color.WHITE);
        Text savingBalance = new Text("Savings balance:");
        savingBalance.setFill(Color.WHITE);
        //text for balance
        Text chBalance = new Text("$$$$");
        chBalance.setFill(Color.WHITE);
        Text saBalance = new Text("$$$$");
        saBalance.setFill(Color.WHITE);
        //button for back, home, and quit
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (value == 1) checking(primaryStage);
                else saving(primaryStage);
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
        Button home = new Button("Home");
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                overview(primaryStage);
            }
        });
        home.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(quit, home, back);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        GridPane balance = new GridPane();
        balance.add(checkingBalance, 0, 0);
        balance.add(savingBalance, 0, 1);
        balance.add(chBalance, 1, 0);
        balance.add(saBalance, 1, 1);
        balance.setAlignment(Pos.CENTER);
        balance.setHgap(20);
        VBox pane = new VBox(message, balance, action);
        pane.setStyle("-fx-background-color: black");
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    //make payment page
    public void makePayment(Stage primaryStage, int value) {
        Label title = new Label("Make a Payment");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        Text setPDate = new Text("Set Payment Date:");
        setPDate.setFont(Font.font(null, 20));
        setPDate.setFill(Color.WHITE);
        DatePicker dateP = new DatePicker();
        Text PAmount = new Text("Set Payment Date:");
        setPDate.setFont(Font.font(null, 20));
        setPDate.setFill(Color.WHITE);
        ToggleGroup RadioBGroup = new ToggleGroup();
        RadioButton CurrentB = new RadioButton("Current Balance :");
        CurrentB.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 17");
        Text currentBalance = new Text("$$$$");
        currentBalance.setFont(Font.font(null, 20));
        currentBalance.setFill(Color.WHITE);
        CurrentB.setToggleGroup(RadioBGroup);
        RadioButton MinB = new RadioButton("Minimun Balance :");
        MinB.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 17");
        Text MinBalance = new Text("$$");
        MinBalance.setFont(Font.font(null, 20));
        MinBalance.setFill(Color.WHITE);
        CurrentB.setToggleGroup(RadioBGroup);
        MinB.setToggleGroup(RadioBGroup);
        RadioButton other = new RadioButton("Other:");
        other.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 17");
        TextField payAmount = new TextField("Enter Amount");
        payAmount.setPrefWidth(120);
        other.setToggleGroup(RadioBGroup);
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    CreditCard(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        Button submit = new Button("Submit");
        submit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create pane
        GridPane setPaymentdate = new GridPane();
        setPaymentdate.add(setPDate, 0, 0);
        setPaymentdate.add(dateP, 1, 0);
        setPaymentdate.setHgap(25);
        setPaymentdate.setAlignment(Pos.CENTER);
        GridPane pOptions = new GridPane();
        pOptions.add(CurrentB, 0, 0);
        pOptions.add(currentBalance, 1, 0);
        pOptions.add(MinB, 0, 1);
        pOptions.add(MinBalance, 1, 1);
        pOptions.add(other, 0, 2);
        pOptions.add(payAmount, 1, 2);
        pOptions.setVgap(20);
        pOptions.setAlignment(Pos.CENTER);
        HBox action = new HBox();
        action.getChildren().addAll(submit, back, quit);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(title, setPaymentdate, pOptions, action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    //look up,delete and create a new account page
    public void lookUp(Stage primaryStage) {
        //title:checking
        Label title = new Label("Look Up");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        Text searchBy = new Text("Search By:");
        searchBy.setFont(Font.font(null, 20));
        searchBy.setFill(Color.WHITE);
        TextField searchInput = new TextField();
        searchInput.setPrefWidth(150);
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("SSN", "First Name", "Last Name", "Zip code"));
        //Search box
        Text availableC = new Text("Available Customer:");
        availableC.setFont(Font.font(null, 20));
        availableC.setFill(Color.WHITE);

        //get SSN
        TableColumn<database, Integer> ssn = new TableColumn<>("SSN");
        ssn.setPrefWidth(80);
        ssn.setCellValueFactory(new PropertyValueFactory<>("SSN"));
        //get first name
        TableColumn<database, String> first_name = new TableColumn<>("First Name");
        first_name.setPrefWidth(85);
        first_name.setCellValueFactory(new PropertyValueFactory<>("fname"));
        //get last name
        TableColumn<database, String> lastName = new TableColumn<>("Last Name");
        lastName.setPrefWidth(85);
        lastName.setCellValueFactory(new PropertyValueFactory<>("lname"));
        //get zip code
        TableColumn<database, String> zipcode = new TableColumn<>("Zip Code");
        zipcode.setPrefWidth(70);
        zipcode.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
        //get the address
        TableColumn<database, String> address = new TableColumn<>("Address");
        address.setPrefWidth(100);
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        //get the city
        TableColumn<database, String> city = new TableColumn<>("City");
        city.setPrefWidth(90);
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        //get the state
        TableColumn<database, String> state = new TableColumn<>("State");
        state.setPrefWidth(60);
        state.setCellValueFactory(new PropertyValueFactory<>("state"));


        availableCTable = new TableView<>();
        availableCTable.setItems(getData(SSN, FirstName, LastName, Address, City, State, ZipCode));
        availableCTable.getColumns().addAll(ssn, first_name, lastName, address, city, state, zipcode);
        availableCTable.setEditable(true);
        first_name.setCellFactory(TextFieldTableCell.forTableColumn());
        lastName.setCellFactory(TextFieldTableCell.forTableColumn());
        city.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setCellFactory(TextFieldTableCell.forTableColumn());
        state.setCellFactory(TextFieldTableCell.forTableColumn());
        zipcode.setCellFactory(TextFieldTableCell.forTableColumn());

        ScrollPane customerBox = new ScrollPane();
        customerBox.setContent(availableCTable);
        customerBox.setFitToWidth(true);
        customerBox.setPrefWidth(600);
        customerBox.setPrefHeight(200);
        Text signUp = new Text("Create New Account:");
        signUp.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 25));
        signUp.setTextAlignment(TextAlignment.CENTER);
        signUp.setFill(Color.RED);
        Label fName = new Label("First name:");
        fName.setStyle("-fx-text-fill: yellow;-fx-font-size: 18");
        fnameInput = new TextField();
        fnameInput.setPrefWidth(100);
        Label lName = new Label("Last name: ");
        lName.setStyle("-fx-text-fill: yellow;-fx-font-size: 18");
        lnameInput = new TextField();
        lnameInput.setPrefWidth(100);

        Label ssnLable = new Label("SSN: ");
        ssnLable.setStyle("-fx-text-fill: yellow;-fx-font-size: 18");
        ssnInput = new TextField();
        ssnInput.setPrefWidth(100);

        Label Laddress = new Label("Address:");
        Laddress.setStyle("-fx-text-fill: white;-fx-font-size: 18");
        addressInput = new TextField();
        addressInput.setPrefWidth(150);

        Label Lcity = new Label("City:");
        Lcity.setStyle("-fx-text-fill: white;-fx-font-size: 18");
        cityInput = new TextField();
        cityInput.setPrefWidth(100);

        Label zipCode = new Label("ZipCode");
        zipCode.setStyle("-fx-text-fill: white;-fx-font-size: 18");
        zipCodeInput = new TextField();
        zipCodeInput.setPrefWidth(50);

        Text Tstate = new Text("State:");
        Tstate.setFill(Color.WHITE);
        Tstate.setFont(Font.font(null, 20));

        ComboBox<String> stateBox = new ComboBox<>();
        stateBox.getItems().addAll("SL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA"
                , "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM",
                "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
        stateBox.setVisibleRowCount(5);
        stateBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue != null)
                        State = newValue.toString();
                });

        Label deposit = new Label("Deposit:");
        TextField depositAmount = new TextField();
        depositAmount.setPrefWidth(25);

        submit = new Button("Submit");
        submit.setOnAction(e -> submitButtonClicked());

        GridPane fRowlayout = new GridPane();
        fRowlayout.add(fName, 0, 1);
        fRowlayout.add(fnameInput, 1, 1);
        fRowlayout.add(lName, 2, 1);
        fRowlayout.add(lnameInput, 3, 1);
        fRowlayout.add(ssnLable, 4, 1);
        fRowlayout.add(ssnInput, 5, 1);
        fRowlayout.setHgap(10);
        fRowlayout.setVgap(10);
        fRowlayout.setAlignment(Pos.CENTER);
        GridPane sRowlayout = new GridPane();
        sRowlayout.add(Laddress, 0, 0);
        sRowlayout.add(addressInput, 1, 0);
        sRowlayout.add(Lcity, 2, 0);
        sRowlayout.add(cityInput, 3, 0);
        sRowlayout.add(Tstate, 4, 0);
        sRowlayout.add(stateBox, 5, 0);
        sRowlayout.add(zipCode, 6, 0);
        sRowlayout.add(zipCodeInput, 7, 0);

        sRowlayout.setAlignment(Pos.CENTER);
        sRowlayout.setHgap(10);
        sRowlayout.setVgap(10);

        // buttons for home, quite, and transfer money
        Button search = new Button("Search");
        search.setOnAction(e -> overview(primaryStage));
        search.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button quit = new Button("Quit");
        quit.setOnAction(e -> {
            try {
                start(primaryStage);
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button delete = new Button("Delete");
        delete.setOnAction(e -> deleteButtonClicked());
        delete.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(search, quit, delete);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        ;
        GridPane Customer = new GridPane();
        Customer.add(availableC, 0, 0);
        Customer.add(customerBox, 0, 1);
        Customer.setAlignment(Pos.CENTER);
        GridPane searchByPane = new GridPane();
        searchByPane.add(searchBy, 0, 0);
        searchByPane.add(cb, 1, 0);
        searchByPane.add(searchInput, 2, 0);
        searchByPane.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(title, searchByPane, Customer, action, signUp, fRowlayout, sRowlayout, submit);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    //yes or no window
    public boolean isInt(TextField user, String userName) {
        try {
            int userID = Integer.parseInt(user.getText());
            return true;
        } catch (NumberFormatException e) {
            ;
            return false;
        }
    }

    //get all of the customer
    public ObservableList<database> getData(String SSN, String f_Name, String l_Name, String address, String city, String state, String zipCode) {
        ObservableList<database> customer = FXCollections.observableArrayList();
        customer.addAll(new database(SSN, f_Name, l_Name, address, city, state, zipCode));
        customer.addAll(new database("1", "BB", "BB", "somewhere", "somecity", "somestate", "5"));

        return customer;
    }

    public void submitButtonClicked() {
        database customer = new database();
        try {
            customer.setFname(fnameInput.getText());
            customer.setLname(lnameInput.getText());
            customer.setZipcode(zipCodeInput.getText());
            customer.setSSN(ssnInput.getText());
            customer.setAddress(addressInput.getText());
            customer.setCity(cityInput.getText());
            customer.setState(State);
        } catch (Exception er) {
            AlertBox.display("Invalid input", "Invalid input\n Please Enter Again", 0);
        }
        availableCTable.getItems().add(customer);
        fnameInput.clear();
        lnameInput.clear();
        ssnInput.clear();
        zipCodeInput.clear();
        stateInput.clear();
        addressInput.clear();
    }

    public void deleteButtonClicked() {
        ObservableList<database> customerSelected, allCustomer;
        allCustomer = availableCTable.getItems();
        customerSelected = availableCTable.getSelectionModel().getSelectedItems();
        customerSelected.forEach(allCustomer::remove);
    }
}

