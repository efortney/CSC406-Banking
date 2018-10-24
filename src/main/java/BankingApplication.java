import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.util.Date;

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
        Label username = new Label("Username");
        username.setTextFill(Color.WHITE);
        Label password = new Label("Password");
        password.setTextFill(Color.WHITE);
        TextField unInput = new TextField();
        unInput.setPrefColumnCount(20);
        TextField pwInput = new TextField();
        pwInput.setPrefColumnCount(20);
        //create quit and log in button
        Button quit = new Button("Quit");
        //action of quit button is closing window and ending program
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        //quit will close the window
        Button enter = new Button("Enter");
        //action of enter button is inputing the log in info, retrieving the user info, indicating the next page: customer lookup or overview of accounts
        enter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                overview(primaryStage);
            }
        });
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        enter.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create HBox for the user inputs and center the position
        GridPane inputPane = new GridPane();
        inputPane.setHgap(5);
        inputPane.setVgap(5);
        inputPane.add(username, 0,0);
        inputPane.add(unInput, 1,0);
        inputPane.add(password, 0,1);
        inputPane.add(pwInput, 1,1);
        inputPane.setAlignment(Pos.CENTER);
        HBox buttonPane = new HBox();
        buttonPane.getChildren().addAll(quit, enter);
        buttonPane.setSpacing(125);
        buttonPane.setAlignment(Pos.CENTER);
        inputPane.add(buttonPane, 1,3);
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
    public void overview(final Stage primaryStage){
        //create taxt with date
        Text date = new Text(new Date().toString());
        date.setStyle("-fx-fill: white; -fx-stroke: white");
        //create label for customer name
        Label name = new Label("name");
        name.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
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
        checking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checking(primaryStage);
            }
        });
        Hyperlink saving = new Hyperlink("Savings");
        saving.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saving(primaryStage);
            }
        });
        Hyperlink credit = new Hyperlink("Credit Card");
        credit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreditCard(primaryStage);
            }
        });
        Hyperlink oc = new Hyperlink("cd");
        oc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        Hyperlink loan = new Hyperlink("Loan");
        loan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loan(primaryStage);
            }
        });
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
        //create hyperlink acking if user want to edit user info
        Hyperlink edit = new Hyperlink("Do you want to edit account information?");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                edit(primaryStage);
            }
        });
        //create button for quit which will lead back to log in page
        Button quit = new Button("Quit");
        //quit button will go back to log in page and start over in log in lookup
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

        HBox head = new HBox();
        head.getChildren().addAll(name, date);
        head.setSpacing(25);
        head.setAlignment(Pos.CENTER);
        //create pane to include all the elements
        GridPane pane = new GridPane();
        pane.add(accounts, 0,0);
        //pane.add(available, 1,0);
        pane.add(checking, 0,1);
        pane.add(ch, 1,1);
        pane.add(saving, 0,2);
        pane.add(sa, 1,2);
        pane.add(credit, 0,3);
        pane.add(cr, 1, 3);
        pane.add(oc, 0,4);
        pane.add(ocs, 1,4);
        pane.add(loan, 0,5);
        pane.add(lo, 1, 5);
        pane.setAlignment(Pos.CENTER);
        VBox mainPane = new VBox();
        mainPane.getChildren().addAll(head, pane, edit, quit);
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
    public void checking(final Stage primaryStage){
        //name label
        Label name = new Label("name");
        name.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        //title:checking
        Label title = new Label("Checking Account");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //account overview: balance
        Text overview = new Text("Balance:");
        overview.setFont(Font.font(null, 20));
        overview.setFill(Color.WHITE);
        Text balance = new Text("$$$$");
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
        accBalance.add(overview, 0,0);
        accBalance.add(balance, 1,0);
        accBalance.setHgap(25);
        accBalance.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        if(teller || manager)pane.getChildren().addAll(name, title, accBalance, action, action2);
        else pane.getChildren().addAll(name, title, accBalance, transfer, action2);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    public void edit(final Stage primaryStage){
        //create text for customer info
        Text fName = new Text("First Name");
        fName.setFill(Color.YELLOW);
        fName.setFont(Font.font(null, FontWeight.BOLD, 25));
        Text lName = new Text("Last Name");
        lName.setFill(Color.YELLOW);
        lName.setFont(Font.font(null, FontWeight.BOLD, 25));
        Text ssn =  new Text("SSN:");
        ssn.setFill(Color.WHITE);
        ssn.setFont(Font.font(null, 20));
        //customer can only edit address
        Text addr = new Text("Address:");
        addr.setFill(Color.WHITE);
        addr.setFont(Font.font(null, 20));
        TextField addrInput = new TextField();
        addrInput.setPrefColumnCount(42);
        Text city = new Text("City:");
        city.setFill(Color.WHITE);
        city.setFont(Font.font(null, 20));
        TextField cityInput = new TextField();
        cityInput.setPrefColumnCount(30);
        Text state = new Text("State:");
        state.setFill(Color.WHITE);
        state.setFont(Font.font(null, 20));
        ChoiceBox stateInput = new ChoiceBox();
        stateInput.setItems(FXCollections.observableArrayList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
        Text zip = new Text("zip:");
        zip.setFill(Color.WHITE);
        zip.setFont(Font.font(null,20));
        TextField zipInput= new TextField();
        zipInput.setPrefColumnCount(5);

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
        //save button to save edited info and go back home to overview
        Button save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //some sort of function to save info
                //then go back to overview page
                overview(primaryStage);
            }
        });
        save.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        HBox name = new HBox();
        name.getChildren().addAll(fName, lName);
        name.setSpacing(25);
        HBox addr1 = new HBox();
        addr1.getChildren().addAll(addr, addrInput);
        HBox addr2 = new HBox();
        addr2.getChildren().addAll(city, cityInput, state, stateInput, zip, zipInput);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(quit, home, save);
        buttons.setSpacing(50);
        buttons.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(name, ssn, addr1, addr2, buttons);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(25);
        pane.setPadding(new Insets(10));
        scene = new Scene(pane, 700,700);
        primaryStage.setScene(scene);
    }

    public void saving(final Stage primaryStage){
        //name label
        Label name = new Label("name");
        name.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        //title:checking
        Label title = new Label("Savings Account");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //account overview: balance
        Text overview = new Text("Balance:");
        overview.setFont(Font.font(null, 20));
        overview.setFill(Color.WHITE);
        Text balance = new Text("$$$$");
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
        accBalance.add(overview, 0,0);
        accBalance.add(balance, 1,0);
        accBalance.setHgap(25);
        accBalance.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        if(teller || manager)pane.getChildren().addAll(name, title, accBalance, action, action2);
        else pane.getChildren().addAll(name, title, accBalance, transfer, action2);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }

    public void transfer(final Stage primaryStage, final int value){
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
        if(value == 1) {
            howMuch.setText("How much do you want to tranfer from the savings account?");
            transfer.setText("Transfer");
            transfer.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        }
        else if(value == 2){
            howMuch.setText("How much do you want to tranfer from the checking account?");
            transfer.setText("Transfer");
            transfer.setStyle("-fx-background-color: blue; -fx-text-fill: black");
        }
        //create input amount textField
        TextField amount = new TextField();
        amount.setMaxSize(250,250);
        amount.setAlignment(Pos.CENTER);
        //create buttons back and quit
        Button back = new Button("Back");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(value == 1)checking(primaryStage);
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
    public void transferSuccess(final Stage primaryStage, final int value){
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
                if(value == 1) checking(primaryStage);
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
        GridPane balance =  new GridPane();
        balance.add(checkingBalance, 0,0);
        balance.add(savingBalance, 0,1);
        balance.add(chBalance, 1,0);
        balance.add(saBalance, 1,1);
        balance.setAlignment(Pos.CENTER);
        balance.setHgap(20);
        VBox pane = new VBox(message, balance, action);
        pane.setStyle("-fx-background-color: black");
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);
        scene = new Scene(pane, 700,700);
        primaryStage.setScene(scene);
    }
    public void CreditCard(final Stage primaryStage){
        //name label
        Label Fname = new Label("First Name:                   ");
        Label Lname = new Label("Last Name: ");
        Fname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Lname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //title:checking
        Label title = new Label("Credit Card");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        //Payment Due Date
        Text dueD=new Text("Payment due Date:");
        dueD.setFont(Font.font(null, 20));
        dueD.setFill(Color.WHITE);
        Text dueday= new Text("08/09/2018");
        dueday.setFont(Font.font(null, 20));
        dueday.setFill(Color.WHITE);
        //Minimum Payment
        Text MinP=new Text("Minimum Payment:");
        MinP.setFont(Font.font(null, 20));
        MinP.setFill(Color.WHITE);
        Text MinPayment= new Text("$$");
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
        Text his=new Text("History:");
        his.setFont(Font.font(null, 20));
        his.setFill(Color.WHITE);
        TableView hisTable=new TableView();
        TableColumn date = new TableColumn("Date");
        date.setPrefWidth(150);
        TableColumn source=new TableColumn("Source");
        source.setPrefWidth(150);
        TableColumn amount=new TableColumn("Amount");
        amount.setPrefWidth(150);
        hisTable.getColumns().addAll(date,source,amount);
        ScrollPane  hisBox=new ScrollPane();
        hisBox.setContent(hisTable);
        hisBox.setFitToWidth(true);
        hisBox.setPrefWidth(450);
        hisBox.setPrefHeight(150);
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
        Button pay = new Button("Pay");
        pay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    makePayment(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pay.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(pay, home, quit);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);;
        GridPane name = new GridPane();
        name.add(Fname,0,0);
        name.add(Lname,1,0);
        name.setHgap(25);
        name.setAlignment(Pos.CENTER);
        GridPane Due = new GridPane();
        Due.add(dueD,0,0);
        Due.add(dueday,1,0);
        Due.add(MinP,0,1);
        Due.add(MinPayment,1,1);
        Due.setHgap(25);
        Due.setAlignment(Pos.CENTER);
        GridPane accBalance = new GridPane();
        accBalance.add(availableC, 0,0);
        accBalance.add(available, 1,0);
        accBalance.add(limit, 2,0);
        accBalance.setAlignment(Pos.CENTER);
        GridPane history = new GridPane();
        history.add(his, 0,0);
        history.add(hisBox, 0,1);
        history.setAlignment(Pos.CENTER);

        VBox pane = new VBox();
        if(teller || manager)pane.getChildren().addAll(name, title, Due,accBalance,history, action);
        else pane.getChildren().addAll(name, title, Due,accBalance,history,action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }
    public void makePayment(final Stage primaryStage){
        Label title = new Label("Make a Payment");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        Text setPDate= new Text("Set Payment Date:");
        setPDate.setFont(Font.font(null, 20));
        setPDate.setFill(Color.WHITE);
        DatePicker dateP=new DatePicker();
        Text PAmount= new Text("Set Payment Date:");
        setPDate.setFont(Font.font(null, 20));
        setPDate.setFill(Color.WHITE);
        ToggleGroup RadioBGroup=new ToggleGroup();
        RadioButton CurrentB=new RadioButton("Current Balance :");
        Text currentBalance=new Text("$$$$");
        currentBalance.setFont(Font.font(null, 20));
        currentBalance.setFill(Color.WHITE);
        CurrentB.setToggleGroup(RadioBGroup);
        RadioButton MinB=new RadioButton("Minimun Balance :");
        Text MinBalance=new Text("$$");
        MinBalance.setFont(Font.font(null, 20));
        MinBalance.setFill(Color.WHITE);
        CurrentB.setToggleGroup(RadioBGroup);
        MinB.setToggleGroup(RadioBGroup);
        RadioButton other=new RadioButton("Other:");
        TextField payAmount=new TextField("Enter Amount");
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
        GridPane setPaymentdate=new GridPane();
        setPaymentdate.add(setPDate,0,0);
        setPaymentdate.add(dateP,1,0);
        setPaymentdate.setHgap(25);
        setPaymentdate.setAlignment(Pos.CENTER);
        GridPane pOptions=new GridPane();
        pOptions.add(CurrentB,0,0);
        pOptions.add(currentBalance,1,0);
        pOptions.add(MinB,0,1);
        pOptions.add(MinBalance,1,1);
        pOptions.add(other,0,2);
        pOptions.add(payAmount,1,2);
        pOptions.setVgap(20);
        pOptions.setAlignment(Pos.CENTER);
        HBox action = new HBox();
        action.getChildren().addAll(submit, back, quit);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(title,setPaymentdate,pOptions,action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }
    public void loan(final Stage primaryStage){
        //name label
        Label Fname = new Label("First Name:                   ");
        Label Lname = new Label("Last Name: ");
        Fname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        Lname.setStyle("-fx-text-fill: yellow; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 20");
        //title:checking
        Label title = new Label("Loan");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        //Payment Due Date
        Text dueD=new Text("Payment due Date:");
        dueD.setFont(Font.font(null, 20));
        dueD.setFill(Color.WHITE);
        Text dueday= new Text("08/09/2018");
        dueday.setFont(Font.font(null, 20));
        dueday.setFill(Color.WHITE);
        //Minimum Payment
        Text MinP=new Text("Minimum Payment:");
        MinP.setFont(Font.font(null, 20));
        MinP.setFill(Color.WHITE);
        Text MinPayment= new Text("$$");
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
        Button pay = new Button("Pay");
        pay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    makePayment(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pay.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(pay, home, quit);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);;
        GridPane name = new GridPane();
        name.add(Fname,0,0);
        name.add(Lname,1,0);
        name.setHgap(25);
        name.setAlignment(Pos.CENTER);
        GridPane info = new GridPane();
        info.add(dueD,0,0);
        info.add(dueday,1,0);
        info.add(MinP,0,1);
        info.add(MinPayment,1,1);
        info.add(balanceOA,0,2);
        info.add(OVBalance,1,2);
        info.add(interestR,0,3);
        info.add(interestRate,1,3);
        info.setHgap(25);
        info.setVgap(25);
        info.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        if(teller || manager)pane.getChildren().addAll(name, title, info, action);
        else pane.getChildren().addAll(name, title, info,action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }
    public void lookUp(final Stage primaryStage){
        //title:checking
        Label title = new Label("Look Up");
        title.setStyle("-fx-text-fill: white; -fx-text-stroke: white; -fx-font-weight: bold; -fx-font-size: 25");
        Text searchBy=new Text("Search By:");
        searchBy.setFont(Font.font(null, 20));
        searchBy.setFill(Color.WHITE);
        TextField searchInput=new TextField();
        searchInput.setPrefWidth(150);
        ChoiceBox cb=new ChoiceBox(FXCollections.observableArrayList("SSN","First Name","Last Name","Zip code"));
        //Search box
        Text availableC=new Text("Available Customer:");
        availableC.setFont(Font.font(null, 20));
        availableC.setFill(Color.WHITE);
        TableView availableCTable=new TableView();
        TableColumn SSN = new TableColumn("SSN");
        SSN.setPrefWidth(125);
        TableColumn fName=new TableColumn("First Name");
        fName.setPrefWidth(125);
        TableColumn lName=new TableColumn("Last Name");
        lName.setPrefWidth(125);
        TableColumn zipCode=new TableColumn("Zipcode");
        zipCode.setPrefWidth(125);
        availableCTable.getColumns().addAll(SSN,fName,lName,zipCode);
        ScrollPane  customerBox=new ScrollPane();
        customerBox.setContent(availableCTable);
        customerBox.setFitToWidth(true);
        customerBox.setPrefWidth(500);
        customerBox.setPrefHeight(150);
        //buttons for home, quite, and transfer money
        Button search = new Button("Search");
        search.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
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
        action.getChildren().addAll( search, quit);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);;
        GridPane Customer = new GridPane();
        Customer.add(availableC, 0,0);
        Customer.add(customerBox, 0,1);
        Customer.setAlignment(Pos.CENTER);
        GridPane searchByPane=new GridPane();
        searchByPane.add(searchBy,0,0);
        searchByPane.add(cb,1,0);
        searchByPane.add(searchInput,2,0);
        searchByPane.setAlignment(Pos.CENTER);
        VBox pane = new VBox();
        pane.getChildren().addAll(title,searchByPane, Customer,action);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);
    }
    public void notEnoughMoney(Stage primaryStage,int value){
        //create message text
        Text message = new Text("Not Enough Balance ");
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
        Text creditBalance = new Text("Credit balance:");
        creditBalance.setFill(Color.WHITE);
        Text crBalance = new Text("$$$$");
        crBalance.setFill(Color.WHITE);
        Text loanBalance = new Text("Loan balance:");
        loanBalance.setFill(Color.WHITE);
        Text loBalance = new Text("$$$$");
        loBalance.setFill(Color.WHITE);
        //button for back, home, and quit
        Button back = new Button("Back");
        back.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button quit = new Button("Quit");
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(quit, back);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        GridPane balance =  new GridPane();
        Text accountType=new Text();
        Text accountBalance=new Text();
        if(value==1){
            accountType=checkingBalance;
            accountBalance=chBalance;
        }else if(value==2){
            accountType=savingBalance;
            accountBalance=saBalance;
        }else if(value==3){
            accountType=creditBalance;
            accountBalance=crBalance;
        }else{
            accountType=loanBalance;
            accountBalance=loBalance;
        }
        balance.add(accountType, 0,0);
        balance.add(accountBalance, 1,0);
        balance.setAlignment(Pos.CENTER);
        balance.setHgap(20);
        VBox pane = new VBox(message, balance, action);
        pane.setStyle("-fx-background-color: black");
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);
        scene = new Scene(pane, 700,700);
        primaryStage.setScene(scene);
    }
    public void invalidInput(Stage primaryStage){
        //create message text
        Text message = new Text("Invalid Input");
        message.setFill(Color.YELLOW);
        message.setFont(Font.font(null, 30));
        //button for back, home, and quit
        Button back = new Button("Back");
        back.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        Button quit = new Button("Quit");
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(quit, back);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        VBox pane = new VBox(message, action);
        pane.setStyle("-fx-background-color: black");
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);
        scene = new Scene(pane, 700,700);
        primaryStage.setScene(scene);
    }
}


