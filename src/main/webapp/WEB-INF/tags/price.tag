<%@ tag description="Writes the HTML code for inserting a product view." pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="price" %>
<%@ attribute name="product" type="com.epam.polygor.webstore.model.Product" %>
<span class="price">
    <fmt:formatNumber type="currency" value="${price}" currencySymbol="$"/>
</span>