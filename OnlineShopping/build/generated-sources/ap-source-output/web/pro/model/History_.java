package web.pro.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import web.pro.model.Account;
import web.pro.model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-29T16:51:27")
@StaticMetamodel(History.class)
public class History_ { 

    public static volatile SingularAttribute<History, Date> date;
    public static volatile SingularAttribute<History, Account> accountid;
    public static volatile SingularAttribute<History, Integer> amount;
    public static volatile SingularAttribute<History, Product> productid;
    public static volatile SingularAttribute<History, Integer> historyid;
    public static volatile SingularAttribute<History, Integer> price;
    public static volatile SingularAttribute<History, Integer> ordernumber;

}