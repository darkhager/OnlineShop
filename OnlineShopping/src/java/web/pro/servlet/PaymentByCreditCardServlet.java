/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.pro.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import web.pro.model.History;
import web.pro.model.Product;
import web.pro.model.controller.AccountJpaController;
import web.pro.model.controller.CartJpaController;
import web.pro.model.controller.HistoryJpaController;
import web.pro.model.controller.ProductJpaController;

/**
 *
 * @author lara_
 */
public class PaymentByCreditCardServlet extends HttpServlet {

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
                getServletContext().getRequestDispatcher("/Login").forward(request, response);
                return;
            } else {
                AccountJpaController accCtrl = new AccountJpaController(utx, emf);
                Account account = accCtrl.findAccount(((Account) session.getAttribute("account")).getAccountid());
                HistoryJpaController hisCtrl = new HistoryJpaController(utx, emf);
                String order = account.getAccountid() + "00" + hisCtrl.getHistoryCount();

                session.setAttribute("totalpriceall", request.getParameter("totalpriceall"));

                String creditone = request.getParameter("creditone");
                String credittwo = request.getParameter("creditone");
                String creditthree = request.getParameter("creditthree");
                String creditfour = request.getParameter("creditfour");
                String creditcvc = request.getParameter("creditcvc");
                String creditexdone = request.getParameter("creditexdone");
                String creditexdtwo = request.getParameter("creditexdtwo");

                if (creditone != null
                        && credittwo != null
                        && creditthree != null
                        && creditfour != null) {
                    if (creditone.length() > 0
                            && credittwo.length() > 0
                            && creditthree.length() > 0
                            && creditfour.length() > 0) {
                        String creditnum = creditone + credittwo + creditthree + creditfour;
                        if (creditnum.length() == 16) {
                            if (creditexdone != null && creditexdone.length() > 0
                                    && creditexdtwo != null && creditexdtwo.length() > 0) {
                                String creditexd = creditexdone + creditexdtwo;
                                if (creditcvc != null && creditcvc.length() == 3
                                        && creditexd != null && creditexd.length() == 4) {
                                    CartJpaController cartCtrl = new CartJpaController(utx, emf);
                                    Cart cart = new Cart();
                                    cart.setAccountid(account);
                                    int maxcart = cartCtrl.getCartCount() + 1;
                                    for (int i = 0; i < maxcart; i++) {
                                        Cart mycart = cartCtrl.findCart(i);
                                        if (mycart != null) {
                                            if (cart.getAccountid().getAccountid().equals(mycart.getAccountid().getAccountid())) {
                                                ProductJpaController proCtrl = new ProductJpaController(utx, emf);
                                                Product product = proCtrl.findProduct(mycart.getProductid().getProductid());
                                                product.setAmount(product.getAmount() - mycart.getAmount());
                                                proCtrl.edit(product);

                                                History history = new History();
                                                history.setOrdernumber(Integer.valueOf(order));
                                                history.setAccountid(account);
                                                history.setProductid(product);
                                                history.setAmount(mycart.getAmount());
                                                history.setPrice(mycart.getProductid().getPrice());
                                                history.setDate(new Date());
                                                hisCtrl.create(history);

                                                List<History> historylist = hisCtrl.findHistoryEntities();
                                                List<History> historyadd = new ArrayList<>();
                                                for (History htr : historylist) {
                                                    if (htr.getAccountid().getAccountid() == account.getAccountid()) {
                                                        historyadd.add(htr);
                                                    }
                                                }
                                                account.setHistoryList(historylist);

                                                cartCtrl.destroy(i);
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
                                    session.setAttribute("numincart", 0);
                                    getServletContext().getRequestDispatcher("/PaymentSuccess.jsp").forward(request, response);
                                    return;
                                }
                            }
                        }
                    }
                    request.setAttribute("invalidcredit", "Please input your information.");
                }
                getServletContext().getRequestDispatcher("/PaymentConfirm.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PaymentByCreditCardServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PaymentByCreditCardServlet.class.getName()).log(Level.SEVERE, null, ex);
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
