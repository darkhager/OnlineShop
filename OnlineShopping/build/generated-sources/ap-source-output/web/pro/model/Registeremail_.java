package web.pro.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import web.pro.model.Account;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2561-11-28T23:11:53")
@StaticMetamodel(Registeremail.class)
public class Registeremail_ { 

    public static volatile SingularAttribute<Registeremail, Date> date;
    public static volatile SingularAttribute<Registeremail, Account> accountid;
    public static volatile SingularAttribute<Registeremail, Integer> registerid;
    public static volatile SingularAttribute<Registeremail, String> registercode;

}