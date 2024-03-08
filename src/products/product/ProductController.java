/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products.product;

import entity.Product;
import entity.History;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jptv22fxshop.HomeController;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class ProductController implements Initializable {
    private Image image;
    private HomeController homeController;
    private Button btnRead;
    private Button btnClose;
    private Stage productWindow;
    @FXML
    private Pane pProductRoot;
    @FXML
    private ImageView ivCover;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void showProduct(Product product) {
       // System.out.println(product.toString());
       productWindow = new Stage();
       productWindow.setTitle(product.getTitle());
       productWindow.initModality(Modality.WINDOW_MODAL);
       productWindow.initOwner(homeController.getApp().getPrimaryStage());
       image = new Image(new ByteArrayInputStream(product.getCover()));
       ImageView ivCoverBig = new ImageView(image);
       ivCoverBig.setId("big_product_cover");
       VBox vbProduct = new VBox();
       vbProduct.setAlignment(Pos.CENTER);
       vbProduct.getChildren().add(ivCoverBig);
       btnRead = new Button("Читать");
       btnClose = new Button("Закрыть");
       btnClose.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Обработка события для левой кнопки мыши
                productWindow.close();
            }
        });
        
        btnClose.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               productWindow.close();
            }
        });
       btnRead.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Обработка события для левой кнопки мыши
                takeUpProduct(product);
            }
        });
        
        btnRead.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               takeUpProduct(product);
            }
        });
       HBox hbButtons = new HBox();
       hbButtons.setPrefSize(Double.MAX_VALUE, 29);
       hbButtons.alignmentProperty().set(Pos.CENTER_RIGHT);
       hbButtons.setSpacing(10);
       hbButtons.setPadding(new Insets(10));
       hbButtons.getChildren().addAll(btnRead,btnClose);
       vbProduct.getChildren().add(hbButtons);
       Scene scene = new Scene(vbProduct,450,700);
       scene.getStylesheets().add(getClass().getResource("/products/product/product.css")
               .toExternalForm());
       productWindow.setScene(scene);
       productWindow.show();
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    private void takeUpProduct(Product product) {
        History history = new History();
        history.setProduct(product);
        history.setUser(jptv22fxshop.JPTV22FXShop.currentUser);
        history.setGiveProductToBuyerDate(new GregorianCalendar().getTime());
        try {
            homeController.getEntityManager().getTransaction().begin();
            homeController.getEntityManager().persist(history);
            homeController.getEntityManager().getTransaction().commit();
            homeController.getLbInfoHome().setText(String.format("Продукт выдан покупателю \"%s\"",
                    jptv22fxshop.JPTV22FXShop.currentUser.getLogin()
                )
            );
            productWindow.close();
        } catch (Exception e) {
            homeController.getEntityManager().getTransaction().rollback();
        }
    }

    public void returnProduct(Product product) {
        
    }
}
