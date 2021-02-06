<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <title><fmt:message key="admin.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <div style="text-align: center;padding-top: 15px">
                <h1><fmt:message key="admin.message"/></h1>
            </div>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
</body>
</html>
