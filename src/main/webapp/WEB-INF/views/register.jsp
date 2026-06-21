<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>

<body>

<!-- NAVBAR -->
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="container">

    <section class="auth-section">

        <div class="auth-card">

            <h2>Create Account</h2>

            <!-- ERROR MESSAGE -->
            <c:if test="${not empty errorMessage}">
                <p style="color:red; margin-bottom:10px;">
                    ${errorMessage}
                </p>
            </c:if>

            <!-- FORM -->
            <form method="post" action="<c:url value='/register' />">

                <div class="form-row">
                    <input type="text" name="fullName" placeholder="Full Name"
                           value="${fullName}" />
                </div>

                <div class="form-row">
                    <input type="email" name="email" placeholder="Email"
                           value="${email}" />
                </div>

                <div class="form-row">
                    <input type="text" name="phone" placeholder="Phone"
                           value="${phone}" />
                </div>

                <div class="form-row">
                    <input type="password" name="password" placeholder="Password" />
                </div>

                <div class="form-row">
                    <input type="text" name="addressLine1" placeholder="Address Line 1"
                           value="${addressLine1}" />
                </div>

                <div class="form-row">
                    <input type="text" name="addressLine2" placeholder="Address Line 2"
                           value="${addressLine2}" />
                </div>

                <div class="form-row">
                    <input type="text" name="city" placeholder="City"
                           value="${city}" />
                </div>

                <div class="form-row">
                    <input type="text" name="state" placeholder="State"
                           value="${state}" />
                </div>

                <div class="form-row">
                    <input type="text" name="pincode" placeholder="Pincode"
                           value="${pincode}" />
                </div>

                <div class="form-row">
                    <input type="text" name="country" placeholder="Country"
                           value="${country}" />
                </div>

                <button type="submit" class="btn-primary auth-submit-btn">
                    Register
                </button>

            </form>

            <!-- FOOTER -->
            <div class="auth-footer-text">
                <p>
                    Already have an account?
                    <a href="<c:url value='/login' />">Login here</a>
                </p>
            </div>

        </div>

    </section>

</main>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>