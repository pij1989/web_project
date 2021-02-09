<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <title><fmt:message key="product.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <br/>
            <c:if test="${createProduct eq 'Success'}">
                <div class="alert alert-success" role="alert" id="successCreateProduct">
                    <fmt:message key="product.successcreate"/>
                </div>
            </c:if>
            <c:if test="${createProduct eq 'Error'}">
                <div class="alert alert-danger" role="alert" id="errorCreateProduct">
                    <fmt:message key="product.errorcreate"/>
                </div>
            </c:if>
            <c:remove var="createProduct" scope="session"/>
            <br/>
            <div class="create-product-container">
                <div class="create-product-item-wrapper bg-light">
                    <h2><fmt:message key="product.message"/></h2>
                    <form id="createProductForm" class="needs-validation" method="post" novalidate
                          action="${pageContext.request.contextPath}/controller" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="create_product">
                        <div class="form-group">
                            <label for="productName"><fmt:message key="product.productname.label"/></label>
                            <input type="text" name="productName" value="" class="form-control" id="productName"
                                   placeholder="<fmt:message key="product.productname.placeholder"/>" required
                                   pattern="[\-\s\w]+"/>
                        </div>

                        <div class="form-group">
                            <label for="category"><fmt:message key="product.category.label"/></label>
                            <input type="text" name="category" value="" class="form-control" id="category"
                                   placeholder="<fmt:message key="product.category.placeholder"/>" required
                                   pattern="[\-\s\w]+"/>
                        </div>

                        <div class="form-group">
                            <label for="price"><fmt:message key="product.price.label"/></label>
                            <input type="text" name="price" value="" class="form-control" id="price"
                                   placeholder="<fmt:message key="product.price.placeholder"/>" required
                                   pattern="^[0-9]+.[0-9]+$"/>
                        </div>

                        <div class="form-group form-check">
                            <input type="checkbox" name="isActiveProduct" class="form-check-input" id="activeCheck">
                            <label class="form-check-label" for="activeCheck"><fmt:message
                                    key="product.activecheck.label"/></label>
                        </div>

                        <div class="form-group">
                            <label for="description"><fmt:message key="product.description.label"/></label>
                            <textarea class="form-control" name="description" id="description" rows="3"></textarea>
                        </div>

                        <div class="form-group">
                            <label for="uploadImage"><fmt:message key="product.uploadimage.label"/></label>
                            <input type="file" name="image" class="form-control-file" id="uploadImage">
                        </div>

                        <div class="create-product-item">
                            <button type="submit" id="submit" class="btn btn-primary"><fmt:message
                                    key="product.submit"/></button>
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
