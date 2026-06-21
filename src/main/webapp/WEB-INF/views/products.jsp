<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Products</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/products.css">
</head>

<body>

<!-- NAVBAR -->
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="container">

    <!-- ================= FILTER SECTION ================= -->
    <section class="filters">

        <form method="get" action="<c:url value='/products' />">

            <!-- CATEGORY -->
            <select name="categoryId">
                <option value="">All Categories</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}"
                        <c:if test="${category.categoryId == selectedCategoryId}">
                            selected
                        </c:if>>
                        ${category.categoryName}
                    </option>
                </c:forEach>
            </select>

            <!-- SEARCH -->
            <input type="text" name="keyword"
                   value="${keyword}"
                   placeholder="Search products..." />

            <!-- PRICE -->
            <input type="number" name="minPrice"
                   value="${minPrice}"
                   placeholder="Min Price" />

            <input type="number" name="maxPrice"
                   value="${maxPrice}"
                   placeholder="Max Price" />

            <!-- SORT -->
            <select name="sortBy">
                <option value="">Sort By</option>

                <option value="priceAsc"
                    <c:if test="${sortBy == 'priceAsc'}">selected</c:if>>
                    Price Low → High
                </option>

                <option value="priceDesc"
                    <c:if test="${sortBy == 'priceDesc'}">selected</c:if>>
                    Price High → Low
                </option>

                <option value="latest"
                    <c:if test="${sortBy == 'latest'}">selected</c:if>>
                    Latest
                </option>
            </select>

            <button type="submit">Apply</button>

        </form>
    </section>

    <!-- ================= PRODUCTS SECTION ================= -->
    <section class="products-section">

        <c:choose>

            
            <c:when test="${not empty products}">

                <div class="product-grid">

                    <c:forEach var="product" items="${products}">

                        <div class="product-card">

                            <!-- IMAGE -->
                          <div class="product-image">

    <img src="<c:url value='/${product.imageUrl}'/>"
         alt="${product.productName}">

</div>
                            <!-- INFO -->
                            <div class="product-info">
                                <h3>${product.productName}</h3>
                                <p class="product-brand">${product.brand}</p>
                                <p class="product-price">₹ ${product.price}</p>

                                <!-- PRODUCT DETAILS URL -->
                                <c:url var="productDetailsUrl" value="/product-details">
                                    <c:param name="productId" value="${product.productId}" />
                                </c:url>

                                <div class="product-actions">
                                    <a href="${productDetailsUrl}">View Details</a>
                                </div>
                            </div>

                        </div>

                    </c:forEach>

                </div>

            </c:when>

            <c:otherwise>
                <div class="empty-state">
                    <p>No products found for the selected filters.</p>
                </div>
            </c:otherwise>

        </c:choose>

    </section>

</main>

<!-- FOOTER -->
<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

</body>
</html>