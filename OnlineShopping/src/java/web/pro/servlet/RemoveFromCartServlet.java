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
import web.pro.model.Cart;
import web.pro.model.Product;
import web.pro.model.controller.AccountJpaController;
import web.pro.model.controller.CartJpaController;
import web.pro.model.controller.ProductJpaController;

/**
 *
 * @author lara_
 */
public class RemoveFromCartServlet extends HttpServlet {

    @PersistenceUnit(unitName = "OnlineShoppingPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        HttpSession session = request.getSession(false);
        String from = request.getParameter("from");
        String productid = request.getParameter("productid");
        int productint = Integer.valueOf(productid);

        AccountJpaController accCtrl = new AccountJpaController(utx, emf);
        Account account = accCtrl.findAccount(((Account) session.getAttribute("account")).getAccountid());
        ProductJpaController proCtrl = new ProductJpaController(utx, emf);
        Product product = proCtrl.findProduct(productint);

        CartJpaController cartCtrl = new CartJpaController(utx, emf);
        Cart cart = new Cart();
        cart.setAccountid(account);
        cart.setProductid(product);
        cart.setAmount(1);

        for (int i = 0; i < cartCtrl.findCartEntities().size() + 1; i++) {
            Cart mycart = cartCtrl.findCart(i + 1);
            if (mycart != null) {
                if (cart.getAccountid().getAccountid().equals(mycart.getAccountid().getAccountid())) {
                    if (cart.getProductid().getProductid().equals(mycart.getProductid().getProductid())) {
                        mycart.setAmount(mycart.getAmount() - 1);
                        cartCtrl.edit(mycart);
                        break;
                    }
                }
            }
        }

        List<Cart> cartlist = cartCtrl.findCartEntities();
        List<Cart> cartadd = new ArrayList<>();
        for (Cart ct : cartlist) {
            if (ct.getAccountid().getAccountid() == account.getAccountid()) {
                cartadd.add(ct);
            }
        }
        account.setCartList(cartlist);
        session.setAttribute("account", account);

        if("cartpage".equals(from)){
            getServletContext().getRequestDispatcher("/Cart").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/ProductPage").forward(request, response);
        }
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
            Logger.getLogger(RemoveFromCartServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RemoveFromCartServlet.class.getName()).log(Level.SEVERE, null, ex);
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
