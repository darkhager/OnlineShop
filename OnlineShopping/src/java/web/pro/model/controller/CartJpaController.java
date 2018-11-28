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
import web.pro.model.Cart;
import web.pro.model.Product;
import web.pro.model.controller.exceptions.NonexistentEntityException;
import web.pro.model.controller.exceptions.PreexistingEntityException;
import web.pro.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author lara_
 */
public class CartJpaController implements Serializable {

    public CartJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cart cart) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountid = cart.getAccountid();
            if (accountid != null) {
                accountid = em.getReference(accountid.getClass(), accountid.getAccountid());
                cart.setAccountid(accountid);
            }
            Product productid = cart.getProductid();
            if (productid != null) {
                productid = em.getReference(productid.getClass(), productid.getProductid());
                cart.setProductid(productid);
            }
            em.persist(cart);
            if (accountid != null) {
                accountid.getCartList().add(cart);
                accountid = em.merge(accountid);
            }
            if (productid != null) {
                productid.getCartList().add(cart);
                productid = em.merge(productid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCart(cart.getCartid()) != null) {
                throw new PreexistingEntityException("Cart " + cart + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cart cart) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cart persistentCart = em.find(Cart.class, cart.getCartid());
            Account accountidOld = persistentCart.getAccountid();
            Account accountidNew = cart.getAccountid();
            Product productidOld = persistentCart.getProductid();
            Product productidNew = cart.getProductid();
            if (accountidNew != null) {
                accountidNew = em.getReference(accountidNew.getClass(), accountidNew.getAccountid());
                cart.setAccountid(accountidNew);
            }
            if (productidNew != null) {
                productidNew = em.getReference(productidNew.getClass(), productidNew.getProductid());
                cart.setProductid(productidNew);
            }
            cart = em.merge(cart);
            if (accountidOld != null && !accountidOld.equals(accountidNew)) {
                accountidOld.getCartList().remove(cart);
                accountidOld = em.merge(accountidOld);
            }
            if (accountidNew != null && !accountidNew.equals(accountidOld)) {
                accountidNew.getCartList().add(cart);
                accountidNew = em.merge(accountidNew);
            }
            if (productidOld != null && !productidOld.equals(productidNew)) {
                productidOld.getCartList().remove(cart);
                productidOld = em.merge(productidOld);
            }
            if (productidNew != null && !productidNew.equals(productidOld)) {
                productidNew.getCartList().add(cart);
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
                Integer id = cart.getCartid();
                if (findCart(id) == null) {
                    throw new NonexistentEntityException("The cart with id " + id + " no longer exists.");
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
            Cart cart;
            try {
                cart = em.getReference(Cart.class, id);
                cart.getCartid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cart with id " + id + " no longer exists.", enfe);
            }
            Account accountid = cart.getAccountid();
            if (accountid != null) {
                accountid.getCartList().remove(cart);
                accountid = em.merge(accountid);
            }
            Product productid = cart.getProductid();
            if (productid != null) {
                productid.getCartList().remove(cart);
                productid = em.merge(productid);
            }
            em.remove(cart);
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

    public List<Cart> findCartEntities() {
        return findCartEntities(true, -1, -1);
    }

    public List<Cart> findCartEntities(int maxResults, int firstResult) {
        return findCartEntities(false, maxResults, firstResult);
    }

    private List<Cart> findCartEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cart.class));
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

    public Cart findCart(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cart.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cart> rt = cq.from(Cart.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
