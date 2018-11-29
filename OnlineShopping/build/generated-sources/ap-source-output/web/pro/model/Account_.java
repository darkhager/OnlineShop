package web.pro.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import web.pro.model.Accountactivate;
import web.pro.model.Cart;
import web.pro.model.Favorite;
import web.pro.model.History;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-29T16:51:27")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, String> firstname;
    public static volatile SingularAttribute<Account, String> address;
    public static volatile ListAttribute<Account, History> historyList;
    public static volatile SingularAttribute<Account, Integer> postcode;
    public static volatile SingularAttribute<Account, String> phonenumber;
    public static volatile SingularAttribute<Account, String> lastname;
    public static volatile SingularAttribute<Account, Date> activatedate;
    public static volatile SingularAttribute<Account, Integer> accountid;
    public static volatile SingularAttribute<Account, String> password;
    public static volatile ListAttribute<Account, Favorite> favoriteList;
    public static volatile ListAttribute<Account, Accountactivate> accountactivateList;
    public static volatile SingularAttribute<Account, String> email;
    public static volatile ListAttribute<Account, Cart> cartList;
    public static volatile SingularAttribute<Account, String> username;

}