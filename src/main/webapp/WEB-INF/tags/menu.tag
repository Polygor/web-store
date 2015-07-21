<%@ tag description="Writes the HTML code for inserting a tab menu." pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="templatemo_menu" class="ddsmoothmenu">
    <ul>
        <li><a href="<c:url value="/catalog"/>" class="selected"> <fmt:message key = "menu.label.home"/></a></li>
        <c:if test="${empty sessionScope.user}">
        <li><a href="<c:url value="/login"/>" class="selected"><fmt:message key ="menu.label.login"/></a></li>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
        <li><a href="<c:url value="/user"/>" class="selected"><fmt:message key ="menu.label.profile"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role == 'ADMINISTRATOR'}">
            <li><a href="<c:url value="/admin"/>" class="selected"><fmt:message key ="menu.label.adminPanel"/></a></li>
    </c:if>
        <br style="clear: left"/>
</ul>
    </div>