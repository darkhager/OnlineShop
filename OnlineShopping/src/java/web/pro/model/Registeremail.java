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
@Table(name = "REGISTEREMAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registeremail.findAll", query = "SELECT r FROM Registeremail r")
    , @NamedQuery(name = "Registeremail.findByRegisterid", query = "SELECT r FROM Registeremail r WHERE r.registerid = :registerid")
    , @NamedQuery(name = "Registeremail.findByRegistercode", query = "SELECT r FROM Registeremail r WHERE r.registercode = :registercode")
    , @NamedQuery(name = "Registeremail.findByDate", query = "SELECT r FROM Registeremail r WHERE r.date = :date")})
public class Registeremail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REGISTERID")
    private Integer registerid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "REGISTERCODE")
    private String registercode;
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "ACCOUNTID", referencedColumnName = "ACCOUNTID")
    @ManyToOne
    private Account accountid;

    public Registeremail() {
    }

    public Registeremail(Integer registerid) {
        this.registerid = registerid;
    }

    public Registeremail(Integer registerid, String registercode) {
        this.registerid = registerid;
        this.registercode = registercode;
    }

    public Integer getRegisterid() {
        return registerid;
    }

    public void setRegisterid(Integer registerid) {
        this.registerid = registerid;
    }

    public String getRegistercode() {
        return registercode;
    }

    public void setRegistercode(String registercode) {
        this.registercode = registercode;
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
        hash += (registerid != null ? registerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registeremail)) {
            return false;
        }
        Registeremail other = (Registeremail) object;
        if ((this.registerid == null && other.registerid != null) || (this.registerid != null && !this.registerid.equals(other.registerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "web.pro.model.Registeremail[ registerid=" + registerid + " ]";
    }
    
}
