<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Cart</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">
</head>

<body>

<!-- NAVBAR -->
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="container">

    <section class="cart-page">

        <h2 class="section-title">My Cart</h2>

        <!-- SUCCESS MESSAGE -->
        <c:if test="${param.success == 'added'}">
            <div class="cart-message success">Item added to cart</div>
        </c:if>

        <c:if test="${param.success == 'updated'}">
            <div class="cart-message success">Cart updated</div>
        </c:if>

        <c:if test="${param.success == 'removed'}">
            <div class="cart-message success">Item removed</div>
        </c:if>

        <!-- ERROR MESSAGE -->
        <c:if test="${param.error != null}">
            <div class="cart-message error">Something went wrong</div>
        </c:if>

        <!-- ================= CART ITEMS ================= -->
        <c:choose>

            <c:when test="${not empty cartItems}">

                <div class="cart-container">

                    <!-- LEFT SIDE -->
                    <div class="cart-items">

                        <c:forEach var="item" items="${cartItems}">

                            <div class="cart-item-card">

                                <!-- IMAGE -->
                                <div class="cart-item-image">
                                    ${item.productName}
                                </div>

                                <!-- DETAILS -->
                                <div class="cart-item-details">

                                    <h3>${item.productName}</h3>
                                    <p>${item.brand}</p>
                                    <p>Size: ${item.size}</p>
                                    <p>₹ ${item.price}</p>

                                    <!-- UPDATE FORM -->
                                    <form action="${pageContext.request.contextPath}/cart" method="post" class="cart-form">

                                        <input type="hidden" name="action" value="update"/>
                                        <input type="hidden" name="cartItemId" value="${item.cartItemId}"/>

                                        <input type="number" name="quantity" value="${item.quantity}" min="1"/>

                                        <button type="submit">Update</button>

                                    </form>

                                    <!-- REMOVE BUTTON -->
                                    <form action="${pageContext.request.contextPath}/cart" method="post">

                                        <input type="hidden" name="action" value="remove"/>
                                        <input type="hidden" name="cartItemId" value="${item.cartItemId}"/>

                                        <button type="submit" class="remove-btn">Remove</button>

                                    </form>

                                </div>

                                <!-- SUBTOTAL -->
                                <div class="cart-item-price">
                                    ₹ ${item.subtotal}
                                </div>

                            </div>

                        </c:forEach>

                    </div>

                    <!-- RIGHT SIDE -->
                    <aside class="cart-summary">

                        <h3>Order Summary</h3>

                        <p>Total: <strong>₹ ${cartTotal}</strong></p>

                      <a href="${pageContext.request.contextPath}/checkout" class="btn-primary">
    Checkout
</a>

                    </aside>

                </div>

            </c:when>

          
            <c:otherwise>

                <div class="empty-state">
                    <p>Your cart is empty</p>

                    <a href="${pageContext.request.contextPath}/products" class="btn-primary">
                        Continue Shopping
                    </a>
                </div>

            </c:otherwise>

        </c:choose>

    </section>

</main>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>