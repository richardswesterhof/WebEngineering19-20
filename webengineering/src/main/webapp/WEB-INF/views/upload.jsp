<%--
  Created by IntelliJ IDEA.
  User: corne
  Date: 22-4-2019
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>upload tester</title>

    <form action="/upload/file" method="post" enctype="multipart/form-data">
        <input type="text" name="description"/><br/>
        Select image to upload:
        <input type="file" name="file" id="fileToUpload"><br/>
        <input type="submit" value="Upload Image" name="submit">
    </form>
</head>
<body>

</body>
</html>
