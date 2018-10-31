
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.paint.Color;

import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;


import database.Customer.Customer;
import database.Customer.CustomerQuery;
import controller.CustomerController;


public class CustomerLoginFormTest extends Application
{

  public static void main(String[] args)
  {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage)
  {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25,25,25,25));

    Scene scene = new Scene(grid, 500, 475);

    Text scenetitle = new Text("Please Login: ");
    scenetitle.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
    Label userName = new Label("User Name:");
    TextField userTextField = new TextField();
    Label pw = new Label("Password:");
    PasswordField pwBox = new PasswordField();
    
    //row0, column0, span2 columns, span1 row
    grid.add(scenetitle,//UI control
                      0,//row index
                      0,//column index
                      2,//column span
                      1);//row span
            
    grid.add(userName,0,1);//row index,column index
    grid.add(userTextField,1,1);//row index,column index
    grid.add(pw,0,2);//row index, column index
    grid.add(pwBox,1,2);//row index, column index

    grid.setGridLinesVisible(false);

    Button loginBtn = new Button("Sign in");
    Button helpBtn = new Button("Examples?");
    Button addBtn = new Button("Add TEST");
    Button deleteBtn = new Button("Delete TEST");
    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(loginBtn);
    hbBtn.getChildren().add(helpBtn);
    hbBtn.getChildren().add(addBtn);
    hbBtn.getChildren().add(deleteBtn);
    grid.add(hbBtn,1,4);

    final Text actiontarget = new Text();
    grid.add(actiontarget,1,6);

    
    loginBtn.setOnAction(new EventHandler<ActionEvent>()
    {
      @Override
      public void handle(ActionEvent event)
      {
        Customer loggedIn = null;

        loggedIn = CustomerController.authorizeCustomer(userTextField.getText(),pwBox.getText());

        if(loggedIn != null)
        {
          actiontarget.setFill(Color.GREEN);
          actiontarget.setText("Hello " + loggedIn.getFNAME() + " " + loggedIn.getLNAME());
        }
        else
        {
          actiontarget.setFill(Color.FIREBRICK);
          actiontarget.setText("Wrong username or password.");
        }

      }
    });

    
    helpBtn.setOnAction(event ->
    {
      List<Customer> customers = new CustomerQuery().getAll().execute();
      
      StringBuilder results = new StringBuilder("");

      actiontarget.setFill(Color.BLUE);
      for(Customer c : customers)
      {
        results.append(c.getFNAME() + " " + c.getLNAME() + "==>" +c.getUSERNAME() + " : " + c.getPASSWORD() + "\n");
      }

      actiontarget.setText(results.toString());

    });

    addBtn.setOnAction(event ->
    {
      if(new CustomerQuery().getBySSN(0).getFirst() == null)
      {
        //Add a new test customer
        Customer newCustomer = new Customer("TEST",//FNAME
                                            "TEST",//LNAME
                                            0,//SSN
                                            "TEST",//USERNAME
                                            "TEST",//PASSWORD
                                            "TEST@TEST",//EMAIL
                                            "TEST",//STREET_ADDRESS
                                            "TEST",//CITY
                                            "TEST",//STATE
                                            0);//ZIP

        //add new customer
        newCustomer.add();
        
        helpBtn.fire();

      }//end of if
      else 
      {
        actiontarget.setFill(Color.BLUE);
        actiontarget.setText("TEST already added.");
      }

    });


    deleteBtn.setOnAction(event ->
    {
      Customer test = new CustomerQuery().getBySSN(0).getFirst();
      if(test != null)
      {
        test.delete();
        helpBtn.fire();
      }
      if(test == null)
      { 
        actiontarget.setFill(Color.BLUE);
        actiontarget.setText("TEST doesn't exist.");
      }

    });
    

    primaryStage.setTitle("JavaFX Login Form");
    primaryStage.setScene(scene);
    primaryStage.show();
  }//end of start()

}//end of SceneGraph class
 
