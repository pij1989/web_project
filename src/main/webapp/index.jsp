<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<c:choose>
    <c:when test="${role eq 'ADMIN'}">
        <jsp:forward page="/jsp/admin/admin.jsp"/>
    </c:when>
    <c:when test="${role eq 'USER'}">
        <jsp:forward page="/jsp/main.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="/jsp/login.jsp"/>
    </c:otherwise>
</c:choose>
</body>
</html>
