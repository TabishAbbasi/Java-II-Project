package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private Text titleText;
    @FXML
    private Label idLabel;
    @FXML
    private TextField idTextBox;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordTextBox;
    @FXML
    private Button loginBtn;
    @FXML
    private Label zoneLabel;

    @FXML
    public void initialize(){
        zoneLabel.setText("Zone: " + ZoneId.systemDefault().getId());
        ResourceBundle rb = ResourceBundle.getBundle("/lang/Lang", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr")){
            String title = rb.getString("Low") + " " +
                            rb.getString("Budget") + " " +
                            rb.getString("Scheduling") + " " +
                            rb.getString("App");
            titleText.setText(title);
            idLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            loginBtn.setText(rb.getString("Login"));
        }
    }

    public void onLogin(){
        if(idTextBox.getText().isEmpty() || passwordTextBox.getText().isEmpty()){
            //generate error
        } else{
            String userName = idTextBox.getText();
            String passWord = passwordTextBox.getText();
//            test to see if the password and username match given values (In Database).
//            if they do then procede to the next form, if not, then generate an error.
        }
    }

}
