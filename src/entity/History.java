/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Melnikov
 */
@Entity
public class History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToOne
    private Product product;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date giveProductToBuyerDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date returnProduct;

    public History() {
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product book) {
        this.product = book;
    }

    public Date getGiveProductToBuyerDate() {
        return giveProductToBuyerDate;
    }

    public void setGiveProductToBuyerDate(Date giveProductToBuyerDate) {
        this.giveProductToBuyerDate = giveProductToBuyerDate;
    }

    public Date getReturnProduct() {
        return returnProduct;
    }

    public void setReturnProduct(Date returnProduct) {
        this.returnProduct = returnProduct;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.user);
        hash = 47 * hash + Objects.hashCode(this.product);
        hash = 47 * hash + Objects.hashCode(this.giveProductToBuyerDate);
        hash = 47 * hash + Objects.hashCode(this.returnProduct);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final History other = (History) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.giveProductToBuyerDate, other.giveProductToBuyerDate)) {
            return false;
        }
        if (!Objects.equals(this.returnProduct, other.returnProduct)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "History{"
                + "id=" + id 
                + ", user=" + user.getFirstname()
                + " " + user.getLastname()
                + ", product=" + product.getTitle()
                + ", giveProductToBuyerDate=" + giveProductToBuyerDate 
                + ", returnProduct=" + returnProduct 
                + '}';
    }

   public StringProperty giveUpDateProperty() {
        return new SimpleStringProperty(this.giveProductToBuyerDate.toString());
   }
   public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(id));
   }
}
