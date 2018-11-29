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
import web.pro.model.Product;
import web.pro.model.controller.ProductJpaController;

/**
 *
 * @author 60130
 */
public class FilterServlet extends HttpServlet {

    @PersistenceUnit(unitName = "OnlineShoppingPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String priceString = request.getParameter("price");

        if (priceString != null) {
            if (priceString.trim().length() > 0) {
                int price = Integer.parseInt(priceString);
                HttpSession session = request.getSession(false);
                ProductJpaController pjc = new ProductJpaController(utx, emf);
                List<Product> proList = new ArrayList<>();
                switch (price) {
                    case 1:
                        proList = pjc.findProductPrice1();
                        request.setAttribute("result", "Less than ฿1,000");
                        break;
                    case 2:
                        proList = pjc.findProductPrice2();
                        request.setAttribute("result", "฿1,001 - ฿2,000");
                        break;
                    case 3:
                        proList = pjc.findProductPrice3();
                        request.setAttribute("result", "฿2,001 - ฿5,000");
                        break;
                    case 4:
                        proList = pjc.findProductPrice4();
                        request.setAttribute("result", "฿5,001 - ฿10,000");
                        break;
                    case 5:
                        proList = pjc.findProductPrice5();
                        request.setAttribute("result", "฿10,001 - ฿30,000");
                        break;
                    case 6:
                        proList = pjc.findProductPrice6();
                        request.setAttribute("result", "More than ฿30,000");
                        break;
                }
                session.setAttribute("products", proList);
                getServletContext().getRequestDispatcher("/ShopPage.jsp").forward(request, response);
            }
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
