<%@ tag description="Takes products from the collection and write HTML code to display all of them" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="products" required="true" type="java.util.Collection" %>

<div id="center">
    <h2><fmt:message key ="product.list"/></h2>
     <c:forEach items="${products}" var = "product">
         <page:displayProductBox product="${product}"/>
     </c:forEach>
</div>