/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import web.pro.model.controller.AccountJpaController;

/**
 *
 * @author lara_
 */
public class PaymentDetailServlet extends HttpServlet {

    @PersistenceUnit(unitName = "OnlineShoppingPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int numincart = (int) session.getAttribute("numincart");
        if (session != null) {
            Account check = (Account) session.getAttribute("account");
            if (check == null) {
                getServletContext().getRequestDispatcher("/Login").forward(request, response);
                return;
            } else {
                if (numincart <= 0) {
                    getServletContext().getRequestDispatcher("/Cart").forward(request, response);
                    return;
                }
                AccountJpaController accCtrl = new AccountJpaController(utx, emf);
                Account account = accCtrl.findAccount(((Account) session.getAttribute("account")).getAccountid());
                session.setAttribute("account", account);
                List<Cart> cart = account.getCartList();
                session.setAttribute("cart", cart);
                session.setAttribute("totalprice", request.getParameter("totalprice"));

                getServletContext().getRequestDispatcher("/PaymentDetail.jsp").forward(request, response);
                return;
            }
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
