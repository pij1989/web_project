<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errors/error.css" type="text/css"/>
    <title><fmt:message key="error.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1><fmt:message key="error.404.first_message"/></h1>
                <h2><fmt:message key="error.404.status"/></h2>
                <div><fmt:message key="error.404.second_message"/></div>
                <div class="error-actions">
                    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary btn-lg">
                        <span><i class="fas fa-home"></i></span> <fmt:message key="error.button"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
</body>
</html>
