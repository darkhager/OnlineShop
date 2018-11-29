/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 60130
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProductid", query = "SELECT p FROM Product p WHERE p.productid = :productid")
    , @NamedQuery(name = "Product.findByProductname", query = "SELECT p FROM Product p WHERE p.productname = :productname")
    , @NamedQuery(name = "Product.findByProducttype", query = "SELECT p FROM Product p WHERE p.producttype = :producttype")
    , @NamedQuery(name = "Product.findByBrand", query = "SELECT p FROM Product p WHERE p.brand = :brand")
    , @NamedQuery(name = "Product.findByDetail", query = "SELECT p FROM Product p WHERE p.detail = :detail")
    , @NamedQuery(name = "Product.findByPrice", query = "SELECT p FROM Product p WHERE p.price = :price")
    , @NamedQuery(name = "Product.findByAmount", query = "SELECT p FROM Product p WHERE p.amount = :amount")
    , @NamedQuery(name = "Product.price1", query = "SELECT p FROM Product p WHERE p.price <= 1000")
    , @NamedQuery(name = "Product.price2", query = "SELECT p FROM Product p WHERE p.price > 1000 and p.price <= 2000")
    , @NamedQuery(name = "Product.price3", query = "SELECT p FROM Product p WHERE p.price > 2000 and p.price <= 5000")
    , @NamedQuery(name = "Product.price4", query = "SELECT p FROM Product p WHERE p.price > 5000 and p.price <= 10000")
    , @NamedQuery(name = "Product.price5", query = "SELECT p FROM Product p WHERE p.price > 10000 and p.price <= 30000")
    , @NamedQuery(name = "Product.price6", query = "SELECT p FROM Product p WHERE p.price > 30000")
    , @NamedQuery(name = "Product.fullSize", query = "SELECT p FROM Product p WHERE lower(p.producttype) = 'full size'")
    , @NamedQuery(name = "Product.onEar", query = "SELECT p FROM Product p WHERE lower(p.producttype) = 'on ear'")
    , @NamedQuery(name = "Product.inEar", query = "SELECT p FROM Product p WHERE lower(p.producttype) = 'in ear'")
    , @NamedQuery(name = "Product.earBud", query = "SELECT p FROM Product p WHERE lower(p.producttype) = 'ear bud'")
    , @NamedQuery(name = "Product.clipEar", query = "SELECT p FROM Product p WHERE lower(p.producttype) = 'clip ear'")
    , @NamedQuery(name = "Product.ciem", query = "SELECT p FROM Product p WHERE lower(p.producttype) = 'ciem'")
    , @NamedQuery(name = "Product.search", query = "SELECT p FROM Product p WHERE lower(p.productname) like :search or lower(p.producttype) like :search or lower(p.detail) like :search or lower(p.brand) like :search")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCTID")
    private Integer productid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PRODUCTNAME")
    private String productname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PRODUCTTYPE")
    private String producttype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "BRAND")
    private String brand;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "DETAIL")
    private String detail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRICE")
    private int price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private int amount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productid")
    private List<Favorite> favoriteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productid")
    private List<Cart> cartList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productid")
    private List<History> historyList;

    public Product() {
    }

    public Product(Integer productid) {
        this.productid = productid;
    }

    public Product(Integer productid, String productname, String producttype, String brand, String detail, int price, int amount) {
        this.productid = productid;
        this.productname = productname;
        this.producttype = producttype;
        this.brand = brand;
        this.detail = detail;
        this.price = price;
        this.amount = amount;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @XmlTransient
    public List<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    @XmlTransient
    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @XmlTransient
    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productid != null ? productid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productid == null && other.productid != null) || (this.productid != null && !this.productid.equals(other.productid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "web.pro.model.Product[ productid=" + productid + " ]";
    }

}
