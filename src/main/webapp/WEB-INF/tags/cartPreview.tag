<%@ tag description="Writes HTML code to display cart's info box" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="cart" type="com.epam.polygor.webstore.model.Cart" %>

<div class="shopping_cart">
    <div class="title_box"><fmt:message key="cart-preview.label.cart"/></div>
    <div class="cart_details">
        ${cart.productAmount} <fmt:message key="cart-preview.label.items"/><br/>
        <span class="border_cart"></span>
        <fmt:message key="cart-preview.label.total"/>
        <span class="value"><page:price price="${cart.totalPrice}"/></span>
    </div>
    <div class="cart_icon">
        <a href="<c:url value="/cart"/>">
            <img src="<c:url value="/static/images/cart.png"/>" alt="" width="35" height="35" border="0"/>
        </a>
    </div>
</div>