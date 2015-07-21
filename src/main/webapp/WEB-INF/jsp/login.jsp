<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<page:genericPage/>
<html>
<head>
    <title><fmt:message key="title.login"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>"/>
</head>
<body>
<div align="center" class="registration_box">
    <h1><fmt:message key="login.label.header"/></h1>

    <fmt:message key="login.placeholder.login" var="loginPlaceholder"/>
    <fmt:message key="login.placeholder.password" var="passwordPlaceholder"/>
    <form action="<c:url value="/login"/>" method="post">
        <hr>
        <input type="text" name="login" placeholder="${loginPlaceholder}" value="<c:out value="${login}"/>"
               required/>
        <input type="password" name="password" placeholder="${passwordPlaceholder}" required/>
        <button type="submit" class="button"><fmt:message key="login.button.submit"/></button>
    </form>
    <a href="<c:url value="/registration"/>"><fmt:message key="login.label.register"/></a>

    <c:if test="${errorMessage != null}">
        <h4><span style="color: red; ">${errorMessage}</span></h4>
    </c:if>
</div>
</body>
</html>