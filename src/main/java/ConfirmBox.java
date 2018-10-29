import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
//How to use this:
//boolean result = ConfirmBox.display("title","message");
//can use for when user try tot close the program
public class ConfirmBox {
    static boolean answer;

    public static boolean display(String title, String message, int accountType) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        //create message text
        window.setTitle(title);
        Label lable = new Label(message);
        lable.setStyle("-fx-background-color: black; -fx-text-fill: yellow");
        lable.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 25));


        //Create two buttons
        Button yesButton = new Button("YES");
        yesButton.setPrefWidth(50);
        Button noButton = new Button("NO");
        noButton.setPrefWidth(50);
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });
        GridPane choice = new GridPane();
        choice.add(yesButton, 0, 0);
        choice.add(noButton, 1, 0);
        choice.setHgap(25);
        choice.setAlignment(Pos.CENTER);

        VBox layout = new VBox();
        layout.getChildren().addAll(lable, choice);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: black");

        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        //window will not close until you close or hide it
        window.showAndWait();
        return answer;
    }
    /*how to close a program properly
    window.setOnCloseRequest(e->{
        e.consume();
        closeProgram();
    })
    public void closeProgram(){
        Boolean answer=ConfirmBox.display("title","message",1);
        if(answer)
            window.close();
    }*/
}