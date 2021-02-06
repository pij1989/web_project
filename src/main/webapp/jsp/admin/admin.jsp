<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <title>Admin page</title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <c:choose>
                <c:when test="${not empty users}">
                    <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                        <form class="form-inline my-3 my-lg-3" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="to_register_page_command">
                            <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                                <span><i class="fas fa-plus"></i> Create</span>
                            </button>
                        </form>
                    </div>
                    <div class="alert alert-success" id="successChangeStatus" hidden role="alert">
                        Статус пользователя изменен
                    </div>
                    <div style="display: flex;justify-content: center">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Number</th>
                                <th scope="col">First name</th>
                                <th scope="col">Last name</th>
                                <th scope="col">Username</th>
                                <th scope="col">Email</th>
                                <th scope="col">Role</th>
                                <th scope="col">Status</th>
                                <th scope="col"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <th scope="row">${user.id}</th>
                                    <td>${user.firstName}</td>
                                    <td>${user.lastName}</td>
                                    <td>${user.username}</td>
                                    <td>${user.email}</td>
                                    <td>${user.roleType}</td>
                                    <td>
                                        <select name="status" class="form-control statusSelect" id="${user.id}"
                                                style="width: 143px;">
                                            <option
                                                    <c:if test="${user.statusType eq 'ACTIVE'}">selected</c:if>
                                                    value="ACTIVE">
                                                ACTIVE
                                            </option>
                                            <option
                                                    <c:if test="${user.statusType eq 'BLOCKED'}">selected</c:if>
                                                    value="BLOCKED">
                                                BLOCKED
                                            </option>
                                            <option
                                                    <c:if test="${user.statusType eq 'WAIT_ACTIVE'}">selected</c:if>
                                                    value="WAIT_ACTIVE">
                                                WAIT_ACTIVE
                                            </option>
                                        </select>
                                    </td>
                                    <td>
                                        <button style="padding-top: 0px; padding-bottom: 0px" type="button"
                                                class="btn btn-link"><span><i class="far fa-edit"></i> Edit</span>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center;padding-top: 15px">
                        <h1>Welcome to admin page</h1>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin.js"></script>
</body>
</html>
