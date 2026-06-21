package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.OrderItemDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CartDAO cartDAO = new CartDAOImpl();
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = getLoggedInUser(request);

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ================= GET CART =================
        Cart cart = cartDAO.getOrCreateCartByUserId(loggedInUser.getUserId());

        List<CartItem> cartItems =
                cartItemDAO.getCartItemsWithProductDetailsByCartId(cart.getCartId());

        BigDecimal cartTotal =
                cartItemDAO.getCartTotal(cart.getCartId());

        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // ================= GET FORM DATA =================
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String pincode = request.getParameter("pincode");
        String paymentMethod = request.getParameter("paymentMethod");

        // ================= CREATE ORDER =================
        Order order = new Order();
        order.setUserId(loggedInUser.getUserId());
       
        order.setTotalAmount(cartTotal.doubleValue());
        order.setOrderStatus("PLACED");

        order.setDeliveryName(name);
        order.setDeliveryPhone(phone);
        order.setDeliveryAddressLine1(address);
        order.setDeliveryCity(city);
        order.setDeliveryPincode(pincode);

        // optional (if not using now)
        order.setDeliveryAddressLine2("");
        order.setDeliveryState("Karnataka"); // or any value
        order.setDeliveryCountry("India");

        order.setPaymentMethod(paymentMethod);

        int orderId = orderDAO.createOrder(order);

        // ================= CREATE ORDER ITEMS =================
        for (CartItem item : cartItems) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrderId(orderId);
            orderItem.setProductId(item.getProductId());
            orderItem.setVariantId(item.getVariantId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());

            orderItemDAO.addOrderItem(orderItem);
        }

        // ================= CLEAR CART =================
        cartItemDAO.clearCart(cart.getCartId());

        // ================= REDIRECT =================
        response.sendRedirect(request.getContextPath() + "/order-confirmation?orderId=" + orderId);
    }

    // ================= HELPER =================
    private User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) return null;

        return (User) session.getAttribute("loggedInUser");
    }
}
