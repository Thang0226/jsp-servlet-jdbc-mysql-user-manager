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
    <h2>
        <a href="/users?action=create">Add New User</a>
    </h2>
    <div align="center" style="margin-bottom: 5px">
        <h2>
            Search User By Country
        </h2>
        <form method="post" style="margin-bottom: 5px">
            <input type="hidden" name="action" value="search">
            <input type="text" name="country" placeholder="Country">
            <input type="submit" value="Search">
        </form>
    </div>
</center>
<div align="center" style="margin-top: 10px">
    <table border="1" cellpadding="5">
        <caption><h3 style="width: 400px; color: green; margin-top: 5px">
            List of Users Searched by Country
        </h3></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
        </tr>
        <c:forEach var="user" items="${list_users}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.country}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div align="center">
    <table border="1" cellpadding="5">
        <caption><h2 style="margin: 30px 0 15px 0;">List of Users</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="user" items="${listUser}">
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
</div>
</body>
</html>
