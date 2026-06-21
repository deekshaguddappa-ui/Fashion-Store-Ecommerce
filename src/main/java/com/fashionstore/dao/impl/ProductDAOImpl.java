package com.fashionstore.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

public class ProductDAOImpl implements ProductDAO {

    private static final String INSERT_PRODUCT_SQL = """
        INSERT INTO products 
        (category_id, product_name, brand, description, price, image_url, is_active)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

    private static final String UPDATE_PRODUCT_SQL = """
        UPDATE products SET 
        category_id = ?, product_name = ?, brand = ?, description = ?, 
        price = ?, image_url = ?, is_active = ?
        WHERE product_id = ?
    """;

    private static final String DELETE_PRODUCT_SQL = """
        DELETE FROM products WHERE product_id = ?
    """;

    private static final String GET_PRODUCT_BY_ID_SQL = """
        SELECT * FROM products WHERE product_id = ?
    """;

    private static final String GET_ALL_PRODUCTS_SQL = """
        SELECT * FROM products ORDER BY product_id DESC
    """;

    private static final String GET_ACTIVE_PRODUCTS_SQL = """
        SELECT * FROM products WHERE is_active = TRUE
    """;

    private static final String GET_PRODUCTS_BY_CATEGORY_SQL = """
        SELECT * FROM products WHERE category_id = ?
    """;

    private static final String SEARCH_PRODUCTS_SQL = """
        SELECT * FROM products 
        WHERE product_name LIKE ? OR brand LIKE ?
    """;

    private static final String GET_PRODUCTS_BY_PRICE_RANGE_SQL = """
        SELECT * FROM products 
        WHERE price BETWEEN ? AND ?
    """;

    private static final String GET_PRODUCTS_BY_BRAND_SQL = """
        SELECT * FROM products WHERE brand = ?
    """;

    private static final String UPDATE_PRODUCT_STATUS_SQL = """
        UPDATE products SET is_active = ? WHERE product_id = ?
    """;
    private static final String GET_LATEST_PRODUCTS_SQL = 
    	    "SELECT * FROM products WHERE is_active = true ORDER BY created_at DESC LIMIT ?";

    @Override
    public boolean addProduct(Product product) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_PRODUCT_SQL)) {

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getBrand());
            ps.setString(4, product.getDescription());
            ps.setDouble(5, product.getPrice());
            ps.setString(6, product.getImageUrl());
            ps.setBoolean(7, product.isActive());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_PRODUCT_SQL)) {

            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getBrand());
            ps.setString(4, product.getDescription());
            ps.setDouble(5, product.getPrice());
            ps.setString(6, product.getImageUrl());
            ps.setBoolean(7, product.isActive());
            ps.setInt(8, product.getProductId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProduct(int productId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_PRODUCT_SQL)) {

            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public List<Product> getFilteredProducts(
            Integer categoryId,
            String keyword,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String sortBy) {

        List<Product> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (categoryId != null) {
            sql.append(" AND category_id = ? ");
            params.add(categoryId);
        }

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND product_name LIKE ? ");
            params.add("%" + keyword + "%");
        }

        if (minPrice != null) {
            sql.append(" AND price >= ? ");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql.append(" AND price <= ? ");
            params.add(maxPrice);
        }

        // 🔥 THIS IS THE MISSING PART (SORT LOGIC)
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc":
                    sql.append(" ORDER BY price ASC ");
                    break;
                case "priceDesc":
                    sql.append(" ORDER BY price DESC ");
                    break;
                case "latest":
                    sql.append(" ORDER BY product_id DESC ");
                    break;
            }
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public List<Product> getRelatedProducts(int categoryId) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE category_id = ? ORDER BY product_id DESC LIMIT 4";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



         
    @Override
    public List<Product> getLatestProducts(int limit) {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_LATEST_PRODUCTS_SQL)) {

            ps.setInt(1, limit);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public Product getProductById(int productId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_PRODUCT_BY_ID_SQL)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL_PRODUCTS_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> getAllActiveProducts() {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ACTIVE_PRODUCTS_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_PRODUCTS_BY_CATEGORY_SQL)) {

            ps.setInt(1, categoryId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SEARCH_PRODUCTS_SQL)) {

            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_PRODUCTS_BY_PRICE_RANGE_SQL)) {

            ps.setBigDecimal(1, minPrice);
            ps.setBigDecimal(2, maxPrice);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_PRODUCTS_BY_BRAND_SQL)) {

            ps.setString(1, brand);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateProductStatus(int productId, boolean isActive) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_PRODUCT_STATUS_SQL)) {

            ps.setBoolean(1, isActive);
            ps.setInt(2, productId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws Exception {
        Product product = new Product();

        product.setProductId(rs.getInt("product_id"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setProductName(rs.getString("product_name"));
        product.setBrand(rs.getString("brand"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setImageUrl(rs.getString("image_url"));
        product.setActive(rs.getBoolean("is_active"));
        product.setCreatedAt(rs.getTimestamp("created_at"));

        return product;
    }
}
