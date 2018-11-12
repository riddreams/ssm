<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <title>主页</title>
</head>
<body>
    <c:if test="${empty sessionScope.user}">
        <h1>Hello,<a href="${pageContext.request.contextPath}/login">请登录</a></h1>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <h1>Hello,${sessionScope.user.userName}</h1>
    </c:if>
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/upload">
        <label for="desc">描述</label>
        <input type="text" id="desc" name="desc"/><br/>
        <label for="file">文件1</label>
        <input id="file" type="file" name="multipartFile"/><br/>
        <label for="file2">文件2</label>
        <input id="file2" type="file" name="multipartFile"/><br/>
        <input type="submit" value="上传"/>
    </form>
</body>
</html>