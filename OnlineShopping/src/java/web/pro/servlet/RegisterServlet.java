/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;
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
import web.pro.model.Registeremail;
import web.pro.model.controller.AccountJpaController;
import web.pro.model.controller.RegisteremailJpaController;

/**
 *
 * @author 60130
 */
public class RegisterServlet extends HttpServlet {

    @PersistenceUnit(unitName = "OnlineShoppingPU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        String postCode = request.getParameter("postcode");
        String phoneNumber = request.getParameter("phonenumber");

        AccountJpaController ajc = new AccountJpaController(utx, emf);

        Account accountUserName = ajc.findAccountByUserName(username);
        Account accountPhoneNumber = ajc.findAccountByPhoneNumber(phoneNumber);
        Account accountEmail = ajc.findAccountByEmail(email);

        if (username != null && username.length() > 0
                && password != null && password.length() > 0
                && repassword != null && repassword.length() > 0
                && email != null && email.length() > 0
                && firstName != null && firstName.length() > 0
                && lastName != null && lastName.length() > 0
                && address != null && address.length() > 0
                && postCode != null && postCode.length() > 0
                && phoneNumber != null && phoneNumber.length() > 0
                && accountEmail == null
                && accountPhoneNumber == null
                && accountUserName == null
                && password.equals(repassword)) {
            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setFirstname(firstName);
            account.setLastname(lastName);
            account.setAddress(address);
            account.setPostcode(Integer.valueOf(postCode));
            account.setPhonenumber(phoneNumber);
            ajc.create(account);
            
            getServletContext().getRequestDispatcher("/RegisterSuccess.jsp").forward(request, response);
            return;
        }
        request.setAttribute("message", "Please input your information.");
        if (password != null && password.length() > 0
                && repassword != null && repassword.length() > 0) {
            if (!password.equals(repassword)) {
                request.setAttribute("messagepassword", "Password in two field are not match.");
                request.setAttribute("message", "");
            }
        }
        if (accountUserName != null) {
            request.setAttribute("messageusername", "This username has been use.");
            request.setAttribute("message", "");
            if (accountPhoneNumber != null) {
                request.setAttribute("messagephonenumber", "This phone number has been use.");
                request.setAttribute("message", "");
                if (accountEmail != null) {
                    request.setAttribute("messageemail", "This email has been use.");
                    request.setAttribute("message", "");
                }
            }
        }
        if (accountPhoneNumber != null) {
            request.setAttribute("messagephonenumber", "This phone number has been use.");
            request.setAttribute("message", "");
        }
        if (accountEmail != null) {
            request.setAttribute("messageemail", "This email has been use.");
            request.setAttribute("message", "");
        }
        getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
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
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
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
