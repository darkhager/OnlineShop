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
import web.pro.model.Account;
import web.pro.model.Cart;
import web.pro.model.History;
import web.pro.model.controller.exceptions.IllegalOrphanException;
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
        if (account.getFavoriteList() == null) {
            account.setFavoriteList(new ArrayList<Favorite>());
        }
        if (account.getCartList() == null) {
            account.setCartList(new ArrayList<Cart>());
        }
        if (account.getHistoryList() == null) {
            account.setHistoryList(new ArrayList<History>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
            em.persist(account);
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

    public void edit(Account account) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account persistentAccount = em.find(Account.class, account.getAccountid());
            List<Favorite> favoriteListOld = persistentAccount.getFavoriteList();
            List<Favorite> favoriteListNew = account.getFavoriteList();
            List<Cart> cartListOld = persistentAccount.getCartList();
            List<Cart> cartListNew = account.getCartList();
            List<History> historyListOld = persistentAccount.getHistoryList();
            List<History> historyListNew = account.getHistoryList();
            List<String> illegalOrphanMessages = null;
            for (Favorite favoriteListOldFavorite : favoriteListOld) {
                if (!favoriteListNew.contains(favoriteListOldFavorite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favorite " + favoriteListOldFavorite + " since its accountid field is not nullable.");
                }
            }
            for (Cart cartListOldCart : cartListOld) {
                if (!cartListNew.contains(cartListOldCart)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cart " + cartListOldCart + " since its accountid field is not nullable.");
                }
            }
            for (History historyListOldHistory : historyListOld) {
                if (!historyListNew.contains(historyListOldHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain History " + historyListOldHistory + " since its accountid field is not nullable.");
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
            account = em.merge(account);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
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
            List<String> illegalOrphanMessages = null;
            List<Favorite> favoriteListOrphanCheck = account.getFavoriteList();
            for (Favorite favoriteListOrphanCheckFavorite : favoriteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Favorite " + favoriteListOrphanCheckFavorite + " in its favoriteList field has a non-nullable accountid field.");
            }
            List<Cart> cartListOrphanCheck = account.getCartList();
            for (Cart cartListOrphanCheckCart : cartListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Cart " + cartListOrphanCheckCart + " in its cartList field has a non-nullable accountid field.");
            }
            List<History> historyListOrphanCheck = account.getHistoryList();
            for (History historyListOrphanCheckHistory : historyListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the History " + historyListOrphanCheckHistory + " in its historyList field has a non-nullable accountid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
