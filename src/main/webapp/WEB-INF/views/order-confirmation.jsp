<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order-confirmation.css">
</head>

<body>

<!-- NAVBAR -->
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="container order-confirmation-page">

    <section class="order-confirmation-section">

        <!-- ================= SUCCESS MESSAGE ================= -->
        <div class="confirmation-card">

            <h1>🎉 Order Placed Successfully!</h1>
            <p>Your order has been placed and will be delivered soon.</p>

            <p><strong>Order ID:</strong> ${order.orderId}</p>
            <p><strong>Payment Method:</strong> ${order.paymentMethod}</p>

        </div>

        <!-- ================= ORDER ITEMS ================= -->
        <div class="order-items">

            <h2>Your Items</h2>

            <c:choose>

                <c:when test="${not empty orderItems}">

                    <c:forEach var="item" items="${orderItems}">

                        <div class="order-item-card">

                            <div class="order-item-info">
                                <p><strong>Product ID:</strong> ${item.productId}</p>
                                <p><strong>Variant:</strong> ${item.variantId}</p>
                                <p><strong>Quantity:</strong> ${item.quantity}</p>
                            </div>

                            <div class="order-item-price">
                                ₹ ${item.price}
                            </div>

                        </div>

                    </c:forEach>

                </c:when>

                <c:otherwise>
                    <p>No order items found.</p>
                </c:otherwise>

            </c:choose>

        </div>

        <!-- ================= TOTAL ================= -->
        <div class="order-total">
            <h3>Total Amount: ₹ ${order.totalAmount}</h3>
        </div>

        <!-- ================= ACTIONS ================= -->
        <div class="confirmation-actions">

            <a href="${pageContext.request.contextPath}/products" class="btn-primary">
                Continue Shopping
            </a>

            <a href="${pageContext.request.contextPath}/home" class="btn-secondary">
                Go to Home
            </a>

        </div>

    </section>

</main>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>