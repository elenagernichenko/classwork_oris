<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>welcome</title>
</head>

<body>

<div class="header">
    <div class="container_1">
        <div class="header__inner">
            <div class="header__text">
                <% String status = (String) request.getAttribute("status");%>
                <h1><%=status%></h1>
            </div>
        </div>
    </div>
</div>

<div class="basic">
    <div class="container_2">
        <div class="basic__inner">
            <div class="block__2">
                <%String res = (String) request.getAttribute("resultOfAuth");%>
                <h4 class="block__text"><%=res%></h4>
            </div>
        </div>
    </div>
</div>

</body>
</html>