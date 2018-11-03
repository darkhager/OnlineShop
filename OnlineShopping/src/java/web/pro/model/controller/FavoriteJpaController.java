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
import web.pro.model.Favorite;
import web.pro.model.Product;
import web.pro.model.controller.exceptions.NonexistentEntityException;
import web.pro.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author 60130
 */
public class FavoriteJpaController implements Serializable {

    public FavoriteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Favorite favorite) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountid = favorite.getAccountid();
            if (accountid != null) {
                accountid = em.getReference(accountid.getClass(), accountid.getAccountid());
                favorite.setAccountid(accountid);
            }
            Product productid = favorite.getProductid();
            if (productid != null) {
                productid = em.getReference(productid.getClass(), productid.getProductid());
                favorite.setProductid(productid);
            }
            em.persist(favorite);
            if (accountid != null) {
                accountid.getFavoriteList().add(favorite);
                accountid = em.merge(accountid);
            }
            if (productid != null) {
                productid.getFavoriteList().add(favorite);
                productid = em.merge(productid);
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

    public void edit(Favorite favorite) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Favorite persistentFavorite = em.find(Favorite.class, favorite.getFavoriteid());
            Account accountidOld = persistentFavorite.getAccountid();
            Account accountidNew = favorite.getAccountid();
            Product productidOld = persistentFavorite.getProductid();
            Product productidNew = favorite.getProductid();
            if (accountidNew != null) {
                accountidNew = em.getReference(accountidNew.getClass(), accountidNew.getAccountid());
                favorite.setAccountid(accountidNew);
            }
            if (productidNew != null) {
                productidNew = em.getReference(productidNew.getClass(), productidNew.getProductid());
                favorite.setProductid(productidNew);
            }
            favorite = em.merge(favorite);
            if (accountidOld != null && !accountidOld.equals(accountidNew)) {
                accountidOld.getFavoriteList().remove(favorite);
                accountidOld = em.merge(accountidOld);
            }
            if (accountidNew != null && !accountidNew.equals(accountidOld)) {
                accountidNew.getFavoriteList().add(favorite);
                accountidNew = em.merge(accountidNew);
            }
            if (productidOld != null && !productidOld.equals(productidNew)) {
                productidOld.getFavoriteList().remove(favorite);
                productidOld = em.merge(productidOld);
            }
            if (productidNew != null && !productidNew.equals(productidOld)) {
                productidNew.getFavoriteList().add(favorite);
                productidNew = em.merge(productidNew);
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
                Integer id = favorite.getFavoriteid();
                if (findFavorite(id) == null) {
                    throw new NonexistentEntityException("The favorite with id " + id + " no longer exists.");
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
            Favorite favorite;
            try {
                favorite = em.getReference(Favorite.class, id);
                favorite.getFavoriteid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The favorite with id " + id + " no longer exists.", enfe);
            }
            Account accountid = favorite.getAccountid();
            if (accountid != null) {
                accountid.getFavoriteList().remove(favorite);
                accountid = em.merge(accountid);
            }
            Product productid = favorite.getProductid();
            if (productid != null) {
                productid.getFavoriteList().remove(favorite);
                productid = em.merge(productid);
            }
            em.remove(favorite);
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

    public List<Favorite> findFavoriteEntities() {
        return findFavoriteEntities(true, -1, -1);
    }

    public List<Favorite> findFavoriteEntities(int maxResults, int firstResult) {
        return findFavoriteEntities(false, maxResults, firstResult);
    }

    private List<Favorite> findFavoriteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Favorite.class));
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

    public Favorite findFavorite(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Favorite.class, id);
        } finally {
            em.close();
        }
    }

    public int getFavoriteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Favorite> rt = cq.from(Favorite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
