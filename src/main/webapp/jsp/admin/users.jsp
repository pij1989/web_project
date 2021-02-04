<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <title>Users page</title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<c:if test="${not empty users}">
    <div style="padding-top: 15px;display: flex;justify-content: center">
        <table class="table">
            <thead class="thead-light">
            <tr>
                <th scope="col">Number</th>
                <th scope="col">First name</th>
                <th scope="col">Last name</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Role</th>
                <th scope="col">Status</th>
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
                    <td>${user.statusType}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
<c:import url="../fragment/bootstrap_script.jsp"/>
</body>
</html>
