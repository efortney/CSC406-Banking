import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import database.Customer.Customer;
import database.Customer.CustomerQuery;

import java.util.ArrayList;
import java.util.List;

public class CustomerTableViewSample extends Application {

    //table
    private TableView<UICustomer> table;
    //items to populate table
    private ObservableList<UICustomer> data;

    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
        //items to populate table
        ArrayList<UICustomer> uiCustomerArrayList = new ArrayList<>();

        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(800);
        stage.setHeight(600);


        //next 3 ui controls are arranged in HBox (single row)
        final Label label = new Label("Customers Search");
        label.setFont(new Font("Arial", 20));

        final ComboBox searchTypeComboBox = new ComboBox();
        searchTypeComboBox.getItems().addAll(
                "SSN",
                "First Name",
                "Last Name",
                "Zip Code"

        );
        //set ssn as default option
        searchTypeComboBox.getSelectionModel().selectFirst();

        TextField searchBox = new TextField();
        Button searchButton = new Button("Search");
        Button getAllButton = new Button("Get All");

        //a single row for combobox, search input, and search button
        final HBox hbox = new HBox();
        hbox.setSpacing(25);
        hbox.getChildren().addAll(searchTypeComboBox, searchBox, searchButton, getAllButton);

        //when search button clicked
        searchButton.setOnAction(event ->
        {
            String stype = searchTypeComboBox.getValue().toString();
            //remove old contents
            uiCustomerArrayList.clear();
            //populate ui data
            for(Customer c : searchCustBy(stype, searchBox))
            {
                uiCustomerArrayList.add(new UICustomer(c.getFNAME(),
                                                       c.getLNAME(),
                                                       c.getSSN(),
                                                       c.getSTREET_ADDRESS(),
                                                       c.getCITY(),
                                                       c.getSTATE(),
                                                       c.getZIP()));
            }
            //add ui data to TableView
            table.setItems(FXCollections.observableArrayList(uiCustomerArrayList));

        });

        //when getAll button clicked
        getAllButton.setOnAction(event ->
        {
            //remove old contents
            uiCustomerArrayList.clear();
            //populate ui data
            for(Customer c : getAllCustomers())
            {
                uiCustomerArrayList.add(new UICustomer(c.getFNAME(),
                        c.getLNAME(),
                        c.getSSN(),
                        c.getSTREET_ADDRESS(),
                        c.getCITY(),
                        c.getSTATE(),
                        c.getZIP()));
            }
            //add ui data to TableView
            table.setItems(FXCollections.observableArrayList(uiCustomerArrayList));

        });

        /*
         *   This VBox is the parent container within this scene.
         */
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPrefWidth(750);

        vbox.setPadding(new Insets(10, 10, 0, 10));

        //initialize ViewTable
        initCustTable();

        vbox.getChildren().addAll(label, hbox, table);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
 
        stage.setScene(scene);
        stage.show();
    }

    public List<Customer> getAllCustomers()
    {
        return new CustomerQuery().getAll().execute();
    }

    //calls appropriate query based on combobox selection and search input
    // (checks for appropriate input type can add alert box for empty or
    //         incorrect search criteria type)
    public List<Customer> searchCustBy(String stype, TextField searchBox)
    {
        List<Customer> customers = new ArrayList<>();

        if(stype.equals("SSN"))
        {
            if(!isInt(searchBox.getText())) return customers;
            else {
                customers = new CustomerQuery().getBySSN(Integer.parseInt(searchBox.getText())).execute();
            }
        }
        if(stype.equals("First Name"))
        {
            customers = new CustomerQuery().getByfname(searchBox.getText()).execute();
        }
        if(stype.equals("Last Name"))
        {
            customers = new CustomerQuery().getBylname(searchBox.getText()).execute();
        }
        if(stype.equals("Zip Code"))
        {
            if(!isInt(searchBox.getText())) return customers;
            else {
                customers = new CustomerQuery().getByzip(Integer.parseInt(searchBox.getText())).execute();
            }
        }

        return customers;
    }

    //type checking of search input
    private static boolean isInt(String str)
    {
        try
        {
            int i = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    //configure table columns and associated UICustomer fields
    //set resize policy, add columns, populate initial data (none)
    public void initCustTable()
    {
        table = new TableView<>();
        /*
         *      Define and map table columns to UICustomer fields
         */
        TableColumn SSNCol = new TableColumn("SSN");
        SSNCol.setMinWidth(50);
        SSNCol.setCellValueFactory(
                new PropertyValueFactory<UICustomer, Integer>("ssn"));

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(50);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<UICustomer, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(50);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<UICustomer, String>("lastName"));

        TableColumn addressCol = new TableColumn("Street Address");
        addressCol.setMinWidth(50);
        addressCol.setCellValueFactory(
                new PropertyValueFactory<UICustomer, String>("streetAddress"));

        TableColumn cityCol = new TableColumn("City");
        cityCol.setMinWidth(50);
        cityCol.setCellValueFactory(
                new PropertyValueFactory<UICustomer, String>("city"));

        TableColumn stateCol = new TableColumn("State");
        stateCol.setMinWidth(50);
        stateCol.setCellValueFactory(
                new PropertyValueFactory<UICustomer, String>("state"));

        TableColumn zipCol = new TableColumn("Zip Code");
        zipCol.setMinWidth(50);
        zipCol.setCellValueFactory(
                new PropertyValueFactory<UICustomer, Integer>("zip"));


        //can't update via table
        table.setEditable(false);
        //see TableViewColumnResizePolicyDemo
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //add columns to TableView
        table.getColumns().addAll(SSNCol, firstNameCol, lastNameCol, addressCol, cityCol, stateCol, zipCol);
        //populate TableView
        table.setItems(data);

    }
    public class UICustomer {

        private final SimpleStringProperty firstname;
        private final SimpleStringProperty lastname;
        private final SimpleIntegerProperty ssn;
        private final SimpleStringProperty streetaddress;
        private final SimpleStringProperty city;
        private final SimpleStringProperty state;
        private final SimpleIntegerProperty zip;
 
        private UICustomer(String fName, String lName, int ssn, String street,String city, String state, int zip ) {
            this.firstname = new SimpleStringProperty(fName);
            this.lastname = new SimpleStringProperty(lName);
            this.ssn = new SimpleIntegerProperty(ssn);
            this.streetaddress = new SimpleStringProperty(street);
            this.city = new SimpleStringProperty(city);
            this.state = new SimpleStringProperty(state);
            this.zip = new SimpleIntegerProperty(zip);
        }
 
        public String getFirstName() {
            return firstname.get();
        }
 
        public void setFirstName(String fName) {
            firstname.set(fName);
        }
 
        public String getLastName() {
            return lastname.get();
        }
 
        public void setLastName(String fName) {
            lastname.set(fName);
        }
 
        public int getSsn() {
            return ssn.get();
        }
 
        public void setSsn(int ssn) {
            this.ssn.set(ssn);
        }

        public String getStreetAddress() {
            return streetaddress.get();
        }

        public void setStreetAddress(String address) {
            streetaddress.set(address);
        }

        public String getCity() {
            return city.get();
        }

        public void setCity(String city) {
            this.city.set(city);
        }

        public String getState() {
            return state.get();
        }

        public void setState(String state) {
            this.state.set(state);
        }

        public int getZip() {
            return zip.get();
        }

        public void setZip(int zip) {
            this.zip.set(zip);
        }
    }
} 
