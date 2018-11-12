<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/doLogin" method="post">
        <label for="userName">用户名</label>
        <input id="userName" type="text" name="userName"/><br/>
        <label for="passWord">密码</label>
        <input id="passWord" type="password" name="passWord"/><br/>
        <span style="color: red">${requestScope.msg}</span><br/>
        <button type="submit">登录</button>
    </form>
</body>
</html>
