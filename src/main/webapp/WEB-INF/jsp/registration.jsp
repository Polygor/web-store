<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <page:genericPage/>
    <title><fmt:message key="title.registration"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>"/>
</head>
<body>
<div align="center" class="registration_box">
    <h1><fmt:message key="registration.label.header"/></h1>
    <hr>
    <fmt:message key="registration.placeholder.firstName" var="firstNamePlaceholder"/>
    <fmt:message key="registration.placeholder.lastName" var="lastNamePlaceholder"/>
    <fmt:message key="registration.placeholder.login" var="loginPlaceholder"/>
    <fmt:message key="registration.placeholder.email" var="emailPlaceholder"/>
    <fmt:message key="registration.placeholder.password" var="passwordPlaceholder"/>
    <fmt:message key="registration.placeholder.confirmPassword" var="confirmPasswordPlaceholder"/>
    <fmt:message key="registration.date.dateOfBirth" var="dateOfBirth"/>
    <fmt:message key="registration.placeholder.city" var="cityPlaceholder"/>
    <fmt:message key="registration.select.country" var="countrySelect"/>
    <fmt:message key="registration.placeholder.address" var="addressPlaceholder"/>
    <fmt:message key="registration.placeholder.postcode" var="postcodePlaceholder"/>
    <fmt:message key="registration.placeholder.phone" var="phonePlaceholder"/>
    <fmt:message key="registration.country.choose" var="chooseCountry"/>
    <fmt:message key="registration.country.kazakhstan" var="Kazakhstan"/>
    <fmt:message key="registration.country.russia" var="Russia"/>
    <fmt:message key="registration.country.belarus" var="Belarus"/>
    <fmt:message key="registration.country.usa" var="USA"/>
    <fmt:message key="registration.country.germany" var="Germany"/>
    <fmt:message key="registration.country.china" var="China"/>
    <form action="<c:url value="/registration"/>" method="post">
       <td>${firstNamePlaceholder}</td>
        <input type="text" name="firstName" maxlength="30" placeholder="${firstNamePlaceholder}" value="<c:out value="${requestScope.firstName}"/>"  required="true"/>
        <td>${lastNamePlaceholder}</td>
        <input type="text" name="lastName" maxlength="30"placeholder="${lastNamePlaceholder}" value="<c:out value="${requestScope.firstName}"/>"  required="true"/>
        <td>${loginPlaceholder}</td>
        <input type="text" name="login" maxlength="30" placeholder="${loginPlaceholder}" value="<c:out value="${requestScope.login}"/>"
               required="true"/>
        <td>${emailPlaceholder}</td>
        <input type="text" name="email" maxlength="30" placeholder="${emailPlaceholder}" value="<c:out value="${requestScope.email}"/>"
               required="true"/>
        <input type="password" name="password" maxlength="30" placeholder="${passwordPlaceholder}" required="true"/>
        <td> ${confirmPasswordPlaceholder}</td>
        <input type="password" name="confirmPassword" maxlength="30" placeholder="${confirmPasswordPlaceholder}" required="true"/>
      <td> ${cityPlaceholder}</td>
        <input type="text" name="city" maxlength="30" placeholder="${cityPlaceholder}"  value="<c:out value="${requestScope.city} "/>" required="true"/>
       <td> ${addressPlaceholder}</td>
        <input type="text" name="address" maxlength="30" placeholder="${addressPlaceholder}" value="<c:out value="${requestScope.address} "/>" required="true"/>
        <td>${postcodePlaceholder}</td>
        <input type="text" name="postcode" maxlength="6" placeholder="${postcodePlaceholder}" value="<c:out value="${requestScope.postcode} "/>" required="true"/>
        <td>${phonePlaceholder}</td>
        <input type="text" name="phone" maxlength="12" placeholder="${phonePlaceholder}" value="<c:out value="${requestScope.phone} "/>" required="true"/>
        <tr>
            <td>${dateOfBirth}</td>
            <td>
                <input type="date" name="birth" id="birth" class="fld" value=""/>
            </td>
        </tr>
        <tr>
            <td>${countrySelect}</td>
            <td>
                <select id="country" name="country">
                    <option value="-1">${chooseCountry}</option>
                    <option value="Kazakhstan">${Kazakhstan}</option>
                    <option value="Russia">${Russia}</option>
                    <option value="Belarus">${Belarus}</option>
                    <option value="USA">${USA}</option>
                    <option value="Germany">${Germany}</option>
                    <option value="Chine">${China}</option>
                </select>
            </td>
        </tr>
        <span><button type="submit" class="button">
            <fmt:message key="registration.button.submit"/>
        </button></span>
    </form>
    <c:forEach items="${errors}" var="error">
        <br/>
        <h4><span class="error_message">${error}</span></h4>
    </c:forEach>
</div>
</body>
</html>