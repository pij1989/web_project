<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<nav class="navbar"
     style="line-height: 1;border-bottom: 1px solid #e5e5e5; display: flex; flex-grow: inherit; justify-content: flex-end">
    <form class="form-inline py-2">
        <%--            <input type="hidden" name="command" value="search">--%>
        <input class="form-control mr-2" type="search" placeholder="Search"
               aria-label="Search" size="100">
        <button class="btn btn-outline-primary my-2 mr-2" type="submit"><span><i
                class="fas fa-search"></i></span> <fmt:message key="header.button.search"/>
        </button>
    </form>
    <form class="form-inline py-2" action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="view_cart"/>
        <button class="btn btn-outline-primary my-2" type="submit">
            <span><i class="fas fa-shopping-cart"></i></span> Cart
        </button>
    </form>
</nav>
<div class="py-1 mb-2" style="line-height: 1;border-bottom: 1px solid #e5e5e5;">
    <nav class="nav d-flex justify-content-between">
        <c:forEach var="category" items="${categories}">
            <a class="p-2 text-muted"
               href="${pageContext.request.contextPath}/controller?command=get_products&categoryId=<c:out value="${category.id}"/>">
                <c:out value="${category.name}"/></a>
        </c:forEach>
    </nav>
</div>
