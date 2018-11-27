/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
public class ProductDetailServlet extends HttpServlet {

    @PersistenceUnit(unitName = "OnlineShoppingPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String productid = request.getParameter("productid");
        if (productid != null) {
            ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
            int productint = Integer.valueOf(productid);
            Product product = productJpaCtrl.findProduct(productint);
            request.setAttribute("product", product);

            AccountJpaController accCtrl = new AccountJpaController(utx, emf);
            Account account = accCtrl.findAccount(((Account) session.getAttribute("account")).getAccountid());

            boolean isfav = false;
            FavoriteJpaController favCtrl = new FavoriteJpaController(utx, emf);
            for (int i = 0; i < favCtrl.getFavoriteCount() + 1; i++) {
                Favorite myfav = favCtrl.findFavorite(i);
                if (myfav != null) {
                    if (myfav.getAccountid().getAccountid().equals(account.getAccountid())) {
                        if (myfav.getProductid().getProductid().equals(product.getProductid())) {
                            isfav = true;
                            break;
                        }
                    }
                }
            }
            System.out.println(isfav);
            request.setAttribute("isfav", isfav);

            getServletContext().getRequestDispatcher("/ProductDetail.jsp").forward(request, response);
            return;
        }
        getServletContext().getRequestDispatcher("/ShopPage.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
