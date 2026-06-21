package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.OrderItemDAOImpl;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/order-confirmation")
public class OrderConfirmationServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = getLoggedInUser(request);

        // 🔒 If not logged in
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ✅ Get orderId from URL
        Integer orderId = parseInteger(request.getParameter("orderId"));

        if (orderId == null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // ✅ Fetch order
        Order order = orderDAO.getOrderById(orderId);

        // 🔒 Security check
        if (order == null || order.getUserId() != loggedInUser.getUserId()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // ✅ Fetch order items
        List<OrderItem> orderItems =
                orderItemDAO.getOrderItemsByOrderId(orderId);

        // ✅ Send to JSP
        request.setAttribute("order", order);
        request.setAttribute("orderItems", orderItems);

        request.getRequestDispatcher("/WEB-INF/views/order-confirmation.jsp")
               .forward(request, response);
    }

    // ================= HELPER =================

    private User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) return null;

        return (User) session.getAttribute("loggedInUser");
    }

    private Integer parseInteger(String value) {
        try {
            if (value != null && !value.trim().isEmpty()) {
                return Integer.parseInt(value.trim());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}