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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="<spring:url value="/static/css/style.css"/>"/>
 </head>
    <body>

    <!--NAVBAR-->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top ">
        <label class="navbar-brand">COMGRAFIC</label>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon">upload</span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <label class="nav-link h5"></label>
                </li>
            </ul>
            <!--TODO: button Back = redirect to Previous page -->
            <form action="/redirect" method="POST" class="form-inline my-2 my-lg-0">
                <!--BACKEND TODO:modify-->
                <button class="btn btn-outline-light my-2 my-sm-0" type="submit"
                    style="background-color: #8A0868; color: #ffffff">Back</button>
            </form>

        </div>
    </nav>
    <!-- end NAVBAR-->
    <div class="container mt-4">
        <div class="row">

            <div class="col">
            </div>

            <div class="col-md mt-5">

                <form action="/upload/file" method="post" enctype="multipart/form-data">
                    <input type="text" name="description" class="form-control mt-3"
                    placeholder="Insert description"/>
                    <h4>Select image to upload:</h4>
                    <input type="file" name="file" id="fileToUpload"><br />
                    <button class="btn btn-lg btn-light btn-block" value="Upload Image" name="submit"
                    style="background-color: #3B0B2E; color: #ffffff">Upload</button>>
                </form>

            </div>

            <div class="col">
            </div>

        </div>
        <!--container-->

        <hr />

        <!--FOOTER-->
        <footer class="footer mt-auto text-center">
            <div class="inner">
                <p>Developed by students of <a href="https://www.rug.nl/">University of Groningen</a></p>
            </div>
        </footer>

        </body>

</html>
