<%-- 
    Document   : createRecord
    Created on : Nov 3, 2015, 5:19:26 PM
    Author     : John Phillips
--%>

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
        <h2>Create New Book Record</h2>
        <form action="create" method="get">

            Title: <input type="text" name="title" size="100" placeholder="Enter book title" required>
            <br><br>
            Author: <input type="text" name="author" size="100" placeholder="Enter author" required>
            <br><br>            
            Subject: <input type="text" name="subject" size="100" placeholder="Enter subject" required>
            <br><br>
            ISBN: <input type="number" name="isbn" placeholder="ISBN" required>
            <br><br>

            <input type="hidden" name="action" value="createRecord">

            <input type="submit" name="submit" value="Submit">
            <br><br>
        </form>
    </body>
</html>

