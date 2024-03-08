/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jptv22fxshop;

import admin.adminpane.AdminpaneController;
import products.product.ProductController;
import products.listproducts.ListproductsController;
import products.newproduct.NewproductController;
import products.returnproduct.ReturnProductController;
import products.tableproducts.TableProductsController;
import entity.Product;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import users.login.LoginController;
import users.newuser.NewuserController;
import users.profile.ProfileController;

/**
 *
 * @author Melnikov
 */
public class HomeController implements Initializable {
    private JPTV22FXShop app;
    private EntityManager em;
    @FXML private VBox vbHomeContent;
    @FXML private Label lbInfoHome;
    @FXML private Label lbInfoUser;
    

    public HomeController() {
        
    }
    @FXML public void clickMenuEditProfile(){
        
        if(!this.authorizationInfo(JPTV22FXShop.roles.USER.toString())){
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/profile/profile.fxml"));
            VBox vbProfileRoot = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setHomeController(this);
            profileController.initProfileForm();
            app.getPrimaryStage().setTitle("JPTV22FXShop - профайл пользователя");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbProfileRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /users/profile/profile.fxml", ex);
        }
    }
    @FXML public void clickMenuAddNewProduct(){
        
        if(!this.authorizationInfo(JPTV22FXShop.roles.MANAGER.toString())){
            return;
        }
        
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/products/newproduct/newproduct.fxml"));
            VBox vbNewProductRoot = loader.load();
            NewproductController newproductController = loader.getController();
            newproductController.setEntityManager(getEntityManager());
            app.getPrimaryStage().setTitle("JPTV22FXShop-добавление новой книги");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbNewProductRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /products/newproduct/newproduct.fxml", ex);
        }
    }
    
    @FXML public void clickMenuLogin(){
        clickMenuLogin("");
    }
    @FXML public void clickMenuLogin(String massage){
        lbInfoHome.setText(massage);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/login/login.fxml"));
            VBox vbLoginRoot = loader.load();
            LoginController loginController = loader.getController();
            loginController.setEntityManager(getEntityManager());
            loginController.setHomeController(this);
            app.getPrimaryStage().setTitle("JPTV22FXShop-Вход");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbLoginRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /users/login/login.fxml", ex);
        }
    }
    @FXML public void clickMenuLogout(){
        jptv22fxshop.JPTV22FXShop.currentUser = null;
        vbHomeContent.getChildren().clear();
        lbInfoHome.setText("Вы вышли!");
        lbInfoUser.setText("");
    }
    
    @FXML public void clickMenuAddNewUser(){
       
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/newuser/newuser.fxml"));
            VBox vbNewuserRoot = loader.load();
            NewuserController newuserController = loader.getController();
            newuserController.setEntityManager(getEntityManager());
            app.getPrimaryStage().setTitle("JPTV22FXShop-регистрация пользователя");
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(vbNewuserRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен //users/newuser/newuser.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuListProducts(){
        if(!this.authorizationInfo(JPTV22FXShop.roles.USER.toString())){
            return;
        }
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/products/listproducts/listproducts.fxml"));
            HBox hbListProductsRoot = loader.load();
           // ListproductsController listbooksController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22FXShop-список книг");
            List<Product> listProducts = getEntityManager().createQuery("SELECT b FROM Product b").getResultList();
            hbListProductsRoot.getChildren().clear();
            hbListProductsRoot.getStyleClass().add("border-hbox");
            for (int i = 0; i < listProducts.size(); i++) {
                Product product = listProducts.get(i);
                FXMLLoader productLoader = new FXMLLoader();
                productLoader.setLocation(getClass().getResource("/products/product/product.fxml"));
                ImageView ivCoverRoot = productLoader.load();
                ivCoverRoot.setCursor(Cursor.OPEN_HAND);
                ivCoverRoot.setId("small_product_cover");
                ProductController productController = productLoader.getController();
                productController.setHomeController(this);
                ivCoverRoot.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        productController.showProduct(product);
                    }
                });

                ivCoverRoot.setImage(new Image(new ByteArrayInputStream(product.getCover())));
                hbListProductsRoot.getChildren().add(ivCoverRoot);
            }
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(hbListProductsRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /products/listproducts/listproducts.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuReturnProduct(){
        if(!this.authorizationInfo(JPTV22FXShop.roles.USER.toString())){
            return;
        }
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/products/returnproduct/returnproduct.fxml"));
            TableView tvReturnProductRoot = loader.load();
            ReturnProductController returnProductController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22FXShop - возврат книги");
            returnProductController.setHomeController(this);
            returnProductController.initTable();
            vbHomeContent.getChildren().clear();
            tvReturnProductRoot.setPrefSize(vbHomeContent.getWidth(), vbHomeContent.getHeight());
            vbHomeContent.getChildren().add(tvReturnProductRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /productks/tableproducts/tableproducts.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuTableProducts(){
        if(!this.authorizationInfo(JPTV22FXShop.roles.USER.toString())){
            return;
        }
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/products/tableproducts/tableproducts.fxml"));
            TableView tvProductsRoot = loader.load();
            TableProductsController tableProductsController = loader.getController();
            app.getPrimaryStage().setTitle("JPTV22FXShop-список книг");
            tableProductsController.setHomeController(this);
            tableProductsController.initTable();
            vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(tvProductsRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /products/tableproducts/tableproducts.fxml", ex);
        }
    }
    @FXML 
    public void clickMenuShowAdminpane(){
        if(!this.authorizationInfo(JPTV22FXShop.roles.ADMINISTRATOR.toString())){
            return;
        }
        lbInfoHome.setText("");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/admin/adminpane/adminpane.fxml"));
            app.getPrimaryStage().setTitle("JPTV22FXShop - Панель администратора");
            AnchorPane apAdminRoot = loader.load();
            AdminpaneController adminpaneController = loader.getController();
            adminpaneController.setEntityManager(getApp().getEntityManager());
            adminpaneController.setCbUsers();
            adminpaneController.setCbRoles();
            apAdminRoot.setPrefWidth(JPTV22FXShop.WIDTH);
            apAdminRoot.setPrefHeight(JPTV22FXShop.HEIGHT);
            this.vbHomeContent.getChildren().clear();
            vbHomeContent.getChildren().add(apAdminRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Не загружен /admin/adminpane/adminpane.fxml", ex);
        }
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vbHomeContent.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        if(jptv22fxshop.JPTV22FXShop.currentUser == null){
            lbInfoUser.setText("Авторизуйтесь!");
        }else{
            lbInfoUser.setText("Управление программой от имени пользователя: "+jptv22fxshop.JPTV22FXShop.currentUser.getLogin());
        }
    }    

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public JPTV22FXShop getApp() {
        return app;
    }

    public void setApp(JPTV22FXShop app) {
        this.app = app;
        this.em = app.getEntityManager();
    }
    public void setLbInfoUser(String message){
        this.lbInfoUser.setText(message);
    }

    public void setLbInfoHome(String massage) {
       this.lbInfoHome.setText(massage);
    }

    public VBox getVbHomeContent() {
        return this.vbHomeContent;
    }

    private boolean authorizationInfo(String role) {
        if(jptv22fxshop.JPTV22FXShop.currentUser == null || !jptv22fxshop.JPTV22FXShop.currentUser.getRoles().contains(role)){
            lbInfoHome.setText("");
            vbHomeContent.getChildren().clear();
            if(jptv22fxshop.JPTV22FXShop.currentUser == null){
                    clickMenuLogin("Авторизуйтесь");
            }else{
                clickMenuLogin(jptv22fxshop.JPTV22FXShop.currentUser.getLogin() + " не имеет права на эту операцию");
            }
            return false;
        }
        return true;

    }

    public Label getLbInfoHome() {
        return lbInfoHome;
    }
    
}
