/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.login;

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
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import jptv22fxshop.HomeController;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class LoginController implements Initializable {
    
    private EntityManager em;
    
    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;
    @FXML private Label lbInfo;
    @FXML private Label lbLoginTitle;
    @FXML private Button btEnter;
    private HomeController homeController;
    
    @FXML private void clickButtonEnter(){
        try {
            //Запрос SELECT u FROM User u WHERE u.login = :login выбирает из базы состояние объекта
            // класса User, где поле login = плейсхолдеру :login, который заполнятеся с помощью 
            // setParameter(<имя плейсхолдера без двоеточия>,<значение>)
            // .getSingleResult() возвращает один результат запроса типа Object
            // если запрос ничего не найдет выбрасывается исключение
            PassEncrypt pe = new PassEncrypt();
            User user = (User) em.createQuery("SELECT u FROM User u WHERE u.login = :login")
                    .setParameter("login", tfLogin.getText())
                    .getSingleResult();
            if(user.getPassword().equals(pe.getEncryptPassword(pfPassword.getText().trim(),pe.getSalt()))){
                jptv22fxshop.JPTV22FXShop.currentUser = user;
                this.homeController.setLbInfoUser(String.format(
                                        "Вы вошли как %s (%s %s)",
                                        user.getLogin(),
                                        user.getFirstname(), 
                                        user.getLastname()
                                ));
            }
            this.homeController.setLbInfoHome("");
            this.homeController.getVbHomeContent().getChildren().clear();
            
        } catch (Exception e) {
            this.homeController.setLbInfoHome("Нет такого пользователя или неправильный пароль");
        }
        tfLogin.setText("");
        pfPassword.setText("");
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickButtonEnter();
            }
        });

        // Обработчик события для Button
        btEnter.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btEnter.fire();
            }
        });
    }    

    public void setEntityManager(EntityManager entityManager) {
       this.em = entityManager;
    }

    private boolean isAuthorization = false;
    public void autorizationAction() {
        tfLogin.setText(jptv22fxshop.JPTV22FXShop.currentUser.getLogin());
        tfLogin.setDisable(true);
        btEnter.setText("Авторизуйтесь");
        lbLoginTitle.setText("Введите пароль");
        // Обработчик события для Button
            //клик на активном Button левой кнопки мыши
        btEnter.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Обработка события для левой кнопки мыши
                setAuthorization(autorization());
            }
        });
        
        btEnter.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               setAuthorization(autorization());
            }
        });
   
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                setAuthorization(autorization());
            }
        });
        
    }
   
    

    private  boolean autorization() {
        if(jptv22fxshop.JPTV22FXShop.currentUser.getPassword().equals(pfPassword.getText())){
            return true;
        }else{
            return false;
        }
    }

    public void setAuthorization(boolean isAuthorization) {
        this.isAuthorization = isAuthorization;
    }

    public boolean isAuthorization() {
        return isAuthorization;
    }

    public void setHomeController(HomeController homeController) {
       this.homeController = homeController;
    }
    
}

