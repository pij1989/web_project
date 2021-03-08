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
    <title><fmt:message key="user.create.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <br/>
            <c:if test="${createUserSuccess}">
                <div class="alert alert-success" role="alert" id="successCreateUser">
                    <fmt:message key="user.success_create"/>
                </div>
                <c:remove var="createUserSuccess" scope="session"/>
            </c:if>
            <c:if test="${createUserError}">
                <div class="alert alert-danger" role="alert" id="errorCreateUser">
                    <fmt:message key="user.error_create"/>
                </div>
                <c:remove var="createUserError" scope="session"/>
            </c:if>
            <br/>
            <div class="create-container">
                <div class="create-item-wrapper bg-light">
                    <h2><fmt:message key="registration.message"/></h2>
                    <form id="createUserForm" class="needs-validation <c:if test="${not empty registrationForm}">was-validated</c:if>" action="${pageContext.request.contextPath}/controller" method="post" novalidate>
                        <input type="hidden" name="command" value="create_user">
                        <div class="form-group">
                            <label for="firstName"><fmt:message key="registration.first_name.label"/> </label>
                            <input type="text" name="firstName" value="<c:out value="${registrationForm['firstName']}"/>" class="form-control" id="firstName"
                                   placeholder="<fmt:message key="registration.first_name.placeholder"/>" required
                                   pattern="[a-zA-Zа-яА-Я]+"/>
                            <small id="firstNameHelp" class="form-text text-muted"><fmt:message key="registration.first_name.help"/></small>
                        </div>

                        <div class="form-group">
                            <label for="lastName"><fmt:message key="registration.last_name.label"/></label>
                            <input type="text" name="lastName" value="<c:out value="${registrationForm['lastName']}"/>" class="form-control" id="lastName"
                                   placeholder="<fmt:message key="registration.last_name.placeholder"/>" required
                                   pattern="[a-zA-Zа-яА-Я]+"/>
                            <small id="lastNameHelp" class="form-text text-muted"><fmt:message key="registration.last_name.help"/></small>
                        </div>

                        <div class="form-group">
                            <label for="username"><fmt:message key="registration.username.label"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text bg-white"><i class="fas fa-user"></i></span>
                                </div>
                                <input type="text" name="username" value="<c:out value="${registrationForm['username']}"/>" class="form-control" id="username"
                                       placeholder="<fmt:message key="registration.username.placeholder"/>" required
                                       pattern="\w+"/>
                            </div>
                            <small id="usernameHelp" class="form-text text-muted"><fmt:message key="registration.username.help"/></small>
                        </div>

                        <div class="form-group">
                            <label for="email"><fmt:message key="registration.email.label"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text bg-white"><i class="fas fa-envelope"></i></span>
                                </div>
                                <input type="email" name="email" value="<c:out value="${registrationForm['email']}"/>" class="form-control" id="email"
                                       placeholder="<fmt:message key="registration.email.placeholder"/>" aria-describedby="emailHelp" required
                                       pattern="^([a-z0-9_-]+\.)*[a-z0-9_-]+@[a-z0-9_-]+(\.[a-z0-9_-]+)*\.[a-z]{2,6}$"/>
                            </div>
                            <small id="emailHelp" class="form-text text-muted"><fmt:message key="registration.email.help"/></small>
                        </div>

                        <div class="form-group">
                            <label for="role"><fmt:message key="user.role.label"/></label>
                            <select name="role" class="form-control" id="role">
                                <option
                                        <c:if test="${registrationForm['role'] eq 'ADMIN'}">selected</c:if>
                                        value="ADMIN">
                                    <fmt:message key="user.role.admin"/>
                                </option>
                                <option
                                        <c:if test="${registrationForm['role'] eq 'USER'}">selected</c:if>
                                        value="USER">
                                    <fmt:message key="user.role.user"/>
                                </option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="status"><fmt:message key="user.status.label"/></label>
                            <select name="status" class="form-control" id="status">
                                <option
                                        <c:if test="${registrationForm['status'] eq 'ACTIVE'}">selected</c:if>
                                        value="ACTIVE">
                                    <fmt:message key="user.status.active"/>
                                </option>
                                <option
                                        <c:if test="${registrationForm['status'] eq 'BLOCKED'}">selected</c:if>
                                        value="BLOCKED">
                                    <fmt:message key="user.status.blocked"/>
                                </option>
                                <option
                                        <c:if test="${registrationForm['status']eq 'WAIT_ACTIVE'}">selected</c:if>
                                        value="WAIT_ACTIVE">
                                    <fmt:message key="user.status.wait_active"/>
                                </option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="password"><fmt:message key="registration.password.label"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text bg-white"><i class="fas fa-lock"></i></span>
                                </div>
                                <input type="password" name="password" value="<c:out value="${registrationForm['password']}"/>" class="form-control" id="password"
                                       placeholder="<fmt:message key="registration.password.placeholder"/>" aria-describedby="passwordHelp" required
                                       pattern="[a-zA-Z0-9@#$%!]{8,40}">
                            </div>
                            <small id="passwordHelp" class="form-text text-muted"><fmt:message key="registration.password.help"/></small>
                        </div>

                        <div class="form-group">
                            <label for="password"><fmt:message key="registration.confirm_password.label"/></label>
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text bg-white"><i class="fas fa-lock"></i></span>
                                </div>
                                <input type="password" name="confirmPassword" value="<c:out value="${registrationForm['confirmPassword']}"/>" class="form-control" id="confirmPassword"
                                       placeholder="<fmt:message key="registration.confirm_password.placeholder"/>"
                                       aria-describedby="passwordHelp" required
                                       pattern="[a-zA-Z0-9@#$%!]{8,40}">
                            </div>
                            <small id="confirmPasswordHelp" class="form-text text-muted"><fmt:message key="registration.confirm_password.help"/></small>
                        </div>
                        <c:remove var="registrationForm" scope="session"/>

                        <div class="create-item">
                            <button type="submit" id="submit" class="btn btn-primary"><fmt:message key="registration.submit"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin/create_user.js"></script>
</body>
</html>
