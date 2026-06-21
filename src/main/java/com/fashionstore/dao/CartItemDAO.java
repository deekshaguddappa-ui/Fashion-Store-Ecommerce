package com.fashionstore.dao;

import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.model.CartItem;

public interface CartItemDAO {

    // ================= ADD =================
    boolean addCartItem(CartItem cartItem);

    // ================= UPDATE =================
    boolean updateCartItemQuantity(int cartItemId, int quantity);

    boolean updateCartItemQuantityByCartAndVariant(int cartId, int variantId, int quantity);

    // ================= DELETE =================
    boolean removeCartItem(int cartItemId);

    boolean removeCartItemByCartAndVariant(int cartId, int variantId);

    boolean clearCart(int cartId);

    // ================= GET SINGLE =================
    CartItem getCartItemById(int cartItemId);

    CartItem getCartItemByCartIdAndVariantId(int cartId, int variantId);

    // ================= GET LIST =================
    List<CartItem> getCartItemsByCartId(int cartId);

    // ================= EXTRA =================
    int getCartItemCount(int cartId);
    List<CartItem> getCartItemsWithProductDetailsByCartId(int cartId);

    BigDecimal getCartTotal(int cartId);
}