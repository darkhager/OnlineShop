package web.pro.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import web.pro.model.Account;
import web.pro.model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2561-11-13T21:52:50")
@StaticMetamodel(Review.class)
public class Review_ { 

    public static volatile SingularAttribute<Review, Integer> score;
    public static volatile SingularAttribute<Review, Account> accountid;
    public static volatile SingularAttribute<Review, Product> productid;
    public static volatile SingularAttribute<Review, String> comment;
    public static volatile SingularAttribute<Review, Integer> reviewid;

}