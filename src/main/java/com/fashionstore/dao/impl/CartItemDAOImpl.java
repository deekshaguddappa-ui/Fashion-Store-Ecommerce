package com.fashionstore.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    // ================= INSERT =================
    private static final String INSERT_CART_ITEM_SQL = """
        INSERT INTO cart_items (cart_id, variant_id, quantity)
        VALUES (?, ?, ?)
    """;

    // ================= UPDATE =================
    private static final String UPDATE_CART_ITEM_SQL = """
        UPDATE cart_items SET quantity = ?
        WHERE cart_item_id = ?
    """;

    private static final String UPDATE_BY_CART_VARIANT_SQL = """
        UPDATE cart_items SET quantity = ?
        WHERE cart_id = ? AND variant_id = ?
    """;

    // ================= DELETE =================
    private static final String DELETE_CART_ITEM_SQL = """
        DELETE FROM cart_items WHERE cart_item_id = ?
    """;

    private static final String DELETE_BY_CART_VARIANT_SQL = """
        DELETE FROM cart_items WHERE cart_id = ? AND variant_id = ?
    """;

    private static final String CLEAR_CART_SQL = """
        DELETE FROM cart_items WHERE cart_id = ?
    """;

    // ================= GET =================
    private static final String GET_CART_ITEMS_WITH_DETAILS = """
        SELECT 
            ci.cart_item_id,
            ci.cart_id,
            ci.variant_id,
            ci.quantity,
            p.product_id,
            p.product_name,
            p.brand,
            p.price,
            p.image_url,
            pv.size
        FROM cart_items ci
        JOIN product_variants pv ON ci.variant_id = pv.variant_id
        JOIN products p ON pv.product_id = p.product_id
        WHERE ci.cart_id = ?
        ORDER BY ci.cart_item_id DESC
    """;

    private static final String COUNT_ITEMS_SQL = """
        SELECT COALESCE(SUM(quantity),0) AS item_count
        FROM cart_items WHERE cart_id = ?
    """;

    private static final String GET_CART_TOTAL_SQL = """
        SELECT COALESCE(SUM(ci.quantity * p.price), 0) AS cart_total
        FROM cart_items ci
        JOIN product_variants pv ON ci.variant_id = pv.variant_id
        JOIN products p ON pv.product_id = p.product_id
        WHERE ci.cart_id = ?
    """;

    // ================= ADD =================
    @Override
    public boolean addCartItem(CartItem cartItem) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_CART_ITEM_SQL)) {

            ps.setInt(1, cartItem.getCartId());
            ps.setInt(2, cartItem.getVariantId());
            ps.setInt(3, cartItem.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= UPDATE =================
    @Override
    public boolean updateCartItemQuantity(int cartItemId, int quantity) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_CART_ITEM_SQL)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public CartItem getCartItemById(int cartItemId) {

        String sql = """
            SELECT * FROM cart_items
            WHERE cart_item_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartItemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CartItem item = new CartItem();
                    item.setCartItemId(rs.getInt("cart_item_id"));
                    item.setCartId(rs.getInt("cart_id"));
                    item.setVariantId(rs.getInt("variant_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    return item;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CartItem getCartItemByCartIdAndVariantId(int cartId, int variantId) {

        String sql = """
            SELECT * FROM cart_items
            WHERE cart_id = ? AND variant_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, variantId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CartItem item = new CartItem();
                    item.setCartItemId(rs.getInt("cart_item_id"));
                    item.setCartId(rs.getInt("cart_id"));
                    item.setVariantId(rs.getInt("variant_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    return item;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public boolean updateCartItemQuantityByCartAndVariant(int cartId, int variantId, int quantity) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_BY_CART_VARIANT_SQL)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.setInt(3, variantId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= DELETE =================
    @Override
    public boolean removeCartItem(int cartItemId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_CART_ITEM_SQL)) {

            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCartItemByCartAndVariant(int cartId, int variantId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_BY_CART_VARIANT_SQL)) {

            ps.setInt(1, cartId);
            ps.setInt(2, variantId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean clearCart(int cartId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(CLEAR_CART_SQL)) {

            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= GET LIST =================
    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        List<CartItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_CART_ITEMS_WITH_DETAILS)) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapWithDetails(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    @Override
    public List<CartItem> getCartItemsWithProductDetailsByCartId(int cartId) {

        List<CartItem> cartItems = new ArrayList<>();

        String sql = """
            SELECT 
                ci.cart_item_id,
                ci.cart_id,
                ci.variant_id,
                ci.quantity,
                p.product_id,
                p.product_name,
                p.brand,
                p.price,
                p.image_url,
                pv.size
            FROM cart_items ci
            JOIN product_variants pv ON ci.variant_id = pv.variant_id
            JOIN products p ON pv.product_id = p.product_id
            WHERE ci.cart_id = ?
            ORDER BY ci.cart_item_id DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                CartItem item = new CartItem();

                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setVariantId(rs.getInt("variant_id"));
                item.setQuantity(rs.getInt("quantity"));

                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setBrand(rs.getString("brand"));
                item.setPrice(rs.getBigDecimal("price"));
                item.setSize(rs.getString("size"));

                cartItems.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    // ================= COUNT =================
    @Override
    public int getCartItemCount(int cartId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(COUNT_ITEMS_SQL)) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("item_count");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ================= TOTAL =================
    @Override
    public BigDecimal getCartTotal(int cartId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_CART_TOTAL_SQL)) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("cart_total");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    // ================= MAPPER =================
    private CartItem mapWithDetails(ResultSet rs) throws Exception {
        CartItem item = new CartItem();

        item.setCartItemId(rs.getInt("cart_item_id"));
        item.setCartId(rs.getInt("cart_id"));
        item.setVariantId(rs.getInt("variant_id"));
        item.setQuantity(rs.getInt("quantity"));

        item.setProductId(rs.getInt("product_id"));
        item.setProductName(rs.getString("product_name"));
        item.setBrand(rs.getString("brand"));
        item.setPrice(rs.getBigDecimal("price"));
        item.setImageUrl(rs.getString("image_url"));
        item.setSize(rs.getString("size"));

        return item;
    }
}