<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="nl.rug.comgrafic.controller.admin.UserManagement" %>

<%--
  Created by IntelliJ IDEA.
  User: ethan
  Date: 24/04/2019
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>User management</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="/static/js/admin/userManagement.js"></script>

    <link rel="stylesheet" href="<spring:url value="/static/css/style.css"/>"/>
    <link rel="stylesheet" href="<spring:url value="/static/css/admin/management.css"/>"/>
    <script>
        // function to open modal for remove confirmation
        $(document).ready(function () {
            $('#removebtnModal').click(function () {
                $('#removemodal').modal('show')
            });
        });
    </script>
</head>

<body>
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
        <!--TODO: button Back = redirect to Admin page -->
        <form action="/admin" method="GET" class="form-inline my-2 my-lg-0">
            <!--BACKEND TODO:modify-->
            <button class="btn btn-outline-light my-2 my-sm-0" type="submit"
                    style="background-color: #8A0868; color: #ffffff">Back</button>
        </form>
        <!--end button Back-->
        <form action="/logout" method="POST" class="form-inline my-2 my-lg-0">
            <button class="btn btn-outline-light my-2 my-sm-0" type="submit"
                    style="background-color: #610B4B; color: #ffffff">Logout</button>
        </form>
    </div>
</nav>
<!-- end NAVBAR-->
<div class="container mt-4">

    <div class="row">
        <!--row0-->

        <div class="col">
        </div>

        <div class="col-lg-12 mt-5">
            <!--col1-->
            <h2 class="display-3 text-center">Gestionar operadores</h2>
            <div class="row">
                </br>
                </hr>
            </div>

            <div class="row justify-content-md-center">
                <!-------- SELECT OPERATOR -------->
                <form action="/admin/userManagement/selectUser" method="POST" class="form-signin">
                    <select name="userId" class="form-control">
                        <option value="-3" disabled hidden selected>Seleccionar operador</option>
                        <c:forEach var="operator" items="${allUsers}">
                            <option value="${operator.id}"><c:out value="${operator.username}"/></option>
                        </c:forEach>
                        <option value="-3" disabled>------------------</option>
                        <option value="-1">NEW ADMIN</option>
                        <option value="-2">NEW PRINTER</option>
                    </select>
                    <button class="btn btn-lg btn-light btn-block" type="Submit" name="action"
                            style="background-color: #610B4B; color: #ffffff">Select</button>
                </form>
            </div>

            <c:if test="${managedUser != null}">
                <div class="form-style-1-heading">Managed user: ${managedUser.username}</div>
                <c:choose>
                    <c:when test="${managedUser.username == UserManagement.NEW_ADMIN}">
                        <form action="/admin/userManagement/newAdmin" method="post">
                            <ul class="form-style-1">
                                <li>
                                    <label>Name <span class="required"></span></label>
                                    <input placeholder="${managedUser.username}" type="text" required="" name="username" class="field-long" />
                                </li>
                                <li>
                                    <label>Password <span class="required"></span></label>
                                    <input placeholder="Password" type="password" name="password" class="field-long" />
                                </li>
                                <li>
                                    <label>Confirm password <span class="required"></span></label>
                                    <input placeholder="Confirm Password" type="password" class="field-long" />
                                </li>
                                <li>
                                    <input type="submit" value="Submit"/>
                                </li>
                            </ul>
                        </form>
                    </c:when>
                    <c:when test="${managedUser.username == UserManagement.NEW_PRINTER}">
                        <form action="/admin/userManagement/newPrinter" method="post">
                            <ul class="form-style-1">
                                <li>
                                    <label>Name <span class="required"></span></label>
                                    <input placeholder="${managedUser.username}" type="text" required="" name="username" class="field-long" />
                                </li>
                                <li>
                                    <label>Password <span class="required"></span></label>
                                    <input placeholder="Password" type="password" name="password" class="field-long" />
                                </li>
                                <li>
                                    <label>Confirm password <span class="required"></span></label>
                                    <input placeholder="Confirm Password" type="password" class="field-long" />
                                </li>
                                <li>
                                    <label>Workhoursssss
                                        <select name="workHours" class="field-long">
                                            <option disabled hidden selected>Seleccionar WORKHOURS</option>
                                            <option value=8>8</option>
                                            <option value=16>16</option>
                                            <option value=24>24</option>
                                        </select>
                                    </label>
                                </li>
                                <li>
                                    <input type="submit" value="Submit" />
                                </li>
                            </ul>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="/admin/userManagement/saveChanges" method="post">
                            <ul class="form-style-1">
                                <li>
                                    <label>Name</label>
                                    <input value="${managedUser.username}" type="text" required="" name="username" class="field-long" />
                                </li>
                                <li>
                                    <label>New Password</label>
                                    <input placeholder="New Password" type="password" name="newPassword" class="field-long" />
                                </li>
                                <li>
                                    <label>Confirm password</label>
                                    <input placeholder="Confirm Password" type="password" class="field-long" />
                                </li>
                                <c:if test="${isAdmin != null && isAdmin == false}">
                                    <li>
                                        <label>Workhours
                                            <select name="workHours" class="field-long">
                                                <option disabled hidden selected>Seleccionar WORKHOURS</option>
                                                <option value=8>8</option>
                                                <option value=16>16</option>
                                                <option value=24>24</option>
                                            </select>
                                        </label>
                                    </li>
                                </c:if>
                                <li>
                                    <input type="submit" value="Submit" />
                                </li>
                            </ul>
                        </form>
                    </c:otherwise>
                </c:choose>

                <form action="/admin/userManagement/delete" method="post">
                    <script>
                        // function to open modal for remove confirmation
                        $(document).ready(function(){
                            $('#removebtnModal').click(function(){
                                $('#removemodal').modal('show')
                            });
                        });
                    </script>
                    <button type="button" id="removebtnModal"  style="background-color: #3B0B2E; color: #ffffff">DELETE</button>
                    <!--MODAL: confirm to remove-->
                    <div class="modal fade" id="removemodal" role="dialog" tabindex="-1">
                        <div class="modal-dialog modal-md" role="document">
                            <div class="modal-content" >
                                <div class="modal-header">
                                    <h4 class="modal-title">&iexcl;Precaucion! Eliminacion operador</h4>
                                </div>
                                <div class="modal-body">
                                    <p>Por favor, confirme para eliminar el operador seleccionado.</p>
                                    <button class="btn btn-md btn-light btn-block" type="Submit" style="background-color: #3B0B2E; color: #ffffff">Confirmar la eliminacion</button>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-light" data-dismiss="modal" style="background-color: #8A0868; color: #ffffff">Atras</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--END MODAL-->
                </form>
            </c:if>


            <div class="row">
                </br>
                </hr>
            </div>
        </div><!--col1-->

        <div class="col">
        </div>

    </div><!--row0-->
</div><!--container-->

<hr />

<!--FOOTER-->
<footer class="footer mt-auto text-center">
    <div class="inner">
        <p>Developed by students of <a href="https://www.rug.nl/">University of Groningen</a></p>
    </div>
</footer>
</body>

</html>