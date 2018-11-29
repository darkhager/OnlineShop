package web.pro.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import web.pro.model.Cart;
import web.pro.model.Favorite;
import web.pro.model.History;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-29T16:51:27")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, Integer> amount;
    public static volatile SingularAttribute<Product, Integer> productid;
    public static volatile ListAttribute<Product, Favorite> favoriteList;
    public static volatile ListAttribute<Product, History> historyList;
    public static volatile SingularAttribute<Product, Integer> price;
    public static volatile SingularAttribute<Product, String> productname;
    public static volatile SingularAttribute<Product, String> detail;
    public static volatile SingularAttribute<Product, String> brand;
    public static volatile ListAttribute<Product, Cart> cartList;
    public static volatile SingularAttribute<Product, String> producttype;

}