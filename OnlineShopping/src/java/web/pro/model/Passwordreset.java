/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 60130
 */
@Entity
@Table(name = "PASSWORDRESET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Passwordreset.findAll", query = "SELECT p FROM Passwordreset p")
    , @NamedQuery(name = "Passwordreset.findByResetid", query = "SELECT p FROM Passwordreset p WHERE p.resetid = :resetid")
    , @NamedQuery(name = "Passwordreset.findByResetcode", query = "SELECT p FROM Passwordreset p WHERE p.resetcode = :resetcode")
    , @NamedQuery(name = "Passwordreset.findByDate", query = "SELECT p FROM Passwordreset p WHERE p.date = :date")})
public class Passwordreset implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RESETID")
    private Integer resetid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RESETCODE")
    private String resetcode;
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "ACCOUNTID", referencedColumnName = "ACCOUNTID")
    @ManyToOne
    private Account accountid;

    public Passwordreset() {
    }

    public Passwordreset(Integer resetid) {
        this.resetid = resetid;
    }

    public Passwordreset(Integer resetid, String resetcode) {
        this.resetid = resetid;
        this.resetcode = resetcode;
    }

    public Integer getResetid() {
        return resetid;
    }

    public void setResetid(Integer resetid) {
        this.resetid = resetid;
    }

    public String getResetcode() {
        return resetcode;
    }

    public void setResetcode(String resetcode) {
        this.resetcode = resetcode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        hash += (resetid != null ? resetid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Passwordreset)) {
            return false;
        }
        Passwordreset other = (Passwordreset) object;
        if ((this.resetid == null && other.resetid != null) || (this.resetid != null && !this.resetid.equals(other.resetid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "web.pro.model.Passwordreset[ resetid=" + resetid + " ]";
    }
    
}
