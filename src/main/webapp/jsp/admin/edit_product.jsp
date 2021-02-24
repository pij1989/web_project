<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <title><fmt:message key="editproduct.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <br/>
            <c:if test="${updateProductSuccess}">
                <div class="alert alert-success" role="alert" id="successUpdateProduct">
                    <fmt:message key="editproduct.successupdate"/>
                </div>
                <c:remove var="updateProductSuccess" scope="session"/>
            </c:if>
            <c:if test="${updateProductError}">
                <div class="alert alert-danger" role="alert" id="errorUpdateProduct">
                    <fmt:message key="editproduct.errorupdate"/>
                </div>
                <c:remove var="updateProductError" scope="session"/>
            </c:if>
            <br/>
            <div class="create-product-container">
                <div class="create-product-item-wrapper bg-light">
                    <h2><fmt:message key="editproduct.message"/></h2>
                    <form id="createProductForm"
                          class="needs-validation <c:if test="${not empty productForm}">was-validated</c:if>"
                          method="post" novalidate
                          action="${pageContext.request.contextPath}/controller" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="update_product">
                        <div class="form-group">
                            <label for="productName"><fmt:message key="product.productname.label"/></label>
                            <input type="text" name="productName" value="<c:out value="${product.name}"/>"
                                   class="form-control" id="productName"
                                   placeholder="<fmt:message key="product.productname.placeholder"/>" required
                                   pattern="[\-\s\w]+"/>
                        </div>

                        <div class="form-group">
                            <label for="category"><fmt:message key="product.category.label"/></label>
                            <select name="category" class="form-control" id="category">
                                <c:forEach var="category" items="${categories}">
                                    <option value="<c:out value="${category.id}"/>"
                                            <c:if test="${product.categoryId eq category.id}">selected</c:if>>
                                        <c:out value="${category.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="price"><fmt:message key="product.price.label"/></label>
                            <input type="text" name="price" value="<c:out value="${product.price}"/>"
                                   class="form-control" id="price"
                                   placeholder="<fmt:message key="product.price.placeholder"/>" required
                                   pattern="^[0-9]+\.[0-9]+$"
                            />
                        </div>

                        <div class="form-group form-check">
                            <input type="checkbox" name="isActiveProduct" value="true" class="form-check-input"
                                   id="activeCheck"
                                   <c:if test="${product.status}">checked</c:if> />
                            <label class="form-check-label" for="activeCheck"><fmt:message
                                    key="product.activecheck.label"/></label>
                        </div>

                        <div class="form-group">
                            <label for="description"><fmt:message key="product.description.label"/></label>
                            <textarea class="form-control" name="description" id="description" rows="3" required><c:out
                                    value="${product.description}"/></textarea>
                        </div>

                        <div class="form-group">
                            <label for="creatingTime"><fmt:message key="product.createdate.label"/></label>
                            <input type="datetime-local" id="creatingTime" name="creatingTime"
                                   value="<c:out value="${product.creatingTime}"/>" required>
                        </div>

                        <div class="form-group">
                            <label for="uploadImage"><fmt:message key="product.uploadimage.label"/></label>
                            <input type="file" name="image" class="form-control-file" id="uploadImage">
                        </div>
                        <%--                        <c:remove var="productForm" scope="session"/>--%>

                        <div class="create-product-item">
                            <button type="submit" id="submit" class="btn btn-primary">
                                <fmt:message key="editproduct.submit"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin/create_product.js"></script>
</body>
</html>
