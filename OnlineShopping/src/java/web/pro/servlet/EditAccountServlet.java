/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import web.pro.model.controller.AccountJpaController;

/**
 *
 * @author lara_
 */
public class EditAccountServlet extends HttpServlet {

    @PersistenceUnit(unitName = "OnlineShoppingPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Account check = (Account) session.getAttribute("account");
            if (check == null) {
                getServletContext().getRequestDispatcher("/Logout").forward(request, response);
                return;
            } else {
                String newusername = request.getParameter("newusername");
                String oldpassword = request.getParameter("oldpassword");
                String newpassword = request.getParameter("newpassword");
                String newrepassword = request.getParameter("newrepassword");
                String newemail = request.getParameter("newemail");
                String newfname = request.getParameter("newfname");
                String newlname = request.getParameter("newlname");
                String newaddress = request.getParameter("newaddress");
                String newpostcode = request.getParameter("newpostcode");
                String newphonenum = request.getParameter("newphonenum");

                AccountJpaController accCtrl = new AccountJpaController(utx, emf);
                Account account = accCtrl.findAccount(((Account) session.getAttribute("account")).getAccountid());
                if (newusername != null && newusername.length() > 0) {
                    Account checkusername = accCtrl.findAccountByUserName(newusername);
                    if (checkusername != null) {
                        if (checkusername.getUsername().equals(newusername)) {
                            request.setAttribute("invalidusername", "This username is already used.");
                            getServletContext().getRequestDispatcher("/Account.jsp").forward(request, response);
                            return;
                        }
                    }
                    account.setUsername(newusername);
                    request.setAttribute("savemessage", "Your changed has been save.");
                }
                if (oldpassword != null && newpassword != null && newrepassword != null
                         && oldpassword.length() > 0  && newpassword.length() > 0  && newrepassword.length() > 0) {
                    if (!account.getPassword().equals(oldpassword)
                            || !account.getPassword().equals(oldpassword)
                            || !newpassword.equals(newrepassword)) {
                        request.setAttribute("invalidpassword", "Password Incorrect.");
                        getServletContext().getRequestDispatcher("/Account.jsp").forward(request, response);
                        return;
                    }
                    if (account.getPassword().equals(oldpassword) && newpassword.equals(newrepassword)) {
                        account.setPassword(newpassword);
                        request.setAttribute("savemessage", "Your changed has been save.");
                    }
                }
                if (newemail != null && newemail.length() > 0) {
                    Account checkemail = accCtrl.findAccountByEmail(newemail);
                    if (checkemail != null) {
                        if (checkemail.getEmail().equals(newemail)) {
                            request.setAttribute("invalidemail", "This email is already used.");
                            getServletContext().getRequestDispatcher("/Account.jsp").forward(request, response);
                            return;
                        }
                    }
                    account.setEmail(newemail);
                    request.setAttribute("savemessage", "Your changed has been save.");
                }
                if (newfname != null && newfname.length() > 0) {
                    account.setFirstname(newfname);
                    request.setAttribute("savemessage", "Your changed has been save.");
                }
                if (newlname != null && newlname.length() > 0) {
                    account.setLastname(newlname);
                    request.setAttribute("savemessage", "Your changed has been save.");
                }
                if (newaddress != null && newaddress.length() > 0) {
                    account.setAddress(newaddress);
                    request.setAttribute("savemessage", "Your changed has been save.");
                }
                if (newpostcode != null && newpostcode.length() > 0) {
                    int intpostcode = Integer.valueOf(newpostcode);
                    account.setPostcode(intpostcode);
                    request.setAttribute("savemessage", "Your changed has been save.");
                }
                if (newphonenum != null && newphonenum.length() > 0) {
                    Account checkphonenum = accCtrl.findAccountByPhoneNumber(newphonenum);
                    if (checkphonenum != null) {
                        if (checkphonenum.getPhonenumber().equals(newphonenum)) {
                            request.setAttribute("invalidphonenum", "This phone number is already used.");
                            getServletContext().getRequestDispatcher("/Account.jsp").forward(request, response);
                            return;
                        }
                    }
                    account.setPhonenumber(newphonenum);
                    request.setAttribute("savemessage", "Your changed has been save.");
                }
                accCtrl.edit(account);
                session.setAttribute("account", account);
                getServletContext().getRequestDispatcher("/Account.jsp").forward(request, response);
                return;
            }
        }
        getServletContext().getRequestDispatcher("/Logout").forward(request, response);
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
            Logger.getLogger(EditAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EditAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
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
