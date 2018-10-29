import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
//How to use this:
//AlertBox.display("title","message",account type as integer);
public class AlertBox {
    public static void display(String title,String message,int accountType){
        Stage window =new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        //create message text
        Text output = new Text();
        output.setText(message);
        output.setFill(Color.YELLOW);
        output.setFont(Font.font(null, 25));
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
        Button close = new Button("Close");
        close.setOnAction(e->window.close());
        close.setStyle("-fx-background-color: yellow; -fx-text-fill: black");
        //create pane
        HBox action = new HBox();
        action.getChildren().addAll(close);
        action.setSpacing(50);
        action.setAlignment(Pos.CENTER);
        GridPane balance = new GridPane();
        Text accountT = new Text();
        Text accountBalance = new Text();
        if (accountType == 1) {
            accountT = checkingBalance;
            accountBalance = chBalance;
        } else if (accountType == 2) {
            accountT = savingBalance;
            accountBalance = saBalance;
        } else if (accountType == 3) {
            accountT = creditBalance;
            accountBalance = crBalance;
        } else if(accountType==4) {
            accountT = loanBalance;
            accountBalance = loBalance;
        }else
            accountT.setText("");
        accountBalance.setText("");
        balance.add(accountT, 0, 0);
        balance.add(accountBalance, 1, 0);
        balance.setAlignment(Pos.CENTER);
        balance.setHgap(20);
        VBox pane = new VBox(output, balance, action);
        pane.setStyle("-fx-background-color: black");
        pane.setSpacing(20);
        pane.setAlignment(Pos.CENTER);


        Scene scene = new Scene(pane, 300, 200);
        window.setScene(scene);
        //window will not close until you close or hide it
        window.showAndWait();
    }
}