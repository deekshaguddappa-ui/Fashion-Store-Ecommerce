<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/checkout.css">
</head>

<body>

<!-- NAVBAR -->
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="container checkout-page">

    <h2 class="section-title">Checkout</h2>
   

    <div class="checkout-container">

        <!-- ================= LEFT SIDE (ADDRESS + PAYMENT) ================= -->
        <div class="checkout-form">

            <form action="${pageContext.request.contextPath}/place-order" method="post">

                <!-- ================= DELIVERY ADDRESS ================= -->
                <h3>Delivery Address</h3>

                <div class="form-group">
                    <label>Name</label>
                    <input type="text" name="name" value="${user.fullName}" required>
                </div>

                <div class="form-group">
                    <label>Phone</label>
                   <input type="text" name="phone" value="${user.phone}" required>
                </div>

                <div class="form-group">
                    <label>Address</label>
                    <input type="text" name="address" required>
                </div>

                <div class="form-group">
                    <label>City</label>
                    <input type="text" name="city" value="${user.city}" required>
                </div>

                <div class="form-group">
                    <label>Pincode</label>
                    <input type="text" name="pincode" value="${user.pincode}" required>
                </div>

                <!-- ================= PAYMENT ================= -->
                <h3>Payment Method</h3>

                <label class="payment-option">
                    <input type="radio" name="paymentMethod" value="COD" checked>
                    <span>Cash on Delivery</span>
                </label>

                <label class="payment-option">
                    <input type="radio" name="paymentMethod" value="CARD">
                    <span>Card</span>
                </label>

                <label class="payment-option">
                    <input type="radio" name="paymentMethod" value="UPI">
                    <span>UPI</span>
                </label>

                <button type="submit" class="btn-primary place-order-btn">
                    Place Order
                </button>

            </form>

        </div>

        <!-- ================= RIGHT SIDE (SUMMARY) ================= -->
        <aside class="checkout-summary">

            <h3>Order Summary</h3>

            <c:forEach var="item" items="${cartItems}">
                <div class="summary-item">
                    <span>${item.productName} (${item.size})</span>
                    <span>₹ ${item.subtotal}</span>
                </div>
            </c:forEach>

            <div class="summary-total">
                <span>Total</span>
                <strong>₹ ${cartTotal}</strong>
            </div>

        </aside>

    </div>

</main>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>