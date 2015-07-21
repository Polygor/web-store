<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="orderList" required="true" type="java.util.Collection" %>

<div class="center_text">
    <table>
        <c:forEach items="${orderList}" var="order">
            <tr>
                <th><page:date timestamp="${order.date}"/></th>
                <th><fmt:message key="order-list.label.totalPrice"/>: <page:price price="${order.totalPrice}"/></th>
                <th><fmt:message key="order-list.label.status"/></th>
            </tr>
            <c:forEach items="${order.productList}" var="product">
                <tr>
                    <td width="550">${product.name}</td>
                    <td><page:price price="${order.price}"/></td>
                    <td><fmt:message key="status.${order.status.name}"/></td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>