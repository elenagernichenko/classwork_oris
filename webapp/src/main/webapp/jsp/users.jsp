<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Users</title>
</head>
<body>
<h1>From JSP Users</h1>
<div>
  <table>
    <tr>
      <th>Username</th>
      <th>Email</th>
    </tr>
    <%
      List<User> users = (List<User>) request.getAttribute("usersForJsp");
      for ( int i = 0; i < users.size(); i++) {
    %>
    <tr>
      <td> <%=users.get(i).getUsername()%></td>
      <td> <%=users.get(i).getEmail()%></td>
    </tr>
    <%}%>
  </table>
</div>
</body>
</html>