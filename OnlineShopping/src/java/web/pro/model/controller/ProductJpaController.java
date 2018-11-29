/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.model.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import web.pro.model.Favorite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import web.pro.model.Cart;
import web.pro.model.History;
import web.pro.model.Product;
import web.pro.model.controller.exceptions.IllegalOrphanException;
import web.pro.model.controller.exceptions.NonexistentEntityException;
import web.pro.model.controller.exceptions.PreexistingEntityException;
import web.pro.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author lara_
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (product.getFavoriteList() == null) {
            product.setFavoriteList(new ArrayList<Favorite>());
        }
        if (product.getCartList() == null) {
            product.setCartList(new ArrayList<Cart>());
        }
        if (product.getHistoryList() == null) {
            product.setHistoryList(new ArrayList<History>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Favorite> attachedFavoriteList = new ArrayList<Favorite>();
            for (Favorite favoriteListFavoriteToAttach : product.getFavoriteList()) {
                favoriteListFavoriteToAttach = em.getReference(favoriteListFavoriteToAttach.getClass(), favoriteListFavoriteToAttach.getFavoriteid());
                attachedFavoriteList.add(favoriteListFavoriteToAttach);
            }
            product.setFavoriteList(attachedFavoriteList);
            List<Cart> attachedCartList = new ArrayList<Cart>();
            for (Cart cartListCartToAttach : product.getCartList()) {
                cartListCartToAttach = em.getReference(cartListCartToAttach.getClass(), cartListCartToAttach.getCartid());
                attachedCartList.add(cartListCartToAttach);
            }
            product.setCartList(attachedCartList);
            List<History> attachedHistoryList = new ArrayList<History>();
            for (History historyListHistoryToAttach : product.getHistoryList()) {
                historyListHistoryToAttach = em.getReference(historyListHistoryToAttach.getClass(), historyListHistoryToAttach.getHistoryid());
                attachedHistoryList.add(historyListHistoryToAttach);
            }
            product.setHistoryList(attachedHistoryList);
            em.persist(product);
            for (Favorite favoriteListFavorite : product.getFavoriteList()) {
                Product oldProductidOfFavoriteListFavorite = favoriteListFavorite.getProductid();
                favoriteListFavorite.setProductid(product);
                favoriteListFavorite = em.merge(favoriteListFavorite);
                if (oldProductidOfFavoriteListFavorite != null) {
                    oldProductidOfFavoriteListFavorite.getFavoriteList().remove(favoriteListFavorite);
                    oldProductidOfFavoriteListFavorite = em.merge(oldProductidOfFavoriteListFavorite);
                }
            }
            for (Cart cartListCart : product.getCartList()) {
                Product oldProductidOfCartListCart = cartListCart.getProductid();
                cartListCart.setProductid(product);
                cartListCart = em.merge(cartListCart);
                if (oldProductidOfCartListCart != null) {
                    oldProductidOfCartListCart.getCartList().remove(cartListCart);
                    oldProductidOfCartListCart = em.merge(oldProductidOfCartListCart);
                }
            }
            for (History historyListHistory : product.getHistoryList()) {
                Product oldProductidOfHistoryListHistory = historyListHistory.getProductid();
                historyListHistory.setProductid(product);
                historyListHistory = em.merge(historyListHistory);
                if (oldProductidOfHistoryListHistory != null) {
                    oldProductidOfHistoryListHistory.getHistoryList().remove(historyListHistory);
                    oldProductidOfHistoryListHistory = em.merge(oldProductidOfHistoryListHistory);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProduct(product.getProductid()) != null) {
                throw new PreexistingEntityException("Product " + product + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product persistentProduct = em.find(Product.class, product.getProductid());
            List<Favorite> favoriteListOld = persistentProduct.getFavoriteList();
            List<Favorite> favoriteListNew = product.getFavoriteList();
            List<Cart> cartListOld = persistentProduct.getCartList();
            List<Cart> cartListNew = product.getCartList();
            List<History> historyListOld = persistentProduct.getHistoryList();
            List<History> historyListNew = product.getHistoryList();
            List<String> illegalOrphanMessages = null;
            for (Favorite favoriteListOldFavorite : favoriteListOld) {
                if (!favoriteListNew.contains(favoriteListOldFavorite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favorite " + favoriteListOldFavorite + " since its productid field is not nullable.");
                }
            }
            for (Cart cartListOldCart : cartListOld) {
                if (!cartListNew.contains(cartListOldCart)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cart " + cartListOldCart + " since its productid field is not nullable.");
                }
            }
            for (History historyListOldHistory : historyListOld) {
                if (!historyListNew.contains(historyListOldHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain History " + historyListOldHistory + " since its productid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Favorite> attachedFavoriteListNew = new ArrayList<Favorite>();
            for (Favorite favoriteListNewFavoriteToAttach : favoriteListNew) {
                favoriteListNewFavoriteToAttach = em.getReference(favoriteListNewFavoriteToAttach.getClass(), favoriteListNewFavoriteToAttach.getFavoriteid());
                attachedFavoriteListNew.add(favoriteListNewFavoriteToAttach);
            }
            favoriteListNew = attachedFavoriteListNew;
            product.setFavoriteList(favoriteListNew);
            List<Cart> attachedCartListNew = new ArrayList<Cart>();
            for (Cart cartListNewCartToAttach : cartListNew) {
                cartListNewCartToAttach = em.getReference(cartListNewCartToAttach.getClass(), cartListNewCartToAttach.getCartid());
                attachedCartListNew.add(cartListNewCartToAttach);
            }
            cartListNew = attachedCartListNew;
            product.setCartList(cartListNew);
            List<History> attachedHistoryListNew = new ArrayList<History>();
            for (History historyListNewHistoryToAttach : historyListNew) {
                historyListNewHistoryToAttach = em.getReference(historyListNewHistoryToAttach.getClass(), historyListNewHistoryToAttach.getHistoryid());
                attachedHistoryListNew.add(historyListNewHistoryToAttach);
            }
            historyListNew = attachedHistoryListNew;
            product.setHistoryList(historyListNew);
            product = em.merge(product);
            for (Favorite favoriteListNewFavorite : favoriteListNew) {
                if (!favoriteListOld.contains(favoriteListNewFavorite)) {
                    Product oldProductidOfFavoriteListNewFavorite = favoriteListNewFavorite.getProductid();
                    favoriteListNewFavorite.setProductid(product);
                    favoriteListNewFavorite = em.merge(favoriteListNewFavorite);
                    if (oldProductidOfFavoriteListNewFavorite != null && !oldProductidOfFavoriteListNewFavorite.equals(product)) {
                        oldProductidOfFavoriteListNewFavorite.getFavoriteList().remove(favoriteListNewFavorite);
                        oldProductidOfFavoriteListNewFavorite = em.merge(oldProductidOfFavoriteListNewFavorite);
                    }
                }
            }
            for (Cart cartListNewCart : cartListNew) {
                if (!cartListOld.contains(cartListNewCart)) {
                    Product oldProductidOfCartListNewCart = cartListNewCart.getProductid();
                    cartListNewCart.setProductid(product);
                    cartListNewCart = em.merge(cartListNewCart);
                    if (oldProductidOfCartListNewCart != null && !oldProductidOfCartListNewCart.equals(product)) {
                        oldProductidOfCartListNewCart.getCartList().remove(cartListNewCart);
                        oldProductidOfCartListNewCart = em.merge(oldProductidOfCartListNewCart);
                    }
                }
            }
            for (History historyListNewHistory : historyListNew) {
                if (!historyListOld.contains(historyListNewHistory)) {
                    Product oldProductidOfHistoryListNewHistory = historyListNewHistory.getProductid();
                    historyListNewHistory.setProductid(product);
                    historyListNewHistory = em.merge(historyListNewHistory);
                    if (oldProductidOfHistoryListNewHistory != null && !oldProductidOfHistoryListNewHistory.equals(product)) {
                        oldProductidOfHistoryListNewHistory.getHistoryList().remove(historyListNewHistory);
                        oldProductidOfHistoryListNewHistory = em.merge(oldProductidOfHistoryListNewHistory);
                    }
                }
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
                Integer id = product.getProductid();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Favorite> favoriteListOrphanCheck = product.getFavoriteList();
            for (Favorite favoriteListOrphanCheckFavorite : favoriteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Favorite " + favoriteListOrphanCheckFavorite + " in its favoriteList field has a non-nullable productid field.");
            }
            List<Cart> cartListOrphanCheck = product.getCartList();
            for (Cart cartListOrphanCheckCart : cartListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Cart " + cartListOrphanCheckCart + " in its cartList field has a non-nullable productid field.");
            }
            List<History> historyListOrphanCheck = product.getHistoryList();
            for (History historyListOrphanCheckHistory : historyListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the History " + historyListOrphanCheckHistory + " in its historyList field has a non-nullable productid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(product);
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

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Product> findProductBySearch(String search) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Product.search");
        query.setParameter("search", "%" + search + "%");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Product> findProductPrice1() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Product.price1");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Product> findProductPrice2() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Product.price2");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Product> findProductPrice3() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Product.price3");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Product> findProductPrice4() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Product.price4");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Product> findProductPrice5() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Product.price5");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Product> findProductPrice6() {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Product.price6");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
