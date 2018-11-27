/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import web.pro.model.Account;
import web.pro.model.Favorite;
import web.pro.model.Product;
import web.pro.model.controller.AccountJpaController;
import web.pro.model.controller.FavoriteJpaController;
import web.pro.model.controller.ProductJpaController;

/**
 *
 * @author lara_
 */
public class AddFavoriteServlet extends HttpServlet {

    @PersistenceUnit(unitName = "OnlineShoppingPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        HttpSession session = request.getSession(false);
        String productid = request.getParameter("productid");
        int productint = Integer.valueOf(productid);
        if (session != null) {
            if (session.getAttribute("account") == null) {
                getServletContext().getRequestDispatcher("/Login").forward(request, response);
                return;
            }
            AccountJpaController accCtrl = new AccountJpaController(utx, emf);
            Account account = accCtrl.findAccount(((Account) session.getAttribute("account")).getAccountid());

            ProductJpaController proCtrl = new ProductJpaController(utx, emf);
            Product product = proCtrl.findProduct(productint);

            FavoriteJpaController favCtrl = new FavoriteJpaController(utx, emf);
            Favorite fav = new Favorite();
            fav.setFavoriteid(favCtrl.getFavoriteCount() + 1);
            fav.setAccountid(account);
            fav.setProductid(product);

            for (int i = 1; i < favCtrl.getFavoriteCount(); i++) {
                Favorite check = favCtrl.findFavorite(i);
                if (check == null) {
                    fav.setFavoriteid(i);
                    break;
                }
            }
            favCtrl.create(fav);

            List<Favorite> favlist = favCtrl.findFavoriteEntities();
            List<Favorite> favadd = new ArrayList<>();
            for (Favorite fv : favlist) {
                if (fv.getAccountid().getAccountid() == account.getAccountid()) {
                    favadd.add(fv);
                }
            }
            account.setFavoriteList(favlist);

            session.setAttribute("account", account);
            getServletContext().getRequestDispatcher("/ProductDetail").forward(request, response);
            return;
        }
        getServletContext().getRequestDispatcher("/Login").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddFavoriteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AddFavoriteServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
