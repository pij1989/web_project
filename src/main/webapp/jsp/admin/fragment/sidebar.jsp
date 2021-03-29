<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<div class="col-md-2 col-xl-2">
    <nav class="nav flex-column sidebar">
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_users"><span><i class="fas fa-user-friends"></i></span> <fmt:message key="admin.sidebar.first_link"/></a>
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_products"><span><i class="fab fa-product-hunt"></i></span> <fmt:message key="admin.sidebar.second_link"/></a>
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_categories"><span><i class="fas fa-bookmark"></i></span> <fmt:message key="admin.sidebar.third_link"/></a>
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_reviews"><span><i class="fas fa-comments"></i></span> <fmt:message key="admin.sidebar.four_link"/></a>
        <a class="nav-link" href="${pageContext.request.contextPath}/controller?command=get_orders"><span><i class="fas fa-dollar-sign"></i></span> <fmt:message key="admin.sidebar.five_link"/></a>
    </nav>
</div>
