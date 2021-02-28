<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom_tag" prefix="ctg" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <title>Edit category</title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <br/>
            <c:if test="${updateCategorySuccess}">
                <div class="alert alert-success" role="alert" id="successUpdateCategory">
                    <fmt:message key="category.successupdate"/>
                </div>
                <c:remove var="updateCategorySuccess" scope="session"/>
            </c:if>
            <c:if test="${updateCategoryError}">
                <div class="alert alert-danger" role="alert" id="errorUpdateCategory">
                    <fmt:message key="category.errorupdate"/>
                </div>
                <c:remove var="updateCategoryError" scope="session"/>
            </c:if>
            <br/>
            <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                <form id="updateCategoryForm" class="form-inline my-3 my-lg-3" method="post"
                      action="${pageContext.request.contextPath}/controller" novalidate>
                    <input type="hidden" name="command" value="update_category">
                    <input type="hidden" name="categoryId" value="<c:out value="${category.id}"/>">
                    <div class="row">
                        <div class="col">
                            <div class="form-group mx-sm-3 mb-2">
                                <label for="updateCategory" class="sr-only">Create category</label>
                                <input type="text" class="form-control" name="categoryName"
                                       value="<c:out value="${category.name}"/>" id="updateCategory" required
                                       pattern="[\-\s\w]+" placeholder="Update category" size="50">
                            </div>
                        </div>
                        <div class="col">
                            <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                                <span> Update</span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div style="display: flex;justify-content: center">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Number</th>
                        <th scope="col">Image</th>
                        <th scope="col">Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Description</th>
                        <th scope="col">Creating time</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="i" value="1"/>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <th scope="row"><c:out value="${i}"/></th>
                            <td style="width: 120px"><img
                                    src="data:image/jpg;base64,<ctg:encodeBytes bytes="${product.image}"/>"
                                    style="width: 50%"/></td>
                            <td><c:out value="${product.name}"/></td>
                            <td><c:out value="${product.price}"/></td>
                            <td><c:out value="${product.amount}"/></td>
                            <td><c:out value="${product.description}"/></td>
                            <td><c:out value="${product.creatingTime}"/></td>
                        </tr>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                    <tr>
                        <td colspan="6">
                            <form method="post" action="${pageContext.request.contextPath}/controller"
                                  style="text-align: center">
                                <input type="hidden" name="command" value="delete_category">
                                <input type="hidden" name="categoryId" value="<c:out value="${category.id}"/>">
                                <button class="btn btn-outline-danger mx-2 my-2 my-sm-0" type="submit">
                                    <span><i class="fas fa-trash"></i> Delete</span>
                                </button>
                            </form>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin/edit_category.js"></script>
</body>
</html>
