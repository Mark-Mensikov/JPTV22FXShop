/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.profile;

import entity.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javax.persistence.EntityManager;
import jptv22fxshop.HomeController;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class ProfileController implements Initializable {
    private User updateUser;
    private HomeController homeController;
    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;
    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;
    @FXML private Button btUpdateProfile;
    
    @FXML private Label lbInfo;
    /**
     * Initializes the controller class.
     */
    @FXML private void clickUpdateProfile(){
        
        updateUser.setFirstname(tfFirstname.getText());
        updateUser.setLastname(tfLastname.getText());
        if(!pfPassword.getText().trim().isEmpty()){
            PassEncrypt pe = new PassEncrypt();
            updateUser.setPassword(pe.getEncryptPassword(pfPassword.getText().trim(),pe.getSalt()));
        }
        try {
            homeController.getEntityManager().getTransaction().begin();
            homeController.getEntityManager().merge(updateUser);
            homeController.getEntityManager().getTransaction().commit();
            jptv22fxshop.JPTV22FXShop.currentUser = updateUser;
            homeController.setLbInfoUser(String.format(
                                        "Вы вошли как %s (%s %s)",
                                        updateUser.getLogin(),
                                        updateUser.getFirstname(), 
                                        updateUser.getLastname()
                                ));
            lbInfo.setText("Профиль изменен");
        } catch (Exception e) {

            lbInfo.setText("Профиль изменить не удалось");
            System.out.println("error: e="+e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickUpdateProfile();
            }
        });

        // Обработчик события для Button
        btUpdateProfile.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickUpdateProfile();
            }
        });
    }    
    public void initProfileForm(){
        updateUser = homeController.getEntityManager().find(User.class, jptv22fxshop.JPTV22FXShop.currentUser.getId());
        tfFirstname.setText(updateUser.getFirstname());
        tfLastname.setText(updateUser.getLastname());
        tfLogin.setText(updateUser.getLogin());
        pfPassword.setText("");
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    
}
