<%@ tag description="Writes the HTML code for adding to cart button" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="product" type="com.epam.polygor.webstore.model.Product" %>

<div id="content">
    <page:productDetails product="${product}"/>
</div>