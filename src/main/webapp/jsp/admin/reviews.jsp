<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="custom_tag" prefix="ctg" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.text"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">
    <title><fmt:message key="reviews.title"/></title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container-fluid">
    <div class="row flex-xl-nowrap">
        <c:import url="fragment/sidebar.jsp"/>
        <main class="col-md-10 col-xl-10" role="main">
            <br/>
            <c:choose>
                <c:when test="${deleteReviewSuccess}">
                    <div class="alert alert-success" role="alert" id="successDeleteReview">
                        Review is deleted
                    </div>
                    <c:remove var="deleteReviewSuccess" scope="session"/>
                </c:when>
                <c:when test="${deleteReviewError}">
                    <div class="alert alert-danger" role="alert" id="errorDeleteReview">
                        Error is occurred
                    </div>
                    <c:remove var="deleteReviewError" scope="session"/>
                </c:when>
            </c:choose>
            <div class="d-flex justify-content-center">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Time creating</th>
                        <th scope="col">Username</th>
                        <th scope="col">User email</th>
                        <th scope="col">Product</th>
                        <th scope="col">Rating</th>
                        <th scope="col">Comment</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="review" items="${reviews}">
                        <tr>
                            <td><ctg:formatLocalDateTime date="${review.creatingTime}" locale="${locale}"/></td>
                            <td><c:out value="${review.user.username}"/></td>
                            <td><c:out value="${review.user.email}"/></td>
                            <td><c:out value="${review.product.name}"/></td>
                            <td>
                                <c:forEach var="i" begin="1" end="${review.rating}">
                                    <span class="fa fa-star checked-rate"></span>
                                </c:forEach>
                            </td>
                            <td class="w-25"><c:out value="${review.comment}"/></td>

                            <td>
                                <div class="d-flex justify-content-end">
                                    <form method="post" action="${pageContext.request.contextPath}/controller">
                                        <input type="hidden" name="command" value="delete_review">
                                        <input type="hidden" name="reviewId" value="<c:out value="${review.id}"/>">
                                        <button class="btn btn-outline-danger mx-2 my-2 my-sm-0" type="submit">
                                            <span><i class="fas fa-trash"></i> <fmt:message
                                                    key="reviews.button.delete"/></span>
                                        </button>
                                    </form>
                                </div>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
<script src="${pageContext.request.contextPath}/js/admin/reviews.js"></script>
</body>
</html>