/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products.listproducts;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import jptv22fxshop.JPTV22FXShop;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class ListproductsController implements Initializable {
    private JPTV22FXShop app;
    @FXML
    private HBox hbListProductsRoot;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public JPTV22FXShop getApp() {
        return app;
    }

    public void setApp(JPTV22FXShop app) {
        this.app = app;
    }
    
}
