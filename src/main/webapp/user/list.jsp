<%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 2/12/24
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2 style="width: 175px; border: 2px solid black">
        <a href="/users?action=create" style="text-decoration: none; color: blue">Add New User</a>
    </h2>
    <div align="center" style="border: 2px solid gray; padding: 10px; margin: 20px; width: 30%">
        <h2 style="margin: 0 10px 10px 10px">
            Search Users By Country
        </h2>
        <form action="/users" method="post" style="margin: 5px 0">
            <input type="hidden" name="action" value="search">
            <input type="text" name="country" placeholder="Country" value="${requestScope.country}">
            <input type="submit" value="Search">
        </form>
        <div align="center" style="margin-top: 10px">
            <table border="1" cellpadding="5" style="border-collapse: collapse">
                <caption><h3 style="width: 400px; color: green; margin-top: 5px">
                    List of Users Searched by Country
                </h3></caption>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Country</th>
                </tr>
                <c:forEach var="user" items="${requestScope.list_users}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.country}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <div align="center" style="border: 2px solid gray; padding: 10px; margin: 20px; width: 30%">
        <table border="1" cellpadding="5" style="border-collapse: collapse">
            <caption><h2 style="margin: 0 10px 10px 10px">List of Users</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="user" items="${requestScope.listUser}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.country}"/></td>
                    <td>
                        <a href="/users?action=edit&id=${user.id}">Edit</a>
                        <a href="/users?action=delete&id=${user.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form action="/users" method="post" style="margin-top: 10px">
            <input type="hidden" name="action" value="sort">
            <input type="submit" value="Sort By Name" style="width: 120px">
        </form>
    </div>
</center>
</body>
</html>
