package web.pro.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import web.pro.model.Account;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2561-11-28T23:11:53")
@StaticMetamodel(Passwordreset.class)
public class Passwordreset_ { 

    public static volatile SingularAttribute<Passwordreset, Date> date;
    public static volatile SingularAttribute<Passwordreset, Account> accountid;
    public static volatile SingularAttribute<Passwordreset, String> resetcode;
    public static volatile SingularAttribute<Passwordreset, Integer> resetid;

}