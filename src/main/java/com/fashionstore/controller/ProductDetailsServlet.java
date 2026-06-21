package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductVariant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ProductDAO productDAO = new ProductDAOImpl();
    private final ProductVariantDAO productVariantDAO = new ProductVariantDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productIdParam = request.getParameter("productId");

        // ❌ If no productId → redirect
        if (productIdParam == null || productIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        int productId;

        try {
            productId = Integer.parseInt(productIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        // ✅ Get product
        Product product = productDAO.getProductById(productId);

        // ❌ If product not found OR inactive → redirect
        if (product == null || !product.isActive()) {
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        // ✅ Get variants (sizes etc.)
        List<ProductVariant> variants =
                productVariantDAO.getVariantsByProductId(productId);

        // ✅ Get related products (same category)
        List<Product> relatedProducts =
                productDAO.getRelatedProducts(product.getCategoryId());

        // ✅ Send to JSP
        request.setAttribute("product", product);
        request.setAttribute("variants", variants);
        request.setAttribute("relatedProducts", relatedProducts);

        request.getRequestDispatcher("/WEB-INF/views/product-details.jsp")
               .forward(request, response);
    }
}