<%@ tag description="Writes the HTML code for inserting a tab menu." pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="user" type="com.epam.polygor.webstore.model.User" %>
<div id="templatemo_wrapper">
    <div id="templatemo_header">
        <div id="site_title"><h1><a href=<c:url value="/catalog"/>>Polygor's Shop</a></h1></div>

        <div id="header_right">
            <div align="right">
                <c:if test="${sessionScope.user.role == 'ADMINISTRATOR'}">
                    <li class="divider"></li>
                    <li><a href="<c:url value="/admin"/>" class="nav"><fmt:message key="menu.label.adminPanel"/></a></li>
                </c:if>
                <c:choose>
    <c:when test="${empty user}">
        <label><fmt:message key="main.label.notAuthorized"/></label><br>
                <a href="<c:url value="/login"/>" class=""><fmt:message key="main.label.login"/></a>
                <a href="<c:url value="/registration"/>" class="c"> <fmt:message key="main.label.register"/></a>
    </c:when>
    <c:otherwise>
        <label><fmt:message key="main.label.welcome"/>, ${user.login}!</label><br>
        <a href="<c:url value="/logout"/>"><fmt:message key="main.label.logout"/></a>
    </c:otherwise>
</c:choose>
    <ul id="language">
                <fmt:message key="main.label.language"/>:
                <a href="<c:url value="/changeLang?lang=en"/>"><img src="<c:url value="/static/images/usa.png"/>"/></a>
                <a href="<c:url value="/changeLang?lang=ru"/>"><img src="<c:url value="/static/images/russia.png"/>"/></a>
            </ul>
         </div>
        <!-- END -->
    </div>
    </div>
    </div>
    <!-- END of header -->