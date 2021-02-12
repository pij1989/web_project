<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="fragment/bootstrap_style.jsp"/>
    <title><fmt:message key="registration_activate.title"/></title>
</head>
<body>
<c:import url="fragment/header.jsp"/>
<div class="card bg-light m-5" style="max-width: 100%;">
    <div class="card-body bg-light" style="text-align: center">
        <h2><fmt:message key="registration_activate.message"/></h2>
    </div>
</div>
<c:import url="fragment/bootstrap_script.jsp"/>
</body>
</html>
