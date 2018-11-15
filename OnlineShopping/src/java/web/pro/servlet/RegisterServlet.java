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

        if (username != null) {
            /*Account checkaccount = ajc.findAccountByUserName(username);
            if (username == checkaccount.getUsername()) {
                request.setAttribute("messageusername", "This username has been use.");
                getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
                return;
            }*/
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
            RegisteremailJpaController rejc = new RegisteremailJpaController(utx, emf);
            Registeremail reg = new Registeremail();
            reg.setAccountid(account);
            Account acc = ajc.findAccountByUserName(account.getUsername());
            reg.setRegistercode(UUID.randomUUID().toString().replace("-", "").substring(0, 15));
            rejc.create(reg);
            HttpSession session = request.getSession(false);
            session.setAttribute("account", acc);
            getServletContext().getRequestDispatcher("/AccountActivate.jsp").forward(request, response);
            return;
        }

        /*if (accountUserName != null) {
            request.setAttribute("messageusername", "This username has been use.");
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
            request.setAttribute("address", address);
            request.setAttribute("postCode", postCode);
            if (accountPhoneNumber != null) {
                request.setAttribute("messagephonenumber", "This phoneNumber has been use.");
                if (accountEmail != null) {
                    request.setAttribute("messageemail", "This email has been use.");
                } else {
                    request.setAttribute("email", email);
                }
            } else {
                request.setAttribute("phoneNumber", phoneNumber);
            }
            if (password != repassword) {
                request.setAttribute("messagepassword", "Password in two field are not match.");
            }
            getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
        }
        if (accountPhoneNumber != null) {
            request.setAttribute("messagephonenumber", "This phoneNumber has been use.");
            request.setAttribute("username", username);
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
            request.setAttribute("address", address);
            request.setAttribute("postCode", postCode);
            if (accountEmail != null) {
                request.setAttribute("messageemail", "This email has been use.");
            } else {
                request.setAttribute("email", email);
            }
            if (password != repassword) {
                request.setAttribute("messagepassword", "Password in two field are not match.");
            }
            getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
        }
        if (accountEmail != null) {
            request.setAttribute("messageemail", "This email has been use.");
            request.setAttribute("username", username);
            request.setAttribute("phoneNumber", phoneNumber);
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
            request.setAttribute("address", address);
            request.setAttribute("postCode", postCode);
            if (password != repassword) {
                request.setAttribute("messagepassword", "Password in two field are not match.");
            }
            getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
        }
        if (password != repassword) {
            request.setAttribute("messagepassword", "Password in two field are not match.");
            getServletContext().getRequestDispatcher("/Register.jsp").forward(request, response);
        }
        if (password == repassword && accountEmail == null && accountPhoneNumber == null && accountUserName == null) {
            request.setAttribute("messageRegister", "Register Success!!!\nPlease Confirm In your Email address");
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
            RegisteremailJpaController rejc = new RegisteremailJpaController(utx, emf);
            Registeremail reg = new Registeremail();
            reg.setAccountid(account);
            Account acc = ajc.findAccountByUserName(account.getUsername());
            reg.setRegistercode(UUID.randomUUID().toString().replace("-","").substring(0,15));
            rejc.create(reg);
            HttpSession session = request.getSession(false);
            session.setAttribute("account", acc);
            getServletContext().getRequestDispatcher("/AccountActivate.jsp").forward(request, response);
            return;
        }*/
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
