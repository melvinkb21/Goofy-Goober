<%-- 
    Document   : updateRecord2
    Created on : Nov 3, 2015, 8:54:49 PM
    Author     : John Phillips
--%>

<%@page import="model.Employee" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Elliott 216 Book Database</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="mystyle.css">
    </head>
    <body>
        <h1><a href="home.html">Elliott 216 Book Database</a></h1>
        <h2>Update Book Record</h2>
        <form action="update" method="get">
            <% Employee employee = (Employee) request.getAttribute("employee");%>
            Book Id: <input type="text" name="empId" value="<%= employee.getEmpId() %>" readonly>
            <br><br>
            Title: <input type="text" name="title" size="100" value="<%= employee.getTitle() %>" required>
            <br><br>
            Author: <input type="text" name="author" size="100" value="<%= employee.getAuthor() %>" required>
            <br><br>            
            Subject: <input type="text" name="subject" size="100" value="<%= employee.getSubject() %>" required>
            <br><br>
            ISBN: <input type="number" name="isbn" value="<%= employee.getIsbn() %>" required>
            <br><br>

            <input type="hidden" name="action" value="updateRecord2">

            <input type="submit" name="submit" value="Submit">
            <br><br>
        </form>
    </body>
</html>
