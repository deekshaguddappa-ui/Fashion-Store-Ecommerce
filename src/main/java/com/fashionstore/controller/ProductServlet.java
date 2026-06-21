package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ProductDAO productDAO = new ProductDAOImpl();
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔥 Parse parameters (SAFE way like your sir)
        Integer categoryId = parseInteger(request.getParameter("categoryId"));
        String keyword = trimToNull(request.getParameter("keyword"));
        BigDecimal minPrice = parseBigDecimal(request.getParameter("minPrice"));
        BigDecimal maxPrice = parseBigDecimal(request.getParameter("maxPrice"));
        String sortBy = trimToNull(request.getParameter("sortBy"));

      
        List<Product> products = productDAO.getFilteredProducts(
                categoryId, keyword, minPrice, maxPrice, sortBy
        );
        //List<Product> products = productDAO.getAllProducts();

        List<Category> categories = categoryDAO.getAllCategories();

        // 🔥 Send data to JSP
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);

        request.setAttribute("selectedCategoryId", categoryId);
        request.setAttribute("keyword", keyword);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("sortBy", sortBy);

        request.getRequestDispatcher("/WEB-INF/views/products.jsp")
               .forward(request, response);
    }

    // 🔧 Helper method: Integer
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

    // 🔧 Helper method: BigDecimal
    private BigDecimal parseBigDecimal(String value) {
        try {
            if (value != null && !value.trim().isEmpty()) {
                return new BigDecimal(value.trim());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔧 Helper method: String clean
    private String trimToNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}