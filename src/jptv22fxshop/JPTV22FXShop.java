/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv22fxshop;

import entity.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tools.PassEncrypt;

/**
 *
 * @author Melnikov
 */
public class JPTV22FXShop extends Application {
    public static enum roles {ADMINISTRATOR, MANAGER, USER};
    public static User currentUser;
    private final EntityManager em;
    private Stage primaryStage;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public JPTV22FXShop() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPTV22FXShopPU");
        em = emf.createEntityManager();
        createSuperUser();
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("JPTV22FXShop");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setApp(this);
        Scene scene = new Scene(root,WIDTH,HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/jptv22fxshop/home.css").toExternalForm());
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void createSuperUser() {
        try {
            em.createQuery("SELECT user FROM User user WHERE user.login = :login")
                            .setParameter("login", "admin")
                            .getSingleResult();
            
        } catch (Exception e) {
            User user = new User();
            user.setFirstname("Mark");
            user.setLastname("Mensikov");
            user.setLogin("admin");
            PassEncrypt pe = new PassEncrypt();
            user.setPassword(pe.getEncryptPassword("12345",pe.getSalt()));
            user.getRoles().add(jptv22fxshop.JPTV22FXShop.roles.ADMINISTRATOR.toString());
            user.getRoles().add(jptv22fxshop.JPTV22FXShop.roles.MANAGER.toString());
            user.getRoles().add(jptv22fxshop.JPTV22FXShop.roles.USER.toString());
            try {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } catch (Exception ex) {
                em.getTransaction().setRollbackOnly();
            }
        }
    }
    
}
