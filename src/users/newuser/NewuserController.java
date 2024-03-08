/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.newuser;

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
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class NewuserController implements Initializable {

    private EntityManager entityManager;
    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;
    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;
    @FXML private Button btAddNewUser;
    
    @FXML private Label lbInfo;
    
    
    
    @FXML private void clickAddNewUser(){
        User user = new User();
        user.setFirstname(tfFirstname.getText());
        user.setLastname(tfLastname.getText());
        user.setLogin(tfLogin.getText());
        PassEncrypt pe = new PassEncrypt();
        user.setPassword(pe.getEncryptPassword(pfPassword.getText().trim(),pe.getSalt()));
        user.getRoles().add("USER");
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(user);
            getEntityManager().getTransaction().commit();
            tfFirstname.setText("");
            tfLastname.setText("");
            tfLogin.setText("");
            pfPassword.setText("");
            lbInfo.setText("Пользователь добавлен");
        } catch (Exception e) {
//            tfFirstname.setText("");
//            tfLastname.setText("");
//            tfLogin.setText("");
            pfPassword.setText("");
            lbInfo.setText("Такой пользователь уже");
            System.out.println("error: e="+e);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickAddNewUser();
            }
        });

        // Обработчик события для Button
        btAddNewUser.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btAddNewUser.fire();
            }
        });
    }    

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
