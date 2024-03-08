/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products.newproduct;

import entity.Product;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import jptv22fxshop.JPTV22FXShop;
//import org.imgscalr.Scalr;

/**
 * FXML Controller class
 *
 * @author Melnikov
 */
public class NewproductController implements Initializable {
    private EntityManager em;
    private JPTV22FXShop app;
    private File selectedFile;
    @FXML
    private TextField tfTitleProduct;
    @FXML
    private Button btSelectCover;
    @FXML
    private Button btAddNewProduct;
    @FXML
    private Label lbInfo;
    
    
    public NewproductController() {
       
    }
    @FXML
    public void selectCover(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор фото для продукта");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        btSelectCover.setText("Выбран файл "+selectedFile.getName());
        btSelectCover.disableProperty().set(true);
    }
    @FXML
    public void addNewProduct(){
        if(tfTitleProduct.getText().isEmpty()){
            lbInfo.setText("Продукт добавить не удалось. Все поля должны быть заполнены");
            return;
        }
        Product product = new Product();
        product.setTitle(tfTitleProduct.getText());
        try{
         //Добавляем в Library проекта библиотеку imgscalr-lib.jar (находим в Интернете)
            // Получаем нужный формат изображения из selectedFile
            // Преобразуем размер изображения к ширине в 400 px 
            // Преобразуем тип в byte[] и инициируем product.setCover(...);
            BufferedImage biProductCover = ImageIO.read(selectedFile);
            //BufferedImage biScaledBookCover = Scalr.resize(biProductCover, Scalr.Mode.FIT_TO_WIDTH,400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            //ImageIO.write (biScaledBookCover, "jpg", baos);
            product.setCover(baos.toByteArray());
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            lbInfo.setText("Продукт успешно добавлен");
        } catch (IOException ex) {
            lbInfo.setText("Продукт добавить не удалось");
            Logger.getLogger(NewproductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btSelectCover.disableProperty().set(false);
        selectedFile = null;
        tfTitleProduct.setText("");
        btSelectCover.setText("Выбрать обложку для продукта");
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
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
    }
    public static BufferedImage convertToBufferedImage(File file) {
        try {
            // Чтение изображения из файла с использованием ImageIO
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
