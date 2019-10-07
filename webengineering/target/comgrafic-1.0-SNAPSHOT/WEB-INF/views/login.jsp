<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <title>Login page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="<spring:url value="/static/css/style.css"/>"/>
</head>

<body>

<div class="container">
        <div class="col-md-auto mt-3 text-center">
            <img src="<spring:url value="/images/logo.png"/>" style="width: 75%">
        </div>

    <div class="row">
            
        <div class="col">
        </div>

        <div class="form col-md-auto mt-1">
            <form action="/login" method="POST" class="form-signin">
                <h3 class="form-signin-heading">Bienvenido</h3>
                <br />
                <div class="userlist">
                    <select id="username" name="username" class="form-control" title="Username">
                        <option value="" disabled hidden selected>Seleccionar usuario</option>
                        <c:forEach var="user" items="${allUsers}">
                            <option value="${user.username}"><c:out value="${user.username}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <%--<input type="text" id="username" role="username" class="form-control" placeholder="Username"--%>
                       <%--placeholder="Password" required="" />--%>
                <br />
                <input type="password" id="password" name="password" class="form-control"
                       placeholder="Password" required="" />

                <br />

                <%--<div textalign="center" th:if="${param.error}">--%>
                        <%--<p style="font-size: 20; color: #FF1C19;">&iexcl;Password invalido! Por favor, verificalo.</p>--%>
                    <%--</div>--%>

                <div class="row">
                    <div class="col-sm">
                        <input class="btn btn-lg btn-light btn-block" type="submit" name="action"  value="Enviar" style="background-color: #610B4B; color: #ffffff"/>
                    </div>
                </div>

            </form>
        </div>

        <div class="col">
        </div>

    </div>
</div>

<hr/>

<footer class="footer mt-auto text-center">
    <div class="inner">
        <p>Developed by students of <a href="https://www.rug.nl/">University of Groningen</a></p>
    </div>
</footer>
</body>

</html>