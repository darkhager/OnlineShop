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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 60130
 */
@Entity
@Table(name = "ACCOUNTACTIVATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accountactivate.findAll", query = "SELECT a FROM Accountactivate a")
    , @NamedQuery(name = "Accountactivate.findByActivateid", query = "SELECT a FROM Accountactivate a WHERE a.activateid = :activateid")
    , @NamedQuery(name = "Accountactivate.findByActivatecode", query = "SELECT a FROM Accountactivate a WHERE a.activatecode = :activatecode")})
public class Accountactivate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ACTIVATEID")
    private Integer activateid;
    @Size(max = 10)
    @Column(name = "ACTIVATECODE")
    private String activatecode;
    @JoinColumn(name = "ACCOUNTID", referencedColumnName = "ACCOUNTID")
    @ManyToOne
    private Account accountid;

    public Accountactivate() {
    }

    public Accountactivate(Integer activateid) {
        this.activateid = activateid;
    }

    public Integer getActivateid() {
        return activateid;
    }

    public void setActivateid(Integer activateid) {
        this.activateid = activateid;
    }

    public String getActivatecode() {
        return activatecode;
    }

    public void setActivatecode(String activatecode) {
        this.activatecode = activatecode;
    }

    public Account getAccountid() {
        return accountid;
    }

    public void setAccountid(Account accountid) {
        this.accountid = accountid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (activateid != null ? activateid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accountactivate)) {
            return false;
        }
        Accountactivate other = (Accountactivate) object;
        if ((this.activateid == null && other.activateid != null) || (this.activateid != null && !this.activateid.equals(other.activateid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "web.pro.model.Accountactivate[ activateid=" + activateid + " ]";
    }
    
}
