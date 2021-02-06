<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${not empty users}">
    <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
        <form class="form-inline my-3 my-lg-3" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="to_register_page_command">
            <button class="btn btn-outline-primary mx-2 my-2 my-sm-0" type="submit">
                <span><i class="fas fa-plus"></i> Create</span>
            </button>
        </form>
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
                    <td>${user.statusType}</td>
                    <td>
                        <button style="padding-top: 0px; padding-bottom: 0px" type="button" class="btn btn-link">
                            <span><i class="far fa-edit"></i> Edit</span></button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
