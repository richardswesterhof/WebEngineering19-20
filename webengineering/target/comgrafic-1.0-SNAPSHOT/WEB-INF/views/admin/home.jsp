<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: ethan
  Date: 19/04/2019
  Time: 17:45
  To change this template use DBFile | Settings | DBFile Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Admin Homepage</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="<spring:url value="/static/css/style.css"/>"/> <link rel="stylesheet"
        href="<spring:url value="/static/css/admin/admin.css"/>"/> <script type="text/javascript"
        src="<spring:url value="/static/js/admin/admin.js"/>"> </script> </head> <body>

    <!--NAVBAR-->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top ">
        <label class="navbar-brand">COMGRAFIC</label>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <label class="nav-link h5">Admin ${username}</label>
                </li>
            </ul>
            <form action="/logout" method="POST" class="form-inline my-2 my-lg-0">
                <button class="btn btn-outline-light my-2 my-sm-0" type="submit"
                    style="background-color: #610B4B; color: #ffffff">Logout</button>
            </form>
        </div>
    </nav>

    <!-- ............................................................................................... -->
    <div class="container mt-4">
        <div class="row">
            <!--row0-->

            <div class="col">
            </div>

            <div class="col-lg-12 mt-5">
                <!--col1-->
                <h2 class="display-3 text-center">Administrator</h2>

                <div class="row justify-content-md-center">

                    <!--row1-->
                    <br>

                    <div class="form-group">
                        <form autocomplete="off" action="/admin/statistics" method="GET" class="form-horizontal"
                              role="form">
                            <button class="btn btn-lg btn-light btn-block" type="Submit"
                                    style="background-color: #8A0868; color: #ffffff">Estadistica</button>
                        </form>
                    </div>

                    <div class="col-md-1"></div>

                    <div class="form-group">
                        <form autocomplete="off" action="/admin/userManagement" method="GET" class="form-horizontal"
                            role="form">
                            <button class="btn btn-lg btn-light btn-block" type="Submit"
                                style="background-color: #3B0B2E; color: #ffffff">Gestionar Usuarios</button>
                        </form>
                    </div>

                    <div class="col-md-1"></div>

                    <div class="form-group">
                        <form autocomplete="off" action="/admin/operatorManagement" method="GET" class="form-horizontal"
                            role="form">
                            <button class="btn btn-lg btn-light btn-block" type="Submit"
                                style="background-color: #610B4B; color: #ffffff">Gestionar Operadores</button>
                        </form>
                    </div>

                    <div class="col-md-1"></div>

                    <%--<div class="col-md-1"></div>--%>
                </div>
                <!--row1-->

                <div class="row">
                    </br>
                    </hr>

                    <h4>trabajos en progreso</h4>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead style="background-color: #8A0868; color: #ffffff">
                                <tr>
                                    <td>Name</td>
                                    <td>Printer</td>
                                    <td>Start time</td>
                                    <td></td>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="shift" items="${shiftList}">
                                    <tr>
                                        <td>${shift.operator.name}</td>
                                        <td>${shift.printer.name}</td>
                                        <td>${shift.startTime.toLocalTime()}</td>
                                        <td>
                                            <form action="/admin/home/endShift" method="post">
                                                <input hidden name="operatorID" value="${shift.operator.id}"
                                                    title="operatorID">
                                                <button type="submit" class="btn btn-light btn-sm btn-block"
                                                    style="background-color: #3B0B2E; color: #ffffff">END</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="col">
                </div>

                <div class="row">
                    </br>
                    </hr>

                    <h4>cambios en el progreso</h4>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead style="background-color: #610B4B; color: #ffffff">
                            <tr>
                                <td>Name</td>
                                <td>Printer</td>
                                <td>Job type</td>
                                <td>Start time</td>
                                <td></td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="job" items="${jobList}">
                                <tr>
                                    <td>${job.operator.name}</td>
                                    <td>${job.printer.name}</td>
                                    <td>${job.jobType.type}</td>
                                    <td>${job.startTime.toLocalTime()}</td>
                                    <td>
                                        <form action="/admin/home/endJob" method="post">
                                            <input hidden name="operatorID" value="${job.operator.id}"
                                                   title="operatorID">
                                            <button class="btn btn-light btn-sm btn-block"
                                                    style="background-color: #3B0B2E; color: #ffffff"
                                                    type="submit">END</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="col">
                </div>

            </div>
            <!--row0-->
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