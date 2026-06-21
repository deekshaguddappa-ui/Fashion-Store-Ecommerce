package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;

import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT_SQL =
        "INSERT INTO order_items (order_id, variant_id, quantity, price) VALUES (?, ?, ?, ?)";

    private static final String GET_BY_ID_SQL =
        "SELECT * FROM order_items WHERE order_item_id=?";

    private static final String GET_BY_ORDER_SQL =
        "SELECT * FROM order_items WHERE order_id=?";

    private static final String DELETE_SQL =
        "DELETE FROM order_items WHERE order_item_id=?";

    private static final String DELETE_BY_ORDER_SQL =
        "DELETE FROM order_items WHERE order_id=?";

    @Override
    public boolean addOrderItem(OrderItem oi) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, oi.getOrderId());
            ps.setInt(2, oi.getVariantId());
            ps.setInt(3, oi.getQuantity());
            ps.setBigDecimal(4, oi.getPrice());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addOrderItems(List<OrderItem> items) {
        for (OrderItem oi : items) {
            addOrderItem(oi);
        }
        return true;
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID_SQL)) {

            ps.setInt(1, orderItemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ORDER_SQL)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteOrderItem(int orderItemId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, orderItemId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteOrderItemsByOrderId(int orderId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_BY_ORDER_SQL)) {

            ps.setInt(1, orderId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private OrderItem map(ResultSet rs) throws Exception {
        OrderItem oi = new OrderItem();

        oi.setOrderItemId(rs.getInt("order_item_id"));
        oi.setOrderId(rs.getInt("order_id"));
        oi.setVariantId(rs.getInt("variant_id"));
        oi.setQuantity(rs.getInt("quantity"));
        oi.setPrice(rs.getBigDecimal("price"));

        return oi;
    }
}