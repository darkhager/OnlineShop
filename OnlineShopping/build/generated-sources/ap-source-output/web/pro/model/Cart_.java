package web.pro.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import web.pro.model.Account;
import web.pro.model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2561-11-13T21:52:50")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile SingularAttribute<Cart, Account> accountid;
    public static volatile SingularAttribute<Cart, Integer> amount;
    public static volatile SingularAttribute<Cart, Product> productid;
    public static volatile SingularAttribute<Cart, Integer> cartid;

}