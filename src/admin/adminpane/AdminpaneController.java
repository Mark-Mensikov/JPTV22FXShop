/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.adminpane;

import entity.User;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class AdminpaneController implements Initializable {
    private EntityManager em;
    private User selectedUser;
    private Enum selectedRole;
    @FXML private ComboBox cbUsers;
    @FXML private ComboBox cbRoles;
    @FXML private Label lbInfo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    public void setCbUsers() {
       cbUsers.setItems(FXCollections.observableArrayList(getEntityManager()
               .createQuery("SELECT u FROM User u WHERE u.login != :admin")
               .setParameter("admin", "admin")
               .getResultList()));
        cbUsers.setCellFactory(param -> new ListCell<User>(){
            @Override
            protected void updateItem(User user,boolean empty){
                super.updateItem(user, empty);
                if(user==null || empty){
                    setText(null);
                }else{
                    setText(user.getFirstname()
                                +" "
                                +user.getLastname()
                                +" ("
                                +user.getLogin()
                                +")" 
                                + " - роли " 
                                + Arrays.toString(user.getRoles().toArray()));
                }
            }
        });
        cbUsers.setButtonCell(new ListCell<User>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (user == null || empty) {
                    setText(null);
                } else {
                    setText(user.getFirstname()
                                +" "
                                +user.getLastname()
                                +" ("
                                +user.getLogin()
                                +")" 
                                + " - роли " 
                                + Arrays.toString(user.getRoles().toArray()));
                }
            }
        });
        cbUsers.setOnAction(event->{
            this.selectedUser = (User) cbUsers.getValue();
            //System.out.println(selectedUser.toString());
            lbInfo.setText("");
        });
    }

    public void setCbRoles() {
        cbRoles.setItems(FXCollections.observableArrayList(jptv22fxshop.JPTV22FXShop.roles.values()));
        //Настройка механизма отображения в ComboBox
        cbRoles.setCellFactory(param -> new ListCell<jptv22fxshop.JPTV22FXShop.roles>(){
            @Override
            protected void updateItem(jptv22fxshop.JPTV22FXShop.roles item,boolean empty){
                super.updateItem(item, empty);
                if(item==null || empty){
                    setText(null);
                }else{
                    setText(item.toString());
                }
            }
        });
        cbRoles.setButtonCell(new ListCell<jptv22fxshop.JPTV22FXShop.roles>() {
            @Override
            protected void updateItem(jptv22fxshop.JPTV22FXShop.roles item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        cbRoles.setOnAction(event->{
            this.selectedRole = (jptv22fxshop.JPTV22FXShop.roles) cbRoles.getValue();
            //System.out.println(selectedRole.toString());
            lbInfo.setText("");
        });
    }
    @FXML public void clickButtonAddRole(){
        if(!this.selectedUser.getRoles().contains(this.selectedRole.toString())){
            this.selectedUser.getRoles().add(this.selectedRole.toString());
            this.changeRole();
        }
    }
    @FXML public void clickButtonRemoveRole(){
        if(this.selectedUser.getLogin().equals("admin")){
            lbInfo.setText("Изменение невозможно!");
            return;
        }
        if(this.selectedUser.getRoles().contains(this.selectedRole.toString())){
            this.selectedUser.getRoles().remove(this.selectedRole.toString());
            this.changeRole();
        }
    }
    private void changeRole(){
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(this.selectedUser);
        getEntityManager().getTransaction().commit();
        setCbUsers();
    }
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
