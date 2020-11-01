<%--解决乱码--%>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%--引入shiro标签--%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
<%--展示用户的身份信息--%>
    <shiro:authenticated>
        认证之后展示内容<br>
        <h1><shiro:principal/></h1>
    </shiro:authenticated>
    <a href="${pageContext.request.contextPath}/user/logout">退出用户</a>
    <ul>
<%--        我们设计用户管理只能被user角色看到，admin能看到所有--%>
        <shiro:hasAnyRoles name="user, admin">
            <li><a href="">用户管理</a>
                <ul>
                    <shiro:hasPermission name="user:add:*">
                        <li><a href="">添加</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:delete:*">
                        <li><a href="">删除</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:update:*">
                        <li><a href="">修改</a></li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="user:select:01">
                        <li><a href="">查询</a></li>
                    </shiro:hasPermission>
                </ul>
            </li>
        </shiro:hasAnyRoles>
        <shiro:hasRole name="admin">
            <li><a href="">商品管理</a> </li>
                <ul>
                    <shiro:hasPermission name="product:*:01">
                        <li><a href="">操作01商品</a></li>
                    </shiro:hasPermission>
                </ul>
            <li><a href="">订单管理</a> </li>
            <li><a href="">物流管理</a> </li>
        </shiro:hasRole>
    </ul>
</body>
</html>
