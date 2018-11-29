package web.pro.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import web.pro.model.Account;
import web.pro.model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-29T16:51:27")
@StaticMetamodel(Favorite.class)
public class Favorite_ { 

    public static volatile SingularAttribute<Favorite, Account> accountid;
    public static volatile SingularAttribute<Favorite, Integer> favoriteid;
    public static volatile SingularAttribute<Favorite, Product> productid;

}