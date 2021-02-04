<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <c:import url="../fragment/bootstrap_style.jsp"/>
    <title>Upload page</title>
</head>
<body>
<c:import url="../fragment/header.jsp"/>
<div class="container" style="padding-top: 35px">
    <form method="post" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
        <div class="form-group">
            <label for="uploadfile">Choose a file</label>
            <input type="file" class="form-control-file" value="Choose file" name="Choose file" id="uploadfile">
        </div>
        <button type="submit" class="btn btn-primary">Upload</button>
    </form>
    <br/>
    <c:choose>
        <c:when test="${upload_result eq 'upload successfully'}">
            <div class="alert alert-success" role="alert">
                    ${upload_result}
            </div>
        </c:when>
        <c:when test="${upload_result eq 'upload failed'}">
            <div class="alert alert-danger" role="alert">
                    ${upload_result}
            </div>
        </c:when>
    </c:choose>
</div>
<c:import url="../fragment/bootstrap_script.jsp"/>
</body>
</html>
