/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products.returnproduct;

import products.product.ProductController;
import products.tableproducts.TableProductsController;
import entity.Product;
import entity.History;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
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
public class ReturnProductController implements Initializable {
    private HomeController homeController;
    
    @FXML
    private TableView tvReturnProductRoot;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    public void setHomeController(HomeController homeController) {
        this.homeController=homeController;
    }

    public void initTable() {
        tvReturnProductRoot.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        List<History> listHistoryWhisReaderProduct = homeController.getEntityManager()
                        .createQuery("SELECT h FROM History h WHERE h.returnProduct = null AND h.user.id = :userId")
                        .setParameter("userId", jptv22fxshop.JPTV22FXShop.currentUser.getId())
                        .getResultList();
        tvReturnProductRoot.setItems(FXCollections.observableArrayList(listHistoryWhisReaderProduct));
        TableColumn<History, String> idHistoryCol = new TableColumn<>("№");
        idHistoryCol.setCellValueFactory(cellData -> cellData.getValue()
                .idProperty());
        
        TableColumn<History, String> titleHistoryProductCol = new TableColumn<>("Название продукта");
        titleHistoryProductCol.setCellValueFactory(cellData -> cellData.getValue()
                .getProduct().titleProperty());
        
        TableColumn<History, Image> coverHistoryProductCol = 
                new TableColumn<>("Фото");
        coverHistoryProductCol.setCellValueFactory(cellData -> {
            Image coverImage = new Image(cellData.getValue().getProduct()
                    .getCoverAsStream());
            return new SimpleObjectProperty<>(coverImage);
        });
        coverHistoryProductCol.setCellFactory(param -> new ImageViewTableCell<>());

        TableColumn<History, String> dateHistoryProductCol = 
                new TableColumn<>("Дата выдачи продукта");
        dateHistoryProductCol.setCellValueFactory(cellData -> cellData.getValue()
                .giveUpDateProperty());
        
        tvReturnProductRoot.getColumns().addAll(idHistoryCol,
                titleHistoryProductCol,
                coverHistoryProductCol,
                dateHistoryProductCol
        );
        tvReturnProductRoot.setRowFactory(tv ->{
            TableRow<History> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && (!row.isEmpty())){
                    History history = row.getItem();
                    System.out.println(String.format("Возврат продукта \"%s\"",
                            history.getProduct().getTitle()));
                    this.returnProduct(history);
                }
            });
            return row;
        });
    }

    private void returnProduct(History history) {
        try {
            history.setReturnProduct(new GregorianCalendar().getTime());
            homeController.getEntityManager().getTransaction().begin();
            homeController.getEntityManager().merge(history);
            homeController.getEntityManager().getTransaction().commit();
            homeController.getLbInfoHome().setText(String.format("Продукт \"%s\" возврацен", history.getProduct().getTitle()));
            homeController.getLbInfoHome().getStyleClass().add("info-home");
            this.initTable();
        } catch (Exception e) {
            homeController.getLbInfoHome().getStyleClass().add("info-home-error");
            homeController.getLbInfoHome().setText(String.format("Продукт \"%s\" возвратить не удалось!", history.getProduct().getTitle()));
            homeController.getEntityManager().getTransaction().rollback();
        }
        
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
