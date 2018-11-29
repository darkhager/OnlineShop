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
import web.pro.model.Accountactivate;
import web.pro.model.controller.exceptions.NonexistentEntityException;
import web.pro.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author 60130
 */
public class AccountactivateJpaController implements Serializable {

    public AccountactivateJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Accountactivate accountactivate) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountid = accountactivate.getAccountid();
            if (accountid != null) {
                accountid = em.getReference(accountid.getClass(), accountid.getAccountid());
                accountactivate.setAccountid(accountid);
            }
            em.persist(accountactivate);
            if (accountid != null) {
                accountid.getAccountactivateList().add(accountactivate);
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

    public void edit(Accountactivate accountactivate) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Accountactivate persistentAccountactivate = em.find(Accountactivate.class, accountactivate.getActivateid());
            Account accountidOld = persistentAccountactivate.getAccountid();
            Account accountidNew = accountactivate.getAccountid();
            if (accountidNew != null) {
                accountidNew = em.getReference(accountidNew.getClass(), accountidNew.getAccountid());
                accountactivate.setAccountid(accountidNew);
            }
            accountactivate = em.merge(accountactivate);
            if (accountidOld != null && !accountidOld.equals(accountidNew)) {
                accountidOld.getAccountactivateList().remove(accountactivate);
                accountidOld = em.merge(accountidOld);
            }
            if (accountidNew != null && !accountidNew.equals(accountidOld)) {
                accountidNew.getAccountactivateList().add(accountactivate);
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
                Integer id = accountactivate.getActivateid();
                if (findAccountactivate(id) == null) {
                    throw new NonexistentEntityException("The accountactivate with id " + id + " no longer exists.");
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
            Accountactivate accountactivate;
            try {
                accountactivate = em.getReference(Accountactivate.class, id);
                accountactivate.getActivateid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accountactivate with id " + id + " no longer exists.", enfe);
            }
            Account accountid = accountactivate.getAccountid();
            if (accountid != null) {
                accountid.getAccountactivateList().remove(accountactivate);
                accountid = em.merge(accountid);
            }
            em.remove(accountactivate);
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

    public List<Accountactivate> findAccountactivateEntities() {
        return findAccountactivateEntities(true, -1, -1);
    }

    public List<Accountactivate> findAccountactivateEntities(int maxResults, int firstResult) {
        return findAccountactivateEntities(false, maxResults, firstResult);
    }

    private List<Accountactivate> findAccountactivateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accountactivate.class));
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

    public Accountactivate findAccountactivate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accountactivate.class, id);
        } finally {
            em.close();
        }
    }
    
     public Accountactivate findAccountActivateByAccountId(Account account) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Accountactivate.findByAccountid");
        query.setParameter("accountid", account.getAccountid());
        try {
            return (Accountactivate) query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public int getAccountactivateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accountactivate> rt = cq.from(Accountactivate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
