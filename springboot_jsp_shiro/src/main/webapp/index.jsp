<%--解决乱码--%>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Shiro Demo Index</title>
</head>
<body>
<%--    快速补全为!加Tab--%>
    <h1>系统主页：受限资源</h1>
    <a href="${pageContext.request.contextPath}/user/logout">退出用户</a>
    <ul>
        <li><a href="">用户管理</a> </li>
        <li><a href="">商品管理</a> </li>
        <li><a href="">订单管理</a> </li>
        <li><a href="">物流管理</a> </li>
    </ul>
</body>
</html>