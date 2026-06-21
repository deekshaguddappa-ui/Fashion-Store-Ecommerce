package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;

import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    private static final String INSERT_SQL =
        "INSERT INTO product_variants (product_id, size, stock) VALUES (?, ?, ?)";

    private static final String UPDATE_SQL =
        "UPDATE product_variants SET product_id=?, size=?, stock=? WHERE variant_id=?";

    private static final String DELETE_SQL =
        "DELETE FROM product_variants WHERE variant_id=?";

    private static final String GET_BY_ID_SQL =
        "SELECT * FROM product_variants WHERE variant_id=?";

    private static final String GET_BY_PRODUCT_SIZE_SQL =
        "SELECT * FROM product_variants WHERE product_id=? AND size=?";

    private static final String GET_BY_PRODUCT_SQL =
        "SELECT * FROM product_variants WHERE product_id=?";

    private static final String GET_ALL_SQL =
        "SELECT * FROM product_variants";

    private static final String UPDATE_STOCK_SQL =
        "UPDATE product_variants SET stock=? WHERE variant_id=?";

    private static final String CHECK_STOCK_SQL =
        "SELECT stock FROM product_variants WHERE variant_id=?";

    @Override
    public boolean addProductVariant(ProductVariant pv) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, pv.getProductId());
            ps.setString(2, pv.getSize());
            ps.setInt(3, pv.getStock());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateProductVariant(ProductVariant pv) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_SQL)) {

            ps.setInt(1, pv.getProductId());
            ps.setString(2, pv.getSize());
            ps.setInt(3, pv.getStock());
            ps.setInt(4, pv.getVariantId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProductVariant(int variantId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, variantId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ProductVariant getVariantById(int variantId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID_SQL)) {

            ps.setInt(1, variantId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProductVariant getVariantByProductIdAndSize(int productId, String size) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_PRODUCT_SIZE_SQL)) {

            ps.setInt(1, productId);
            ps.setString(2, size);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return map(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ProductVariant> getVariantsByProductId(int productId) {
        List<ProductVariant> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_PRODUCT_SQL)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) list.add(map(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ProductVariant> getAllVariants() {
        List<ProductVariant> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateStock(int variantId, int stockQuantity) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_STOCK_SQL)) {

            ps.setInt(1, stockQuantity);
            ps.setInt(2, variantId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isStockAvailable(int variantId, int requiredQuantity) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(CHECK_STOCK_SQL)) {

            ps.setInt(1, variantId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock") >= requiredQuantity;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private ProductVariant map(ResultSet rs) throws Exception {
        ProductVariant pv = new ProductVariant();

        pv.setVariantId(rs.getInt("variant_id"));
        pv.setProductId(rs.getInt("product_id"));
        pv.setSize(rs.getString("size"));
        pv.setStock(rs.getInt("stock"));

        return pv;
    }
}