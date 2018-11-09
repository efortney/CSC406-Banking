
import database.Customer.Customer;
import database.Customer.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.util.List;

public class lookUp extends BankingApplication {

    public void display(Stage primaryStage) {
        //look up,delete and create a new account page
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
        cb.setValue("SSN");
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
        //create the table view to store the customers' info
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

        //create the labels for create a new account
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

        //create a combobox and store all the states in there
        Text Tstate = new Text("State:");
        Tstate.setFill(Color.WHITE);
        Tstate.setFont(Font.font(null, 20));
        ComboBox<String> stateBox = new ComboBox<>();
        stateBox.getItems().addAll("SL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA",
                "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM",
                "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
        stateBox.setVisibleRowCount(5);
        stateBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue != null) {
                        State = newValue.toString();
                    }
                });
        //create a button that will create a new customer
        add = new Button("Add");
        add.setOnAction(e -> {
            if (ssnInput.getText() == null || fnameInput.getText() == null || lnameInput.getText() == null || addressInput.getText() == null || State == null || zipCodeInput.getText() == null || cityInput.getText() == null) {
                alertbox.display("Invalid input", "Invalid input");
            } else {
                Customer newCustomer = new Customer(ssnInput.getText(), fnameInput.getText(), lnameInput.getText(), addressInput.getText(), cityInput.getText(), State, zipCodeInput.getText());
                addDataToDatabase(newCustomer);

            }
            fnameInput.clear();
            lnameInput.clear();
            ssnInput.clear();
            zipCodeInput.clear();
            addressInput.clear();
            stateBox.getItems().clear();
            cityInput.clear();
        });
        //create and set the layout
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
        search.setOnAction(e -> searchButtonClicked(cb, searchInput));
        search.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        //Button for show the customers
        Button showAll = new Button("All Users");
        showAll.setOnAction(e -> displayAlluser(availableCTable));
        showAll.setStyle("-fx-background-color: yellow; -fx-text-fill: black");

        //Look into
        Button lookInto = new Button("Look into");
        lookInto.setOnAction(e -> {
            try {
                overView overview = new overView();
                ObservableList<database> customerSelected;
                customerSelected = availableCTable.getSelectionModel().getSelectedItems();
                overview.display(primaryStage, customerSelected);
            } catch (Exception er) {
                alertbox.display("invalid selection", "Please select a customer");
            }
        });
        lookInto.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        //Button for back the program
        Button quit = new Button("Back");
        quit.setOnAction(e -> {
            try {
                start(primaryStage);
            } catch (Exception er) {
                er.printStackTrace();
            }
        });
        quit.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        //create a delete button
        Button delete = new Button("Delete");
        //-1470002102, Broderick, Jones, 277271419, 9119, bjones89, s1ll8s0nyar, bjones89@hotmail.com, Park Lane, St. Joseph, MO, 645010
        delete.setOnAction(e -> deleteButtonClicked(availableCTable));
        delete.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        //create pane for the actions
        HBox action = new HBox();
        action.getChildren().addAll(search, quit, delete, showAll, lookInto);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        //display Customers' info
        GridPane Customer = new GridPane();
        Customer.add(availableC, 0, 0);
        Customer.add(customerBox, 0, 1);
        Customer.setAlignment(Pos.CENTER);
        //Search Pane
        GridPane searchByPane = new GridPane();
        searchByPane.add(searchBy, 0, 0);
        searchByPane.add(cb, 1, 0);
        searchByPane.add(searchInput, 2, 0);
        searchByPane.setAlignment(Pos.CENTER);
        //create the Vbox to store all the layouts
        VBox pane = new VBox();
        pane.getChildren().addAll(title, searchByPane, Customer, action, signUp, fRowlayout, sRowlayout, add);
        pane.setStyle("-fx-background-color: black");
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        scene = new Scene(pane, 700, 700);
        primaryStage.setScene(scene);

    }

    //function that will search by SSM
    public void searchCustomerbySSN(String SSN, TableView newdata) {
        List<Customer> all = new CustomerQuery().getBySSN(SSN).execute();
        ObservableList<database> foundCustomer = FXCollections.observableArrayList();
        for (int i = 0; i < all.size(); i++) {
            foundCustomer.add(new database(all.get(i).getSSN(), all.get(i).getFNAME(), all.get(i).getLNAME(), all.get(i).getSTREET_ADDRESS(), all.get(i).getCITY(), all.get(i).getSTATE(), all.get(i).getZIP()));
        }
        newdata.setItems(foundCustomer);
        if (SSN.equals("") || all.size() == 0) {
            alertbox.display("Invalid SSN", "Can not find SSN\n Please Enter Again");
        }

    }

    //function that will search by First Name
    public void searchCustomerbyfname(String fname, TableView newdata) {
        List<Customer> all = new CustomerQuery().getByfname(fname).execute();
        ObservableList<database> foundCustomer = FXCollections.observableArrayList();
        for (int i = 0; i < all.size(); i++) {
            foundCustomer.add(new database(all.get(i).getSSN(), all.get(i).getFNAME(), all.get(i).getLNAME(), all.get(i).getSTREET_ADDRESS(), all.get(i).getCITY(), all.get(i).getSTATE(), all.get(i).getZIP()));
        }
        newdata.setItems(foundCustomer);

        if (fname.equals("") || all.size() == 0) {
            alertbox.display("Invalid first name", "Can not find first name\n Please Enter Again");
        }

    }

    //function that will search by Last Name
    public void searchCustomerbyLastname(String lname, TableView newdata) {
        List<Customer> all = new CustomerQuery().getBylname(lname).execute();
        ObservableList<database> foundCustomer = FXCollections.observableArrayList();
        for (int i = 0; i < all.size(); i++) {
            foundCustomer.add(new database(all.get(i).getSSN(), all.get(i).getFNAME(), all.get(i).getLNAME(), all.get(i).getSTREET_ADDRESS(), all.get(i).getCITY(), all.get(i).getSTATE(), all.get(i).getZIP()));
        }
        newdata.setItems(foundCustomer);

        if (lname.equals("") || all.size() == 0) {
            alertbox.display("Invalid last name", "Can not find last name\n Please Enter Again");
        }

    }

    //function that will search by Zip Code
    public void searchCustomerbyzip(String zip, TableView newdata) {

        List<Customer> all = new CustomerQuery().getByzip(zip).execute();
        ObservableList<database> foundCustomer = FXCollections.observableArrayList();
        for (int i = 0; i < all.size(); i++) {
            foundCustomer.add(new database(all.get(i).getSSN(), all.get(i).getFNAME(), all.get(i).getLNAME(), all.get(i).getSTREET_ADDRESS(), all.get(i).getCITY(), all.get(i).getSTATE(), all.get(i).getZIP()));
        }
        newdata.setItems(foundCustomer);

        if (zip.equals("") || all.size() == 0) {
            alertbox.display("Invalid zipcode", "Can not find zipcode\n Please Enter Again");
        }

    }

    //display all the customers
    public void displayAlluser(TableView table) {
        List<Customer> all = new CustomerQuery().getAll().execute();
        ObservableList<database> alluser = FXCollections.observableArrayList();
        for (int i = 0; i < all.size(); i++) {
            alluser.add(new database(all.get(i).getSSN(), all.get(i).getFNAME(), all.get(i).getLNAME(), all.get(i).getSTREET_ADDRESS(), all.get(i).getCITY(), all.get(i).getSTATE(), all.get(i).getZIP()));
        }
        table.setItems(alluser);
    }

    //add new customer to the database
    public void addDataToDatabase(Customer newCustomer) {
        //create a new customer if the SSN does not existed
        if (newCustomer.add() == true) {
            //alertbox=new AlertBox();
            alertbox.display("Successful", "New Customer added");
        } else {
            //check to see if there is a duplicate SSN
            //alertbox=new AlertBox();
            alertbox.display("Unsuccessful", "Customer already existed");
        }
    }

    //action when the search button is clicked
    public void searchButtonClicked(ComboBox cbox, TextField search) {
        try {
            String cbField = cbox.getValue().toString();
            //search by the combobox value that you picked and search by the field
            if (cbField.equals("SSN")) {
                searchCustomerbySSN(search.getText(), availableCTable);
            } else if (cbField.equals("First Name")) {
                searchCustomerbyfname(search.getText(), availableCTable);
            } else if (cbField.equals("Last Name")) {
                searchCustomerbyLastname(search.getText(), availableCTable);
            } else {
                searchCustomerbyzip(search.getText(), availableCTable);
            }
            search.clear();
        } catch (Exception er) {
            alertbox.display("Can't find user", "Can not find user");
        }
    }

    //action that when you clicked the delete button
    public void deleteButtonClicked(TableView table) {
        try {
            ObservableList<database> customerSelected, allCustomer;
            allCustomer = table.getItems();
            customerSelected = table.getSelectionModel().getSelectedItems();
            String selectedSSN = customerSelected.get(0).getSSN();
            customerSelected.forEach(allCustomer::remove);
            Customer deleteCustomer = new CustomerQuery().getBySSN(selectedSSN).getFirst();
            deleteCustomer.delete();
        } catch (Exception er) {
            alertbox.display("Can't find user", "Can not find user");
        }

    }
}
