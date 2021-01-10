<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="property.text"/>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#"><span><i class="fas fa-store"></i></span> <fmt:message key="header.brand"/> </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <c:if test="${role eq 'USER'}">
                <div style="display: flex; flex-grow: inherit;">
                    <form class="form-inline my-2 my-lg-0 mx-lg-5" action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="search">
                        <input class="form-control mr-sm-2" type="search" placeholder="Search"
                               aria-label="Search" size="30">
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><span><i
                                class="fas fa-search"></i></span> <fmt:message key="header.button.search"/>
                        </button>
                    </form>
                </div>
            </c:if>
            <div style="display: flex; flex-grow: inherit; justify-content: flex-end">
                <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller"
                      method="post">
                    <input type="hidden" name="command" value="change_language"/>
                    <div class="dropdown">
                        <button class="btn btn-success dropdown-toggle mx-2" type="button" id="language"
                                data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            <fmt:message key="header.button.language"/>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="change_language">
                            <button class="dropdown-item" type="submit" name="language" value="en">EN</button>
                            <button class="dropdown-item" type="submit" name="language" value="ru">RU</button>
                        </div>
                    </div>
                </form>
                <c:choose>
                    <c:when test="${role eq 'USER'}">
                        <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="logout">
                            <button class="btn btn-outline-success mx-2 my-2 my-sm-0" type="submit">
                                <fmt:message key="header.button.signout"/>
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${role eq 'GUEST'}">
                        <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="to_login_page_command">
                            <button class="btn btn-outline-success mx-2 my-2 my-sm-0" type="submit">
                                <fmt:message key="header.button.signin"/>
                            </button>
                        </form>
                    </c:when>
                </c:choose>
                <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command" value="to_register_page_command">
                    <button class="btn btn-outline-success mx-2 my-2 my-sm-0" type="submit">
                        <fmt:message key="header.button.signup"/>
                    </button>
                </form>
            </div>
        </div>
    </nav>
</header>


