/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.authorization;

//import com.sun.deploy.trace.Trace;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class AuthorizationController implements Initializable {

    @FXML private Button btAuthorization;
    @FXML private PasswordField pfPassword;
    private boolean authorization;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btAuthorization.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Обработка события для левой кнопки мыши
                setAuthorization(authorization());
            }
        });
        
        btAuthorization.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               setAuthorization(authorization());
            }
        });
   
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setAuthorization(authorization());
            }
        });
    }    

   
    private boolean authorization(){
        if(pfPassword.equals(jptv22fxshop.JPTV22FXShop.currentUser.getPassword())){
            return true;
        }else{
            return false;
        }
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }
    
    
}
