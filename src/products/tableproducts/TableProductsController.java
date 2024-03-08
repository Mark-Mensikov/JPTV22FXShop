/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products.tableproducts;

import products.product.ProductController;
import entity.Product;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jptv22fxshop.HomeController;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class TableProductsController implements Initializable {

    
    @FXML private TableView tvProductsRoot;
    private HomeController homeController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    
    
    public void initTable() {
       
        tvProductsRoot.setItems(FXCollections.observableArrayList(
                homeController.getEntityManager()
                        .createQuery("SELECT b FROM Product b")
                        .getResultList()
        ));
        TableColumn<Product, String> idProductCol = new TableColumn<>("№");
        idProductCol.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        TableColumn<Product, String> titleProductCol = new TableColumn<>("Название книги");
        titleProductCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        TableColumn<Product, Image> coverProductCol = new TableColumn<>("Обложка");
        coverProductCol.setCellValueFactory(cellData -> {
            Image coverImage = new Image(cellData.getValue().getCoverAsStream());
            return new SimpleObjectProperty<>(coverImage);
        });
        coverProductCol.setCellFactory(param -> new ImageViewTableCell<>());
        tvProductsRoot.getColumns().addAll(idProductCol,titleProductCol,coverProductCol);
        tvProductsRoot.setRowFactory(tv ->{
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    Product product = row.getItem();
                    System.out.println("Выбран продукт с ID: " + product.getId());
                    ProductController ProductController = new ProductController();
                    ProductController.setHomeController(homeController);
                    ProductController.showProduct(product);
                }
            });
            return row;
        });
    }

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    class ImageViewTableCell<S, T> extends TableCell<S, T> {
        private final ImageView imageView = new ImageView();
        public ImageViewTableCell() {
            imageView.setFitWidth(50);
            imageView.setFitHeight(80);
        }
        
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                imageView.setImage((Image) item);
                setGraphic(imageView);
            }
        }
    }
    
    
}
