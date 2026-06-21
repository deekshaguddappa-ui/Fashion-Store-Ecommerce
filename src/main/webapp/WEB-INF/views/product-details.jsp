<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>

    <link rel="stylesheet" href="<c:url value='/assets/css/style.css'/>">
    <link rel="stylesheet" href="<c:url value='/assets/css/product-details.css'/>">
</head>

<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<main class="container product-details">

    <section class="product-details-card">

        <!-- LEFT -->
       <div class="product-image-box">

    <img src="<c:url value='/${product.imageUrl}'/>"
         alt="${product.productName}">

</div>

        <!-- RIGHT -->
        <div class="product-info-box">

            <h1>${product.productName}</h1>
            <p class="brand">${product.brand}</p>
            <p class="price">₹ ${product.price}</p>

            <!-- ================= VARIANTS ================= -->
            <div class="variants">

                <h3>Select Size</h3>

                <c:if test="${not empty variants}">
                    <div class="variant-list">

                        <form id="addToCartForm"
                              action="<c:url value='/cart'/>"
                              method="post">

                            <input type="hidden" name="action" value="add"/>
                            <input type="hidden" name="variantId" id="selectedVariantId"/>
                            <input type="hidden" name="quantity" value="1"/>

                            <c:forEach var="v" items="${variants}">
                                <button type="button"
                                        class="variant-btn"
                                        onclick="selectVariant('${v.variantId}', this)">
                                    ${v.size}
                                </button>
                            </c:forEach>

                        </form>

                    </div>
                </c:if>

                <c:if test="${empty variants}">
                    <p class="stock-text">No sizes available.</p>
                </c:if>

            </div>

            <!-- INFO -->
            <div class="stock-info">
                <p class="stock-text">Select a size to continue.</p>
            </div>

            <!-- ACTIONS -->
            <div class="product-actions">

                <button type="button" class="btn-primary" onclick="addToCart()">
                    Add to Cart
                </button>

                <a href="<c:url value='/products'/>" class="btn-secondary">
                    Back to Products
                </a>

            </div>

        </div>

    </section>

</main>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

<script>
    function selectVariant(id, btn) {
        document.getElementById("selectedVariantId").value = id;

        document.querySelectorAll(".variant-btn").forEach(function(b) {
            b.classList.remove("active");
        });

        btn.classList.add("active");
    }

    function addToCart() {
        const variantId = document.getElementById("selectedVariantId").value;

        if (!variantId) {
            alert("Please select a size first!");
            return;
        }

        document.getElementById("addToCartForm").submit();
    }
</script>

</body>
</html>