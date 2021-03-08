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
    <title><fmt:message key="categories.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <br/>
            <c:if test="${createCategorySuccess}">
                <div class="alert alert-success" role="alert" id="successCreateCategory">
                    <fmt:message key="category.success_create"/>
                </div>
                <c:remove var="createCategorySuccess" scope="session"/>
            </c:if>
            <c:if test="${createCategoryError}">
                <div class="alert alert-danger" role="alert" id="errorCreateCategory">
                    <fmt:message key="category.error_create"/>
                </div>
                <c:remove var="createCategoryError" scope="session"/>
            </c:if>
            <c:if test="${categoryNotExist}">
                <div class="alert alert-danger" role="alert" id="categoryNotExist">
                    <fmt:message key="category.not_exist"/>
                </div>
            </c:if>
            <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                <form id="createCategoryForm" class="form-inline my-3 my-lg-3" method="post"
                      action="${pageContext.request.contextPath}/controller" novalidate>
                    <input type="hidden" name="command" value="create_category">
                    <div class="row">
                        <div class="col">
                            <div class="form-group mx-sm-3 mb-2">
                                <label for="createCategory" class="sr-only"><fmt:message
                                        key="categories.label"/></label>
                                <input type="text" class="form-control" name="categoryName" id="createCategory" required
                                       pattern="[\-\s\w]+" placeholder="<fmt:message key="categories.create.placeholder"/>" size="50">
                            </div>
                        </div>
                        <div class="col">
                            <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                                <span><i class="fas fa-plus"></i> <fmt:message key="admin.button.create"/></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <div style="display: flex;justify-content: center">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="categories.table.head.number"/></th>
                        <th scope="col"><fmt:message key="categories.table.head.name"/></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="i" value="1"/>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <th scope="row"><c:out value="${i}"/></th>
                            <td><c:out value="${category.name}"/></td>
                            <td>
                                <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                                    <form action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="edit_category">
                                        <input type="hidden" name="categoryId"
                                               value="<c:out value="${category.id}"/>">
                                        <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                                            <span><i class="fas fa-edit"></i> <fmt:message
                                                    key="categories.button.edit"/></span>
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin/categories.js"></script>
</body>
</html>
