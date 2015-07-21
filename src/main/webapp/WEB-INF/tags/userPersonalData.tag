<%@ tag description="Writes the HTML code for inserting a tab menu." %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="user" type="com.epam.polygor.webstore.model.User" %>

<h2><fmt:message key="profile.label.yourLogin"/>: ${user.login}</h2>

<h2><fmt:message key="profile.label.yourEmail"/>: ${user.email}</h2>

<h2><fmt:message key="profile.label.yourFirstName"/>: ${user.firstName}</h2>

<h2><fmt:message key="profile.label.yourLastName"/>: ${user.lastName}</h2>

<h2><fmt:message key="profile.label.yourCity"/>: ${user.city}</h2>

<h2><fmt:message key="profile.label.yourCountry"/>: ${user.country}</h2>

<h2><fmt:message key="profile.label.yourAddress"/>: ${user.address}</h2>

<h2><fmt:message key="profile.label.yourPostcode"/>: ${user.postcode}</h2>

<h2><fmt:message key="profile.label.yourPhone"/>: ${user.phone}</h2>


