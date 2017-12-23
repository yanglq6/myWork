<%--
  Created by IntelliJ IDEA.
  User: xiaozl
  Date: 2015/8/18
  Time: 8:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>获取信息</title>
</head>
<body>

<table>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td>ID=${user.id}</td>
            <td>UserNamer=${user.userName}</td>
            <td>password=${user.password}</td>
            <td>
                <%--<form action="${website}meetings" method="post">--%>
                    <%--<!-- 这里的website的意思是当点击修改和删除后，页面跳转处理后再次回到了localhost:8080/meetings-->--%>
                <%----%>
                <%--</form>--%>
                <a href="/updateUser?id=${user.id}">修改</a>
                <%--<a href="deleteUserById?id=${user["id"]}">删除</a>--%>
                <a href="/deleteUserById?id=${user.id}">刪除</a>

            </td>
        </tr>
    </c:forEach>
    <a href="/insert">添加账户</a>
</table>
</body>
</html>
