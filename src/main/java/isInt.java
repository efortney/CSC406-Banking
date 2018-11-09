import javafx.scene.control.TextField;

public class isInt {
    public static boolean display(TextField user, String userName) {
        try {
            int userID = Integer.parseInt(user.getText());
            return true;
        } catch (NumberFormatException e) {
            ;
            return false;
        }
    }
}