package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CartDAO cartDAO = new CartDAOImpl();
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();

    // ================= GET =================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = getLoggedInUser(request);

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cart cart = cartDAO.getOrCreateCartByUserId(loggedInUser.getUserId());

        List<CartItem> cartItems = cartItemDAO.getCartItemsByCartId(cart.getCartId());
        BigDecimal cartTotal = cartItemDAO.getCartTotal(cart.getCartId());

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", cartTotal);

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    // ================= POST =================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = getLoggedInUser(request);

        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        switch (action) {
            case "add":
                handleAddToCart(request, response, loggedInUser);
                break;

            case "update":
                handleUpdateCart(request, response, loggedInUser);
                break;

            case "remove":
                handleRemoveFromCart(request, response, loggedInUser);
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    // ================= ADD =================
    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {

        Integer variantId = parseInteger(request.getParameter("variantId"));
        Integer quantity = parseInteger(request.getParameter("quantity"));

        if (variantId == null || quantity == null || quantity <= 0) {
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid");
            return;
        }

        ProductVariant variant = productVariantDAO.getVariantById(variantId);

        if (variant == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid_variant");
            return;
        }

        Cart cart = cartDAO.getOrCreateCartByUserId(user.getUserId());

        CartItem existingItem = cartItemDAO.getCartItemByCartIdAndVariantId(cart.getCartId(), variantId);

        boolean result;

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            result = cartItemDAO.updateCartItemQuantity(existingItem.getCartItemId(), newQuantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCartId(cart.getCartId());
            newItem.setVariantId(variantId);
            newItem.setQuantity(quantity);

            result = cartItemDAO.addCartItem(newItem);
        }

        if (result) {
            response.sendRedirect(request.getContextPath() + "/cart?success=added");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart?error=failed");
        }
    }

    // ================= UPDATE =================
    private void handleUpdateCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {

        Integer cartItemId = parseInteger(request.getParameter("cartItemId"));
        Integer quantity = parseInteger(request.getParameter("quantity"));

        if (cartItemId == null || quantity == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid");
            return;
        }

        if (quantity <= 0) {
            cartItemDAO.removeCartItem(cartItemId);
            response.sendRedirect(request.getContextPath() + "/cart?success=removed");
            return;
        }

        boolean updated = cartItemDAO.updateCartItemQuantity(cartItemId, quantity);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/cart?success=updated");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart?error=failed");
        }
    }

    // ================= REMOVE =================
    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {

        Integer cartItemId = parseInteger(request.getParameter("cartItemId"));

        if (cartItemId == null) {
            response.sendRedirect(request.getContextPath() + "/cart?error=invalid");
            return;
        }

        boolean removed = cartItemDAO.removeCartItem(cartItemId);

        if (removed) {
            response.sendRedirect(request.getContextPath() + "/cart?success=removed");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart?error=failed");
        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
