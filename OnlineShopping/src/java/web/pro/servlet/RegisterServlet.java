/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import web.pro.model.Account;
import web.pro.model.controller.AccountJpaController;

/**
 *
 * @author 60130
 */
public class RegisterServlet extends HttpServlet {

    @Resource
    UserTransaction utx;

    @PersistenceUnit(unitName = "MyFirstWebAppPU")
    EntityManagerFactory emf;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        String postCode = request.getParameter("postcode");
        String phoneNumber = request.getParameter("phonenumber");
        AccountJpaController ajc = new AccountJpaController(utx, emf);
        Account account1 = ajc.findAccountByUserName(userName);
        Account account2 = ajc.findAccountByPhoneNumber(phoneNumber);
        Account account3 = ajc.findAccountByEmail(email);
        if (account1 != null) {
            request.setAttribute("message1", "This username has been use.");
            if (account2 != null) {
                request.setAttribute("message2", "This phoneNumber has been use.");
                if (account3 != null) {
                    request.setAttribute("message3", "This email has been use.");

                }
            }
            getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
        }
        if (account2 != null) {
            request.setAttribute("message2", "This phoneNumber has been use.");
            if (account3 != null) {
                request.setAttribute("message3", "This email has been use.");

            }
            getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
        }
        if (account3 != null) {
            request.setAttribute("message3", "This email has been use.");
            getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
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
