/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.model.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import web.pro.model.Account;
import web.pro.model.Passwordreset;
import web.pro.model.controller.exceptions.NonexistentEntityException;
import web.pro.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author lara_
 */
public class PasswordresetJpaController implements Serializable {

    public PasswordresetJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Passwordreset passwordreset) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountid = passwordreset.getAccountid();
            if (accountid != null) {
                accountid = em.getReference(accountid.getClass(), accountid.getAccountid());
                passwordreset.setAccountid(accountid);
            }
            em.persist(passwordreset);
            if (accountid != null) {
                accountid.getPasswordresetList().add(passwordreset);
                accountid = em.merge(accountid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Passwordreset passwordreset) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Passwordreset persistentPasswordreset = em.find(Passwordreset.class, passwordreset.getResetid());
            Account accountidOld = persistentPasswordreset.getAccountid();
            Account accountidNew = passwordreset.getAccountid();
            if (accountidNew != null) {
                accountidNew = em.getReference(accountidNew.getClass(), accountidNew.getAccountid());
                passwordreset.setAccountid(accountidNew);
            }
            passwordreset = em.merge(passwordreset);
            if (accountidOld != null && !accountidOld.equals(accountidNew)) {
                accountidOld.getPasswordresetList().remove(passwordreset);
                accountidOld = em.merge(accountidOld);
            }
            if (accountidNew != null && !accountidNew.equals(accountidOld)) {
                accountidNew.getPasswordresetList().add(passwordreset);
                accountidNew = em.merge(accountidNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = passwordreset.getResetid();
                if (findPasswordreset(id) == null) {
                    throw new NonexistentEntityException("The passwordreset with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Passwordreset passwordreset;
            try {
                passwordreset = em.getReference(Passwordreset.class, id);
                passwordreset.getResetid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The passwordreset with id " + id + " no longer exists.", enfe);
            }
            Account accountid = passwordreset.getAccountid();
            if (accountid != null) {
                accountid.getPasswordresetList().remove(passwordreset);
                accountid = em.merge(accountid);
            }
            em.remove(passwordreset);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Passwordreset> findPasswordresetEntities() {
        return findPasswordresetEntities(true, -1, -1);
    }

    public List<Passwordreset> findPasswordresetEntities(int maxResults, int firstResult) {
        return findPasswordresetEntities(false, maxResults, firstResult);
    }

    private List<Passwordreset> findPasswordresetEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Passwordreset.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Passwordreset findPasswordreset(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Passwordreset.class, id);
        } finally {
            em.close();
        }
    }

    public int getPasswordresetCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Passwordreset> rt = cq.from(Passwordreset.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
