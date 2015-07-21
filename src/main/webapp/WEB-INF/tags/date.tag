<%@ tag description="Writes the HTML code for inserting a date." pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>
<%@ attribute name="timestamp" required="true" type="java.sql.Timestamp"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<jsp:setProperty name="dateValue" property="time" value="${timestamp.time}"/>
<span class="value">
    <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm:ss"/>
</span>