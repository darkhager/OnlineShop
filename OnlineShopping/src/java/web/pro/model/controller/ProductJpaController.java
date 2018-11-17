/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.model.controller;

import java.beans.Statement;
import java.io.Serializable;
import java.sql.ResultSet;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import web.pro.model.Review;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import web.pro.model.Favorite;
import web.pro.model.Cart;
import web.pro.model.History;
import web.pro.model.Product;
import web.pro.model.controller.exceptions.NonexistentEntityException;
import web.pro.model.controller.exceptions.PreexistingEntityException;
import web.pro.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author 60130
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
        if (product.getReviewList() == null) {
            product.setReviewList(new ArrayList<Review>());
        }
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
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : product.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewid());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            product.setReviewList(attachedReviewList);
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
            for (Review reviewListReview : product.getReviewList()) {
                Product oldProductidOfReviewListReview = reviewListReview.getProductid();
                reviewListReview.setProductid(product);
                reviewListReview = em.merge(reviewListReview);
                if (oldProductidOfReviewListReview != null) {
                    oldProductidOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldProductidOfReviewListReview = em.merge(oldProductidOfReviewListReview);
                }
            }
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

    public void edit(Product product) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product persistentProduct = em.find(Product.class, product.getProductid());
            List<Review> reviewListOld = persistentProduct.getReviewList();
            List<Review> reviewListNew = product.getReviewList();
            List<Favorite> favoriteListOld = persistentProduct.getFavoriteList();
            List<Favorite> favoriteListNew = product.getFavoriteList();
            List<Cart> cartListOld = persistentProduct.getCartList();
            List<Cart> cartListNew = product.getCartList();
            List<History> historyListOld = persistentProduct.getHistoryList();
            List<History> historyListNew = product.getHistoryList();
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getReviewid());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            product.setReviewList(reviewListNew);
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
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    reviewListOldReview.setProductid(null);
                    reviewListOldReview = em.merge(reviewListOldReview);
                }
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Product oldProductidOfReviewListNewReview = reviewListNewReview.getProductid();
                    reviewListNewReview.setProductid(product);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldProductidOfReviewListNewReview != null && !oldProductidOfReviewListNewReview.equals(product)) {
                        oldProductidOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldProductidOfReviewListNewReview = em.merge(oldProductidOfReviewListNewReview);
                    }
                }
            }
            for (Favorite favoriteListOldFavorite : favoriteListOld) {
                if (!favoriteListNew.contains(favoriteListOldFavorite)) {
                    favoriteListOldFavorite.setProductid(null);
                    favoriteListOldFavorite = em.merge(favoriteListOldFavorite);
                }
            }
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
            for (Cart cartListOldCart : cartListOld) {
                if (!cartListNew.contains(cartListOldCart)) {
                    cartListOldCart.setProductid(null);
                    cartListOldCart = em.merge(cartListOldCart);
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
            for (History historyListOldHistory : historyListOld) {
                if (!historyListNew.contains(historyListOldHistory)) {
                    historyListOldHistory.setProductid(null);
                    historyListOldHistory = em.merge(historyListOldHistory);
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

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
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
            List<Review> reviewList = product.getReviewList();
            for (Review reviewListReview : reviewList) {
                reviewListReview.setProductid(null);
                reviewListReview = em.merge(reviewListReview);
            }
            List<Favorite> favoriteList = product.getFavoriteList();
            for (Favorite favoriteListFavorite : favoriteList) {
                favoriteListFavorite.setProductid(null);
                favoriteListFavorite = em.merge(favoriteListFavorite);
            }
            List<Cart> cartList = product.getCartList();
            for (Cart cartListCart : cartList) {
                cartListCart.setProductid(null);
                cartListCart = em.merge(cartListCart);
            }
            List<History> historyList = product.getHistoryList();
            for (History historyListHistory : historyList) {
                historyListHistory.setProductid(null);
                historyListHistory = em.merge(historyListHistory);
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

    public List<Product> findProductSearch(String search) {
        EntityManager em = getEntityManager();
        String sql = "SELECT * FROM  customer WHERE Name LIKE '%" + search + "%' ";
        Statement s = connect.createStatement();
        ResultSet rec = s.executeQuery(sql);
        while ((rec != null) && (rec.next())) {

            System.out.print(rec.getString("CustomerID"));

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
