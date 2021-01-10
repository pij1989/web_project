<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/errors/error404.css" type="text/css"/>
    <title>Error page</title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>Oops!</h1>
                <h2>404 Not Found</h2>
                <div class="error-details">
                    Sorry, an error has occured, Requested page not found!
                </div>
                <div class="error-actions">
                    <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary btn-lg">
                        <span><i class="fas fa-home"></i></span> Take Me Home
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
</body>
</html>
