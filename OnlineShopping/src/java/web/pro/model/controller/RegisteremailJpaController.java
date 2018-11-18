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
import web.pro.model.Registeremail;
import web.pro.model.controller.exceptions.NonexistentEntityException;
import web.pro.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author lara_
 */
public class RegisteremailJpaController implements Serializable {

    public RegisteremailJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Registeremail registeremail) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountid = registeremail.getAccountid();
            if (accountid != null) {
                accountid = em.getReference(accountid.getClass(), accountid.getAccountid());
                registeremail.setAccountid(accountid);
            }
            em.persist(registeremail);
            if (accountid != null) {
                accountid.getRegisteremailList().add(registeremail);
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

    public void edit(Registeremail registeremail) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Registeremail persistentRegisteremail = em.find(Registeremail.class, registeremail.getRegisterid());
            Account accountidOld = persistentRegisteremail.getAccountid();
            Account accountidNew = registeremail.getAccountid();
            if (accountidNew != null) {
                accountidNew = em.getReference(accountidNew.getClass(), accountidNew.getAccountid());
                registeremail.setAccountid(accountidNew);
            }
            registeremail = em.merge(registeremail);
            if (accountidOld != null && !accountidOld.equals(accountidNew)) {
                accountidOld.getRegisteremailList().remove(registeremail);
                accountidOld = em.merge(accountidOld);
            }
            if (accountidNew != null && !accountidNew.equals(accountidOld)) {
                accountidNew.getRegisteremailList().add(registeremail);
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
                Integer id = registeremail.getRegisterid();
                if (findRegisteremail(id) == null) {
                    throw new NonexistentEntityException("The registeremail with id " + id + " no longer exists.");
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
            Registeremail registeremail;
            try {
                registeremail = em.getReference(Registeremail.class, id);
                registeremail.getRegisterid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registeremail with id " + id + " no longer exists.", enfe);
            }
            Account accountid = registeremail.getAccountid();
            if (accountid != null) {
                accountid.getRegisteremailList().remove(registeremail);
                accountid = em.merge(accountid);
            }
            em.remove(registeremail);
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

    public List<Registeremail> findRegisteremailEntities() {
        return findRegisteremailEntities(true, -1, -1);
    }

    public List<Registeremail> findRegisteremailEntities(int maxResults, int firstResult) {
        return findRegisteremailEntities(false, maxResults, firstResult);
    }

    private List<Registeremail> findRegisteremailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Registeremail.class));
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

    public Registeremail findRegisteremail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Registeremail.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegisteremailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Registeremail> rt = cq.from(Registeremail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
