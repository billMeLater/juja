<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SQLcmd</title>
</head>
<body>
<h3>connect to DB</h3>
<form action="connect" method="post">
    <table>
        <tr>
            <td>Database name</td>
            <td><input type="text" name="dbName"/></td>
        </tr>
        <tr>
            <td>User name</td>
            <td><input type="text" name="dbUser"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="dbPass"/></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="connect"/></td>
        </tr>
    </table>
</form>
<a href="menu.jsp">menu</a>
</body>
</html>
