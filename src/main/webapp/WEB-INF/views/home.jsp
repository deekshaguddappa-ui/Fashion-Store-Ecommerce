<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Fashion Store</title>

    <link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/css/home.css'/>">
</head>

<body>

<!-- NAVBAR -->
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main>

    <!-- ================= HERO SECTION ================= -->
    <section class="hero-section">
        <div class="container">
            <div class="hero-card">

                <div class="hero-content">
                    <span class="hero-badge">New Season Collection</span>

                    <h1>Discover Your Style with Fashion Store</h1>

                    <p>
                        Explore the latest trends in fashion for men, women, kids,
                        and find your perfect style with our curated collection.
                    </p>

                    <a href="<c:url value='/products'/>" class="btn-primary">
                        Shop Now
                    </a>
                </div>

                <div class="hero-image">
                    <div class="hero-image-box">
                        Trendy Fashion Collection
                    </div>
                </div>

            </div>
        </div>
    </section>


    <!-- ================= CATEGORY SECTION ================= -->
    <section class="categories-section">
        <div class="container">

            <div class="section-header">
                <h2 class="section-title">Shop by Category</h2>
            </div>

            <c:choose>

                <c:when test="${not empty categories}">
                    <div class="category-grid">

                        <c:forEach var="category" items="${categories}">
                            <div class="category-card">

                                <h3>${category.categoryName}</h3>
                                <p>${category.description}</p>

                                <a href="<c:url value='/products?categoryId=${category.categoryId}'/>">
                                    View Products
                                </a>

                            </div>
                        </c:forEach>

                    </div>
                </c:when>

                <c:otherwise>
                    <div class="empty-state">
                        <p>No categories available right now.</p>
                    </div>
                </c:otherwise>

            </c:choose>

        </div>
    </section>


    <!-- ================= PRODUCTS SECTION ================= -->
    <section class="products-section">
        <div class="container">

            <div class="section-header">
                <h2 class="section-title">Latest Products</h2>

                <a href="<c:url value='/products'/>">View All</a>
            </div>

            <c:choose>

                <c:when test="${not empty latestProducts}">
                    <div class="product-grid">

                        <c:forEach var="product" items="${latestProducts}">
                            <div class="product-card">

                               <div class="product-image">

    <img src="<c:url value='/${product.imageUrl}'/>"
         alt="${product.productName}">

</div>

                                <div class="product-info">
                                    <h3>${product.productName}</h3>
                                    <p class="product-brand">${product.brand}</p>
                                    <p class="product-price">₹ ${product.price}</p>
                                </div>

                                <div class="product-actions">
                                    <a href="<c:url value='/product-details?productId=${product.productId}'/>">
                                        View Details
                                    </a>
                                </div>

                            </div>
                        </c:forEach>

                    </div>
                </c:when>

                <c:otherwise>
                    <div class="empty-state">
                        <p>No products available right now.</p>
                    </div>
                </c:otherwise>

            </c:choose>

        </div>
    </section>

</main>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>