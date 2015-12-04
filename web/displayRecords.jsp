<%-- 
    Document   : displayRecords
    Created on : Nov 3, 2015, 4:52:40 PM
    Author     : John Phillips
--%>

<%@page import="java.util.List, model.Employee"%>
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
        <h2>Book Report</h2>
        <%
            List<Employee> mydata = (List<Employee>) request.getAttribute("mydata");
            out.println("<table>");
            for (Employee employee : mydata) {
                out.println(employee.inHTMLRowFormat());
            }
            out.println("</table>");
        %>
    </body>
</html>
