<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <title>Error page</title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container" style="text-align: center">
    <img class="img-thumbnail" src="${pageContext.request.contextPath}/img/error500.jpg">
    <br/>
    <h2>Status code: ${pageContext.errorData.statusCode}</h2>
    <br/>
    <h2>Exception: ${pageContext.exception}</h2>
    <br/>
    <h2>Message from exception: ${pageContext.exception.message}</h2>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
</body>
</html>
