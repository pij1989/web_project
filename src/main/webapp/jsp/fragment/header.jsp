<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <c:choose>
            <c:when test="${role eq 'ADMIN'}">
                <a class="navbar-brand"
                   href="${pageContext.request.contextPath}/controller?command=to_admin_page_command">
                    <span><i class="fas fa-store"></i></span> <fmt:message key="header.brand"/>
                </a>
            </c:when>
            <c:when test="${role eq 'USER'}">
                <a class="navbar-brand"
                   href="${pageContext.request.contextPath}/controller?command=to_main_page_command">
                    <span><i class="fas fa-store"></i></span> <fmt:message key="header.brand"/>
                </a>
            </c:when>
            <c:when test="${role eq 'GUEST'}">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/controller?command=to_login_page_command">
                    <span><i class="fas fa-store"></i></span> <fmt:message key="header.brand"/></a>
            </c:when>
        </c:choose>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                <c:if test="${role eq 'USER' or role eq 'ADMIN'}">
                    <div style="color: white; padding-top: 5px; padding-right: 15px">
                        <span><i class="far fa-user"></i> ${user.username}</span>
                    </div>
                </c:if>
                <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="change_locale"/>
                    <div class="dropdown">
                        <button class="btn btn-success dropdown-toggle mx-2" type="button" id="language"
                                data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="header.button.language"/>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="change_locale">
                            <button class="dropdown-item" type="submit" name="language" value="en-US"><fmt:message
                                    key="header.language.english"/></button>
                            <button class="dropdown-item" type="submit" name="language" value="ru-BY"><fmt:message
                                    key="header.language.russian"/></button>
                        </div>
                    </div>
                </form>
                <c:choose>
                    <c:when test="${role eq 'USER' or role eq 'ADMIN'}">
                        <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="logout">
                            <button id="logout" class="btn btn-outline-success mx-2 my-2 my-sm-0" type="submit">
                                <fmt:message key="header.button.sign_out"/>
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${role eq 'GUEST'}">
                        <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="to_login_page_command">
                            <button class="btn btn-outline-success mx-2 my-2 my-sm-0" type="submit">
                                <fmt:message key="header.button.sign_in"/>
                            </button>
                        </form>
                        <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="to_register_page_command">
                            <button class="btn btn-outline-success mx-2 my-2 my-sm-0" type="submit">
                                <fmt:message key="header.button.sign_up"/>
                            </button>
                        </form>
                    </c:when>
                </c:choose>
                <c:if test="${role eq 'USER'}">
                    <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="get_orders">
                        <button class="btn btn-outline-success mx-2 my-2 my-sm-0" type="submit">
                            <fmt:message key="header.button.orders"/>
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </nav>
</header>


