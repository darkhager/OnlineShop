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
import web.pro.model.controller.AccountJpaController;

/**
 *
 * @author 60130
 */
public class LoginServlet extends HttpServlet {

    @PersistenceUnit(unitName = "OnlineShoppingPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        if (userName != null && password != null
                && userName.trim().length() > 0 && password.trim().length() > 0) {
            AccountJpaController ajc = new AccountJpaController(utx, emf);
            Account account1 = ajc.findAccountByUserName(userName);
            Account account2 = ajc.findAccountByEmail(userName);
            Account account3 = ajc.findAccountByPhoneNumber(userName);
            
            if(account1 == null){
                System.out.println("account1 null");
            }
            if(account2 == null){
                System.out.println("account2 null");
            }
            if(account3 == null){
                System.out.println("account3 null");
            }
            
            if (account1 != null) {
                if (account1.getPassword().equals(password)) {
                    HttpSession session = request.getSession(false);
                    session.setAttribute("account", account1);
                    getServletContext().getRequestDispatcher("/ShopPage.jsp").forward(request, response);
                    return;
                }
            } 
            if (account2 != null) {
                if (account2.getPassword().equals(password)) {
                    HttpSession session = request.getSession(false);
                    session.setAttribute("account", account2);
                    getServletContext().getRequestDispatcher("/ShopPage.jsp").forward(request, response);
                    return;
                }
            }
            if (account3 != null) {
                if (account3.getPassword().equals(password)) {
                    HttpSession session = request.getSession(false);
                    session.setAttribute("account", account3);
                    getServletContext().getRequestDispatcher("/ShopPage.jsp").forward(request, response);
                    return;
                }
            }
            request.setAttribute("message", "Username or password incorrect please try again");
        }
        getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
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
