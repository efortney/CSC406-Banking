import com.sun.rowset.internal.Row;
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

//db imports
import database.Customer.Customer;
import database.Customer.CustomerQuery;

import java.util.Collection;
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
    String SSN ;
    String FirstName;
    String LastName;
    String Address;
    String City ;
    String State ;
    String ZipCode;
    String Balance;
    String password;
    TableView<database> availableCTable;
    String fieldPicked;


    Button submit,add;
    TextField fnameInput, lnameInput, addressInput, ssnInput, zipCodeInput, cityInput;


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
        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll("SSN", "First Name", "Last Name", "Zip code");
        //Search box
        Text availableC = new Text("Available Customer:");
        availableC.setFont(Font.font(null, 20));
        availableC.setFill(Color.WHITE);

        //get SSN
        TableColumn<database, String> ssn = new TableColumn<>("SSN");
        ssn.setPrefWidth(85);
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
        availableCTable.getColumns().addAll(ssn, first_name, lastName, address, city, state, zipcode);
        availableCTable.setEditable(true);

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

        add = new Button("Add");
        add.setOnAction(e -> {
            if(ssnInput.getText()==null||fnameInput.getText()==null||lnameInput.getText()==null||addressInput.getText()==null||State==null||zipCodeInput.getText()==null||cityInput.getText()==null){
                AlertBox.display("Invalid input","Invalid input",0);
            }else{
            Customer newCustomer=new Customer(ssnInput.getText(),fnameInput.getText(),lnameInput.getText(),addressInput.getText(),cityInput.getText(),State,zipCodeInput.getText());
            addDataToDatabase(newCustomer);
                fnameInput.clear();
                lnameInput.clear();
                ssnInput.clear();
                zipCodeInput.clear();
                addressInput.clear();
                stateBox.getItems().clear();
                cityInput.clear();
            }
        });

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
        //"SSN", "First Name", "Last Name", "Zip code")
        Button search = new Button("Search");
        search.setOnAction(e -> {
            cb.getSelectionModel().selectedItemProperty()
                    .addListener((v, oldValue, newValue) -> {
                            fieldPicked=newValue.toString();
                    });
            if (fieldPicked.equals("SSN")){
                searchCustomerbySSN(searchInput.getText(),availableCTable);
            }else if (fieldPicked.equals("First Name")){
                searchCustomerbyfname(searchInput.getText(),availableCTable);
            }else if (fieldPicked.equals("Last Name")){
                searchCustomerbyLastname(searchInput.getText(),availableCTable);
            }else if (fieldPicked.equals("Zip code")){
                searchCustomerbyzip(searchInput.getText(),availableCTable);
            }else{
                AlertBox.display("Error","Please pick a search field",0);
            }
            searchInput.clear();
        });
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
        pane.getChildren().addAll(title, searchByPane, Customer, action, signUp, fRowlayout, sRowlayout, add);
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

    public ObservableList<database> searchCustomerbySSN(String SSN,TableView newdata) {
        ObservableList<database>foundCustomer=null;
        try {
            Customer customer1 = new CustomerQuery().getBySSN(SSN).getFirst();
          foundCustomer=FXCollections.observableArrayList(
                    new database(customer1.getSSN(),customer1.getFNAME(),customer1.getLNAME(),customer1.getSTREET_ADDRESS(),customer1.getCITY(),customer1.getSTATE(),customer1.getZIP())
            );
          newdata.setItems(foundCustomer);
        } catch (Exception er) {
            AlertBox.display("Invalid SSN", "Can not find SSN\n Please Enter Again", 0);
        }
        return foundCustomer;
    }
    public ObservableList<database> searchCustomerbyfname(String fname,TableView newdata) {
        ObservableList<database>foundCustomer=null;
        try {
            Customer customer1 = new CustomerQuery().getByfname(fname).getFirst();
            foundCustomer=FXCollections.observableArrayList(
                    new database(customer1.getSSN(),customer1.getFNAME(),customer1.getLNAME(),customer1.getSTREET_ADDRESS(),customer1.getCITY(),customer1.getSTATE(),customer1.getZIP())
            );
            newdata.setItems(foundCustomer);
        } catch (Exception er) {
            AlertBox.display("Invalid first name", "Can not find first name\n Please Enter Again", 0);
        }
        return foundCustomer;
    }
    public ObservableList<database> searchCustomerbyLastname(String lname,TableView newdata) {
        ObservableList<database>foundCustomer=null;
        try {
            Customer customer1 = new CustomerQuery().getBylname(lname).getFirst();
            foundCustomer=FXCollections.observableArrayList(
                    new database(customer1.getSSN(),customer1.getFNAME(),customer1.getLNAME(),customer1.getSTREET_ADDRESS(),customer1.getCITY(),customer1.getSTATE(),customer1.getZIP())
            );
            newdata.setItems(foundCustomer);
        } catch (Exception er) {
            AlertBox.display("Invalid last name", "Can not find last name\n Please Enter Again", 0);
        }
        return foundCustomer;
    }
    public ObservableList<database> searchCustomerbyzip(String zip,TableView newdata) {
        ObservableList<database>foundCustomer=null;
        try {
            Customer customer1 = new CustomerQuery().getByzip(zip).getFirst();
            foundCustomer=FXCollections.observableArrayList(
                    new database(customer1.getSSN(),customer1.getFNAME(),customer1.getLNAME(),customer1.getSTREET_ADDRESS(),customer1.getCITY(),customer1.getSTATE(),customer1.getZIP())
            );
            newdata.setItems(foundCustomer);

        } catch (Exception er) {
            AlertBox.display("Invalid zipcode", "Can not find zipcode\n Please Enter Again", 0);
        }
        return foundCustomer;
    }
    // availableCTable.setItems( searchCustomerbySSN("343221891",availableCTable));
    public void displayAlluser(TableView alluser){
        alluser.setItems( searchCustomerbySSN("343221891",alluser));
        alluser.setItems( searchCustomerbyfname("Broderick",alluser));
        alluser.setItems( searchCustomerbySSN("113212530",alluser));
        alluser.setItems( searchCustomerbySSN("314213777",alluser));
        alluser.setItems( searchCustomerbySSN("234274322",alluser));
        alluser.setItems( searchCustomerbySSN("826219321",alluser));
        alluser.setItems( searchCustomerbySSN("927281623",alluser));
        alluser.setItems( searchCustomerbySSN("393731845",alluser));
        alluser.setItems( searchCustomerbySSN("126951584",alluser));
        alluser.setItems( searchCustomerbySSN("828235791",alluser));
        alluser.setItems( searchCustomerbySSN("927261987",alluser));
        alluser.setItems( searchCustomerbySSN("143299783",alluser));
        alluser.setItems( searchCustomerbySSN("723419193",alluser));
        alluser.setItems( searchCustomerbySSN("527216921",alluser));
        alluser.setItems( searchCustomerbySSN("277271419",alluser));

    }

    public void addDataToDatabase(Customer newCustomer){
       if(newCustomer.add()==true){
           AlertBox.display("Successful","New Customer addeed",0);
       }
       else{AlertBox.display("Unsuccessful","New Customer not addeed",0);
       }
    }
    public void deleteButtonClicked(){

    }
}


