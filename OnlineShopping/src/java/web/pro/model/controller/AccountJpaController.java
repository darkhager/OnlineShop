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
import web.pro.model.Passwordreset;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import web.pro.model.Account;
import web.pro.model.Review;
import web.pro.model.Favorite;
import web.pro.model.Cart;
import web.pro.model.History;
import web.pro.model.Registeremail;
import web.pro.model.controller.exceptions.NonexistentEntityException;
import web.pro.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author 60130
 */
public class AccountJpaController implements Serializable {

    public AccountJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Account account) throws RollbackFailureException, Exception {
        if (account.getPasswordresetList() == null) {
            account.setPasswordresetList(new ArrayList<Passwordreset>());
        }
        if (account.getReviewList() == null) {
            account.setReviewList(new ArrayList<Review>());
        }
        if (account.getFavoriteList() == null) {
            account.setFavoriteList(new ArrayList<Favorite>());
        }
        if (account.getCartList() == null) {
            account.setCartList(new ArrayList<Cart>());
        }
        if (account.getHistoryList() == null) {
            account.setHistoryList(new ArrayList<History>());
        }
        if (account.getRegisteremailList() == null) {
            account.setRegisteremailList(new ArrayList<Registeremail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Passwordreset> attachedPasswordresetList = new ArrayList<Passwordreset>();
            for (Passwordreset passwordresetListPasswordresetToAttach : account.getPasswordresetList()) {
                passwordresetListPasswordresetToAttach = em.getReference(passwordresetListPasswordresetToAttach.getClass(), passwordresetListPasswordresetToAttach.getResetid());
                attachedPasswordresetList.add(passwordresetListPasswordresetToAttach);
            }
            account.setPasswordresetList(attachedPasswordresetList);
            List<Review> attachedReviewList = new ArrayList<Review>();
            for (Review reviewListReviewToAttach : account.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewid());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            account.setReviewList(attachedReviewList);
            List<Favorite> attachedFavoriteList = new ArrayList<Favorite>();
            for (Favorite favoriteListFavoriteToAttach : account.getFavoriteList()) {
                favoriteListFavoriteToAttach = em.getReference(favoriteListFavoriteToAttach.getClass(), favoriteListFavoriteToAttach.getFavoriteid());
                attachedFavoriteList.add(favoriteListFavoriteToAttach);
            }
            account.setFavoriteList(attachedFavoriteList);
            List<Cart> attachedCartList = new ArrayList<Cart>();
            for (Cart cartListCartToAttach : account.getCartList()) {
                cartListCartToAttach = em.getReference(cartListCartToAttach.getClass(), cartListCartToAttach.getCartid());
                attachedCartList.add(cartListCartToAttach);
            }
            account.setCartList(attachedCartList);
            List<History> attachedHistoryList = new ArrayList<History>();
            for (History historyListHistoryToAttach : account.getHistoryList()) {
                historyListHistoryToAttach = em.getReference(historyListHistoryToAttach.getClass(), historyListHistoryToAttach.getHistoryid());
                attachedHistoryList.add(historyListHistoryToAttach);
            }
            account.setHistoryList(attachedHistoryList);
            List<Registeremail> attachedRegisteremailList = new ArrayList<Registeremail>();
            for (Registeremail registeremailListRegisteremailToAttach : account.getRegisteremailList()) {
                registeremailListRegisteremailToAttach = em.getReference(registeremailListRegisteremailToAttach.getClass(), registeremailListRegisteremailToAttach.getRegisterid());
                attachedRegisteremailList.add(registeremailListRegisteremailToAttach);
            }
            account.setRegisteremailList(attachedRegisteremailList);
            em.persist(account);
            for (Passwordreset passwordresetListPasswordreset : account.getPasswordresetList()) {
                Account oldAccountidOfPasswordresetListPasswordreset = passwordresetListPasswordreset.getAccountid();
                passwordresetListPasswordreset.setAccountid(account);
                passwordresetListPasswordreset = em.merge(passwordresetListPasswordreset);
                if (oldAccountidOfPasswordresetListPasswordreset != null) {
                    oldAccountidOfPasswordresetListPasswordreset.getPasswordresetList().remove(passwordresetListPasswordreset);
                    oldAccountidOfPasswordresetListPasswordreset = em.merge(oldAccountidOfPasswordresetListPasswordreset);
                }
            }
            for (Review reviewListReview : account.getReviewList()) {
                Account oldAccountidOfReviewListReview = reviewListReview.getAccountid();
                reviewListReview.setAccountid(account);
                reviewListReview = em.merge(reviewListReview);
                if (oldAccountidOfReviewListReview != null) {
                    oldAccountidOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldAccountidOfReviewListReview = em.merge(oldAccountidOfReviewListReview);
                }
            }
            for (Favorite favoriteListFavorite : account.getFavoriteList()) {
                Account oldAccountidOfFavoriteListFavorite = favoriteListFavorite.getAccountid();
                favoriteListFavorite.setAccountid(account);
                favoriteListFavorite = em.merge(favoriteListFavorite);
                if (oldAccountidOfFavoriteListFavorite != null) {
                    oldAccountidOfFavoriteListFavorite.getFavoriteList().remove(favoriteListFavorite);
                    oldAccountidOfFavoriteListFavorite = em.merge(oldAccountidOfFavoriteListFavorite);
                }
            }
            for (Cart cartListCart : account.getCartList()) {
                Account oldAccountidOfCartListCart = cartListCart.getAccountid();
                cartListCart.setAccountid(account);
                cartListCart = em.merge(cartListCart);
                if (oldAccountidOfCartListCart != null) {
                    oldAccountidOfCartListCart.getCartList().remove(cartListCart);
                    oldAccountidOfCartListCart = em.merge(oldAccountidOfCartListCart);
                }
            }
            for (History historyListHistory : account.getHistoryList()) {
                Account oldAccountidOfHistoryListHistory = historyListHistory.getAccountid();
                historyListHistory.setAccountid(account);
                historyListHistory = em.merge(historyListHistory);
                if (oldAccountidOfHistoryListHistory != null) {
                    oldAccountidOfHistoryListHistory.getHistoryList().remove(historyListHistory);
                    oldAccountidOfHistoryListHistory = em.merge(oldAccountidOfHistoryListHistory);
                }
            }
            for (Registeremail registeremailListRegisteremail : account.getRegisteremailList()) {
                Account oldAccountidOfRegisteremailListRegisteremail = registeremailListRegisteremail.getAccountid();
                registeremailListRegisteremail.setAccountid(account);
                registeremailListRegisteremail = em.merge(registeremailListRegisteremail);
                if (oldAccountidOfRegisteremailListRegisteremail != null) {
                    oldAccountidOfRegisteremailListRegisteremail.getRegisteremailList().remove(registeremailListRegisteremail);
                    oldAccountidOfRegisteremailListRegisteremail = em.merge(oldAccountidOfRegisteremailListRegisteremail);
                }
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

    public void edit(Account account) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account persistentAccount = em.find(Account.class, account.getAccountid());
            List<Passwordreset> passwordresetListOld = persistentAccount.getPasswordresetList();
            List<Passwordreset> passwordresetListNew = account.getPasswordresetList();
            List<Review> reviewListOld = persistentAccount.getReviewList();
            List<Review> reviewListNew = account.getReviewList();
            List<Favorite> favoriteListOld = persistentAccount.getFavoriteList();
            List<Favorite> favoriteListNew = account.getFavoriteList();
            List<Cart> cartListOld = persistentAccount.getCartList();
            List<Cart> cartListNew = account.getCartList();
            List<History> historyListOld = persistentAccount.getHistoryList();
            List<History> historyListNew = account.getHistoryList();
            List<Registeremail> registeremailListOld = persistentAccount.getRegisteremailList();
            List<Registeremail> registeremailListNew = account.getRegisteremailList();
            List<Passwordreset> attachedPasswordresetListNew = new ArrayList<Passwordreset>();
            for (Passwordreset passwordresetListNewPasswordresetToAttach : passwordresetListNew) {
                passwordresetListNewPasswordresetToAttach = em.getReference(passwordresetListNewPasswordresetToAttach.getClass(), passwordresetListNewPasswordresetToAttach.getResetid());
                attachedPasswordresetListNew.add(passwordresetListNewPasswordresetToAttach);
            }
            passwordresetListNew = attachedPasswordresetListNew;
            account.setPasswordresetList(passwordresetListNew);
            List<Review> attachedReviewListNew = new ArrayList<Review>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getReviewid());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            account.setReviewList(reviewListNew);
            List<Favorite> attachedFavoriteListNew = new ArrayList<Favorite>();
            for (Favorite favoriteListNewFavoriteToAttach : favoriteListNew) {
                favoriteListNewFavoriteToAttach = em.getReference(favoriteListNewFavoriteToAttach.getClass(), favoriteListNewFavoriteToAttach.getFavoriteid());
                attachedFavoriteListNew.add(favoriteListNewFavoriteToAttach);
            }
            favoriteListNew = attachedFavoriteListNew;
            account.setFavoriteList(favoriteListNew);
            List<Cart> attachedCartListNew = new ArrayList<Cart>();
            for (Cart cartListNewCartToAttach : cartListNew) {
                cartListNewCartToAttach = em.getReference(cartListNewCartToAttach.getClass(), cartListNewCartToAttach.getCartid());
                attachedCartListNew.add(cartListNewCartToAttach);
            }
            cartListNew = attachedCartListNew;
            account.setCartList(cartListNew);
            List<History> attachedHistoryListNew = new ArrayList<History>();
            for (History historyListNewHistoryToAttach : historyListNew) {
                historyListNewHistoryToAttach = em.getReference(historyListNewHistoryToAttach.getClass(), historyListNewHistoryToAttach.getHistoryid());
                attachedHistoryListNew.add(historyListNewHistoryToAttach);
            }
            historyListNew = attachedHistoryListNew;
            account.setHistoryList(historyListNew);
            List<Registeremail> attachedRegisteremailListNew = new ArrayList<Registeremail>();
            for (Registeremail registeremailListNewRegisteremailToAttach : registeremailListNew) {
                registeremailListNewRegisteremailToAttach = em.getReference(registeremailListNewRegisteremailToAttach.getClass(), registeremailListNewRegisteremailToAttach.getRegisterid());
                attachedRegisteremailListNew.add(registeremailListNewRegisteremailToAttach);
            }
            registeremailListNew = attachedRegisteremailListNew;
            account.setRegisteremailList(registeremailListNew);
            account = em.merge(account);
            for (Passwordreset passwordresetListOldPasswordreset : passwordresetListOld) {
                if (!passwordresetListNew.contains(passwordresetListOldPasswordreset)) {
                    passwordresetListOldPasswordreset.setAccountid(null);
                    passwordresetListOldPasswordreset = em.merge(passwordresetListOldPasswordreset);
                }
            }
            for (Passwordreset passwordresetListNewPasswordreset : passwordresetListNew) {
                if (!passwordresetListOld.contains(passwordresetListNewPasswordreset)) {
                    Account oldAccountidOfPasswordresetListNewPasswordreset = passwordresetListNewPasswordreset.getAccountid();
                    passwordresetListNewPasswordreset.setAccountid(account);
                    passwordresetListNewPasswordreset = em.merge(passwordresetListNewPasswordreset);
                    if (oldAccountidOfPasswordresetListNewPasswordreset != null && !oldAccountidOfPasswordresetListNewPasswordreset.equals(account)) {
                        oldAccountidOfPasswordresetListNewPasswordreset.getPasswordresetList().remove(passwordresetListNewPasswordreset);
                        oldAccountidOfPasswordresetListNewPasswordreset = em.merge(oldAccountidOfPasswordresetListNewPasswordreset);
                    }
                }
            }
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    reviewListOldReview.setAccountid(null);
                    reviewListOldReview = em.merge(reviewListOldReview);
                }
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Account oldAccountidOfReviewListNewReview = reviewListNewReview.getAccountid();
                    reviewListNewReview.setAccountid(account);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldAccountidOfReviewListNewReview != null && !oldAccountidOfReviewListNewReview.equals(account)) {
                        oldAccountidOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldAccountidOfReviewListNewReview = em.merge(oldAccountidOfReviewListNewReview);
                    }
                }
            }
            for (Favorite favoriteListOldFavorite : favoriteListOld) {
                if (!favoriteListNew.contains(favoriteListOldFavorite)) {
                    favoriteListOldFavorite.setAccountid(null);
                    favoriteListOldFavorite = em.merge(favoriteListOldFavorite);
                }
            }
            for (Favorite favoriteListNewFavorite : favoriteListNew) {
                if (!favoriteListOld.contains(favoriteListNewFavorite)) {
                    Account oldAccountidOfFavoriteListNewFavorite = favoriteListNewFavorite.getAccountid();
                    favoriteListNewFavorite.setAccountid(account);
                    favoriteListNewFavorite = em.merge(favoriteListNewFavorite);
                    if (oldAccountidOfFavoriteListNewFavorite != null && !oldAccountidOfFavoriteListNewFavorite.equals(account)) {
                        oldAccountidOfFavoriteListNewFavorite.getFavoriteList().remove(favoriteListNewFavorite);
                        oldAccountidOfFavoriteListNewFavorite = em.merge(oldAccountidOfFavoriteListNewFavorite);
                    }
                }
            }
            for (Cart cartListOldCart : cartListOld) {
                if (!cartListNew.contains(cartListOldCart)) {
                    cartListOldCart.setAccountid(null);
                    cartListOldCart = em.merge(cartListOldCart);
                }
            }
            for (Cart cartListNewCart : cartListNew) {
                if (!cartListOld.contains(cartListNewCart)) {
                    Account oldAccountidOfCartListNewCart = cartListNewCart.getAccountid();
                    cartListNewCart.setAccountid(account);
                    cartListNewCart = em.merge(cartListNewCart);
                    if (oldAccountidOfCartListNewCart != null && !oldAccountidOfCartListNewCart.equals(account)) {
                        oldAccountidOfCartListNewCart.getCartList().remove(cartListNewCart);
                        oldAccountidOfCartListNewCart = em.merge(oldAccountidOfCartListNewCart);
                    }
                }
            }
            for (History historyListOldHistory : historyListOld) {
                if (!historyListNew.contains(historyListOldHistory)) {
                    historyListOldHistory.setAccountid(null);
                    historyListOldHistory = em.merge(historyListOldHistory);
                }
            }
            for (History historyListNewHistory : historyListNew) {
                if (!historyListOld.contains(historyListNewHistory)) {
                    Account oldAccountidOfHistoryListNewHistory = historyListNewHistory.getAccountid();
                    historyListNewHistory.setAccountid(account);
                    historyListNewHistory = em.merge(historyListNewHistory);
                    if (oldAccountidOfHistoryListNewHistory != null && !oldAccountidOfHistoryListNewHistory.equals(account)) {
                        oldAccountidOfHistoryListNewHistory.getHistoryList().remove(historyListNewHistory);
                        oldAccountidOfHistoryListNewHistory = em.merge(oldAccountidOfHistoryListNewHistory);
                    }
                }
            }
            for (Registeremail registeremailListOldRegisteremail : registeremailListOld) {
                if (!registeremailListNew.contains(registeremailListOldRegisteremail)) {
                    registeremailListOldRegisteremail.setAccountid(null);
                    registeremailListOldRegisteremail = em.merge(registeremailListOldRegisteremail);
                }
            }
            for (Registeremail registeremailListNewRegisteremail : registeremailListNew) {
                if (!registeremailListOld.contains(registeremailListNewRegisteremail)) {
                    Account oldAccountidOfRegisteremailListNewRegisteremail = registeremailListNewRegisteremail.getAccountid();
                    registeremailListNewRegisteremail.setAccountid(account);
                    registeremailListNewRegisteremail = em.merge(registeremailListNewRegisteremail);
                    if (oldAccountidOfRegisteremailListNewRegisteremail != null && !oldAccountidOfRegisteremailListNewRegisteremail.equals(account)) {
                        oldAccountidOfRegisteremailListNewRegisteremail.getRegisteremailList().remove(registeremailListNewRegisteremail);
                        oldAccountidOfRegisteremailListNewRegisteremail = em.merge(oldAccountidOfRegisteremailListNewRegisteremail);
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
                Integer id = account.getAccountid();
                if (findAccount(id) == null) {
                    throw new NonexistentEntityException("The account with id " + id + " no longer exists.");
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
            Account account;
            try {
                account = em.getReference(Account.class, id);
                account.getAccountid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
            }
            List<Passwordreset> passwordresetList = account.getPasswordresetList();
            for (Passwordreset passwordresetListPasswordreset : passwordresetList) {
                passwordresetListPasswordreset.setAccountid(null);
                passwordresetListPasswordreset = em.merge(passwordresetListPasswordreset);
            }
            List<Review> reviewList = account.getReviewList();
            for (Review reviewListReview : reviewList) {
                reviewListReview.setAccountid(null);
                reviewListReview = em.merge(reviewListReview);
            }
            List<Favorite> favoriteList = account.getFavoriteList();
            for (Favorite favoriteListFavorite : favoriteList) {
                favoriteListFavorite.setAccountid(null);
                favoriteListFavorite = em.merge(favoriteListFavorite);
            }
            List<Cart> cartList = account.getCartList();
            for (Cart cartListCart : cartList) {
                cartListCart.setAccountid(null);
                cartListCart = em.merge(cartListCart);
            }
            List<History> historyList = account.getHistoryList();
            for (History historyListHistory : historyList) {
                historyListHistory.setAccountid(null);
                historyListHistory = em.merge(historyListHistory);
            }
            List<Registeremail> registeremailList = account.getRegisteremailList();
            for (Registeremail registeremailListRegisteremail : registeremailList) {
                registeremailListRegisteremail.setAccountid(null);
                registeremailListRegisteremail = em.merge(registeremailListRegisteremail);
            }
            em.remove(account);
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

    public List<Account> findAccountEntities() {
        return findAccountEntities(true, -1, -1);
    }

    public List<Account> findAccountEntities(int maxResults, int firstResult) {
        return findAccountEntities(false, maxResults, firstResult);
    }

    private List<Account> findAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Account.class));
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

    public Account findAccount(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Account.class, id);
        } finally {
            em.close();
        }
    }
    
    public Account findAccountByUserName(String username) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Account.findByUsername");
        query.setParameter("username", username);
        try {
            return (Account) query.getSingleResult();
        } catch(Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Account findAccountByEmail(String email) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Account.findByEmail");
        query.setParameter("email", email);
        try {
            return (Account) query.getSingleResult();
        } catch(Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Account findAccountByPhoneNumber(String phonenumber) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Account.findByPhonenumber");
        query.setParameter("phonenumber", phonenumber);
        try {
            return (Account) query.getSingleResult();
        } catch(Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public int getAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Account> rt = cq.from(Account.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
