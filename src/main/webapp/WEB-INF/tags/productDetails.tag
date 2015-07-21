<%@ tag description="Writes the HTML code for inserting a product details view." %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="product" type="com.epam.polygor.webstore.model.Product" %>


<div class="center_content">
    <div class="center_title_bar">${product.name}</div>
    <div class="prod_box_big">
        <div class="center_prod_box_big">
            <div class="product_img_big"><img src="<c:out value="/image/${product.image.id}"/>"/></div>
            <div class="details_big_box">
                <div class="specifications"><fmt:message key ="product.manufacturer"/></div>
                <span>${product.manufacturer}</span>
                <div class = "specifications"><fmt:message key ="product.inStock"/></div>
                <span>${product.inStock}</span>
                <div class="product_title_big"><fmt:message key="product.description"/>:</div>
                <div class="specifications">
                    <span>${product.description}</span><br/>
                    <br/>
                    <fmt:message key="product.price"/>: <span class="blue"><page:price price="${product.price}"/></span><br/>
                </div>
                <page:addToCart product="${product}"/>
            </div>
        </div>
    </div>
</div>
