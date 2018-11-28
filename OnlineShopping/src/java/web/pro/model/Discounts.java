/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lara_
 */
@Entity
@Table(name = "DISCOUNTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discounts.findAll", query = "SELECT d FROM Discounts d")
    , @NamedQuery(name = "Discounts.findByDiscountid", query = "SELECT d FROM Discounts d WHERE d.discountid = :discountid")
    , @NamedQuery(name = "Discounts.findByDiscounttype", query = "SELECT d FROM Discounts d WHERE d.discounttype = :discounttype")
    , @NamedQuery(name = "Discounts.findByDiscountamount", query = "SELECT d FROM Discounts d WHERE d.discountamount = :discountamount")})
public class Discounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DISCOUNTID")
    private String discountid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISCOUNTTYPE")
    private Character discounttype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DISCOUNTAMOUNT")
    private int discountamount;

    public Discounts() {
    }

    public Discounts(String discountid) {
        this.discountid = discountid;
    }

    public Discounts(String discountid, Character discounttype, int discountamount) {
        this.discountid = discountid;
        this.discounttype = discounttype;
        this.discountamount = discountamount;
    }

    public String getDiscountid() {
        return discountid;
    }

    public void setDiscountid(String discountid) {
        this.discountid = discountid;
    }

    public Character getDiscounttype() {
        return discounttype;
    }

    public void setDiscounttype(Character discounttype) {
        this.discounttype = discounttype;
    }

    public int getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(int discountamount) {
        this.discountamount = discountamount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (discountid != null ? discountid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Discounts)) {
            return false;
        }
        Discounts other = (Discounts) object;
        if ((this.discountid == null && other.discountid != null) || (this.discountid != null && !this.discountid.equals(other.discountid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "web.pro.model.Discounts[ discountid=" + discountid + " ]";
    }
    
}
