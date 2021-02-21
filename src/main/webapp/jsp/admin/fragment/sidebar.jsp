<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<div class="col-md-2 col-xl-2">
    <nav class="nav flex-column sidebar">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_users"><span><i class="fas fa-user-friends"></i></span> <fmt:message key="admin.sidebar.firstlink"/></a>
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_products"><span><i class="fab fa-product-hunt"></i></span> <fmt:message key="admin.sidebar.secondlink"/></a>
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_categories"><span><i class="fas fa-bookmark"></i></span> <fmt:message key="admin.sidebar.thirdlink"/></a>
    </nav>
</div>
