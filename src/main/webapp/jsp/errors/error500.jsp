<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<fmt:setBundle basename="property.text"/>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errors/error.css" type="text/css"/>
    <title><fmt:message key="error.title"/></title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1 class="text-danger"><fmt:message key="error.500.status"/></h1>
                <h2>Status code: ${pageContext.errorData.statusCode}</h2>
                <br/>
                <h2>Exception: ${pageContext.exception}</h2>
                <br/>
                <h2>Message from exception: ${pageContext.exception.message}</h2>
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
