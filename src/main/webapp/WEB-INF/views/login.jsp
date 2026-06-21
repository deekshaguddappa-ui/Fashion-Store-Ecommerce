<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

    <!-- CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>

<body>

<!-- NAVBAR -->
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="auth-page">

    <section class="auth-section">

        <div class="auth-card">

            <h2>Login</h2>

            <!-- ERROR MESSAGE -->
            <c:if test="${not empty errorMessage}">
                <p class="auth-error">
                    ${errorMessage}
                </p>
            </c:if>

            <!-- SUCCESS MESSAGE -->
            <c:if test="${not empty successMessage}">
                <p style="color: green; text-align:center;">
                    ${successMessage}
                </p>
            </c:if>

            <!-- FORM -->
            <form method="post" action="<c:url value='/login' />">

                <div class="form-row">
                    <input type="email"
                           name="email"
                           placeholder="Enter your email"
                           value="${email}"
                           required />
                </div>

                <div class="form-row">
                    <input type="password"
                           name="password"
                           placeholder="Enter your password"
                           required />
                </div>

                <button type="submit"
                        class="btn-primary auth-submit-btn">
                    Login
                </button>

            </form>

            <!-- FOOTER TEXT -->
            <div class="auth-footer-text">
                <p>
                    Don’t have an account?
                    <a href="<c:url value='/register' />">Register here</a>
                </p>
            </div>

        </div>

    </section>

</main>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>