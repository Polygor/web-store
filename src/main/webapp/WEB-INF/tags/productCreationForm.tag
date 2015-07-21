<%@tag description="displays product creation form" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="page" tagdir="/WEB-INF/tags" %>

<fmt:message key="product-creation.label.selectCategory" var="selectCategoryLabel"/>
<fmt:message key="product-creation.label.name" var="nameLabel"/>
<fmt:message key="product-creation.label.manufacturer" var="manufacturerLabel"/>
<fmt:message key="product-creation.label.price" var="priceLabel"/>
<fmt:message key="product-creation.label.description" var="descriptionLabel"/>

<div class="creation_form">
    <div align="center">
        <page:findAndDisplayMessage/>
        <page:displayErrors errors="${errors}"/>
    </div>
    <form id="productCreation" action="<c:url value="product-creation/create"/>" method="POST"
          enctype="multipart/form-data">
        <fieldset>
            <legend><fmt:message key="admin.label.header"/></legend>
            <page:selectCategoriesMenu formName="productCreation" paramName="categoryName"
                                       label="${selectCategoryLabel}:"/>
            <page:inputTextField label="${nameLabel}:" inputName="productName" value="${productName}"
                                 formName="productCreation"/>
            <page:inputTextField label="${manufacturerLabel}:" inputName="productManufacturer" value="${productManufacturer}"
                                 formName="productCreation"/>
            <page:inputTextField label="${priceLabel}:" inputName="price" value="${price}"
                                 formName="productCreation"/>$

            <page:inputTextArea label="${descriptionLabel}:" inputName="description" value="${description}"
                                formName="productCreation" maxLength="1024"/>

            <br/>
            <label class="input_field"> <fmt:message key="product-creation.label.image"/>:
                <input type="file" name="image"/>
            </label>
            <br/>
            <div class="center_text" style="padding-top: 20px">
                <button type="submit" form="productCreation" class="base_button creation_product_button">
                    <fmt:message key="product-creation.button.create"/>
                </button>
            </div>
        </fieldset>
    </form>
</div>


