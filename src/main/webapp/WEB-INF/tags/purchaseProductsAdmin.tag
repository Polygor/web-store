<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="orderList" required="true" type="java.util.Collection" %>
<%@ attribute name="user" required="true" type="com.epam.polygor.webstore.model.User" %>

<div class="center_text">
    <table>
        <tr>
            <th colspan="4">
                <fmt:message key="order-list.label.productListBelongsToUser"/> ${user.login} (${user.email})
            </th>
        </tr>
    </table>
    <c:forEach items="${orderList}" var="order" varStatus="orderLoopStatus">
        <form id="changingStatusForm${orderLoopStatus.count}" method="POST"
              action="<c:url value="/admin/changeOrderStatus"/>">
            <input type="hidden" value="${order.user.id}" name="userID"/>
                <%--for displaying success message--%>
            <input type="hidden" value="${orderLoopStatus.count}" name="orderNumber"/>
            <table align="right">
                <tr>
                    <th><page:date timestamp="${order.date}"/></th>
                    <th><fmt:message key="product-list.label.totalPrice"/>: <page:price value="${order.totalPrice}"/></th>
                    <th><fmt:message key="product-list.label.status"/></th>
                </tr>
                <c:forEach items="${order.productList}" var="product">
                    <tr>
                        <td width="550">${product.name}</td>
                        <td><page:price price="${order.price}"/></td>
                        <td>
                            <input form="changingStatusForm${orderLoopStatus.count}" type="hidden"
                                   value="${order.id}"
                                   name="orderID"/>
                            <page:selectStatusMenu formName="changingStatusForm${orderLoopStatus.count}"
                                                   defaultValue="${order.status.name}"/>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <th colspan="4">
                        <button type="submit" form="changingStatusForm${orderLoopStatus.count}" class="base_button">
                            <fmt:message key="order-list.button.confirmChanges"/>
                        </button>
                        <c:set var="currentPurchaseNumber">${orderLoopStatus.count}</c:set>
                        <c:if test="${not empty requestScope[currentPurchaseNumber]}">
                            <fmt:message var="successChangedMessage" key="order-list.button.successChanged"/>
                            ${successChangedMessage}
                        </c:if>
                    </th>
                </tr>
            </table>
        </form>
    </c:forEach>
</div>