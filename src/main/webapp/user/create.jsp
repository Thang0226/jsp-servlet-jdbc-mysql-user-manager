<%--
  Created by IntelliJ IDEA.
  User: thang
  Date: 2/12/24
  Time: 13:54
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
    <a href="users?action=users">List All Users</a>
  </h2>
</center>
<div align="center">
  <form method="post">
    <table border="1" cellpadding="5" style="border-collapse: collapse">
      <caption>
        <h2>Add New User</h2>
      </caption>
      <tr>
        <th>User Name:</th>
        <td>
          <input type="text" name="name" id="name" size="45"/>
        </td>
      </tr>
      <tr>
        <th>User Email:</th>
        <td>
          <input type="text" name="email" id="email" size="45"/>
        </td>
      </tr>
      <tr>
        <th>Country:</th>
        <td>
          <input type="text" name="country" id="country" size="15"/>
        </td>
      </tr>
      <tr>
        <th>Permission:</th>
        <td>
          <input type="checkbox" name="add" size="15"/> add |
          <input type="checkbox" name="edit" size="15"/> edit |
          <input type="checkbox" name="delete" size="15"/> delete |
          <input type="checkbox" name="view" size="15"/> view
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center">
          <input type="submit" value="Save"/>
        </td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>
