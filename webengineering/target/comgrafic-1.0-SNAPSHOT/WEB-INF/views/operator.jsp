<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>Operator Tools page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="<spring:url value="/static/css/style.css"/>"/>
    <script type="text/javascript" src="<spring:url value="/static/js/operator.js"/>"></script>
    <script type="text/javascript" src="<spring:url value="/static/js/operator12.js"/>"></script>
</head>

<body>
<!---NAVBAR-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <label class="navbar-brand">COMGRAFIC</label>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <label class="nav-link h5">Printer: ${printerName}</label>
            </li>
        </ul>
        <form action="/logout" method="POST" class="form-inline my-2 my-lg-0" onsubmit="confirmLogout(${busyOperators});alert('LOL');alert('Busy: '+${busyOperators});alert('Again LOL');">
            <button class="btn btn-outline-light my-2 my-sm-0" type="submit"
                    style="background-color: #610B4B; color: #ffffff">Logout</button>
        </form>
    </div>
</nav>

<div class="container mt-4">
    <div class="row ">


        <div class="col">
        </div>

        <div class="col-lg-12 mt-5">
            <h2 class="display-3 text-center">Operador</h2>
            <br />
            <br />
            <br />
            <h3 class="display-7 text-center">
                <c:if test="${operator != null}">Operador seleccionado: ${operator.name}</c:if>
            </h3>
            <br />
            <br />
            <div class="row justify-content-md-center">

                <!--SELECT OPERATOR-->
                <div class="col-3">
                    <form action="/operator/selectOperator" method="POST" class="form-group">
                        <h4 class="text-center">Seleccionar nombre</h4>
                        <div class="operatorlist">
                            <select id="opId" name="opId" class="form-control">
                                <option value="" disabled hidden selected>Seleccionar operador</option>
                                <c:forEach var="user" items="${allOperators}">
                                    <option value="${user.id}"><c:out value="${user.name}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <br />
                        <input class="btn btn-md btn-light btn-block" type="submit" name="select" value="SELECCIONAR"
                               style="background-color: #610B4B; color: #ffffff" />

                    </form>
                </div>

                <!--SHIFT-->
                <div class="col-3">
                    <c:if test="${operator != null}">
                        <h4 class="text-center">Turnos</h4>
                        <c:choose>
                            <c:when test="${!operator.shiftStarted}">
                                <form action="/operator/shift/start" method="POST" class="form-group">
                                    <!--MODAL: start popup-->
                                    <button type="button" id="startShiftbtnModal" class="btn btn-md btn-light btn-block"
                                            style="background-color: #3B0B2E; color: #ffffff">COMIENZO</button>
                                    <!--MODAL-->
                                    <div class="modal fade" id="startShiftmodal" role="dialog" tabindex="-1">
                                        <div class="modal-dialog modal-md" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Inicio de turno</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Por favor, confirme para iniciar el turno para el operador ${operator.name}.</p>
                                                    <input class="btn btn-md btn-light btn-block" type="submit"
                                                           name="start" value="COMIENZO"
                                                           style="background-color: #3B0B2E; color: #ffffff" />
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-light" data-dismiss="modal"
                                                            style="background-color: #8A0868; color: #ffffff">Atras</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END MODAL-->
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="/operator/shift/end" method="POST" class="form-group">
                                    <!--MODAL: stop popup-->
                                    <button type="button" id="stopShiftbtnModal" class="btn btn-md btn-light btn-block"
                                            style="background-color: #8A0868; color: #ffffff">DETENER</button>
                                    <!--MODAL-->
                                    <div class="modal fade" id="stopShiftmodal" role="dialog" tabindex="-1">
                                        <div class="modal-dialog modal-md" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Detener turno</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Por favor, confirme para detener el turno para el operador ${operator.name}.</p>
                                                    <input class="btn btn-md btn-light btn-block" type="submit"
                                                           name="start" value="DETENER"
                                                           style="background-color: #3B0B2E; color: #ffffff" />
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-light" data-dismiss="modal"
                                                            style="background-color: #8A0868; color: #ffffff">Atras</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END MODAL-->
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>

                <!--JOBS-->
                <div class="col-3">
                    <c:if test="${operator.shiftStarted}">
                        <h4 class="text-center">Trabajos</h4>
                        <c:choose>
                            <c:when test="${!operator.doingJob}">
                                <form action="/operator/job/start" method="POST" class="form-group">
                                    <div class="operatorlist">
                                        <select id="jobTypeName" name="jobTypeName" class="form-control"
                                            onchange="showExtraInput();">
                                            <option value="" disabled hidden selected>Seleccionar trabajo</option>
                                            <c:forEach var="jobtype" items="${allJobTypes}">
                                                <option value="${jobtype.type}"><c:out value="${jobtype.type}"/></option>
                                            </c:forEach>
                                        </select>

                                        <!--&lt;!&ndash; When Otros is selected shows up a textbox to specify &ndash;&gt;-->
                                        <input type="text" name="specification" id="specification"
                                               class="form-control mt-3" placeholder="Especificar" style="display: none;"/>
                                        <input type="number" name="numberOfPlates" id="numberOfPlates" onkeydown="return event.keyCode !== 69"
                                               min="0" value="0"
                                               class="form-control mt-3" placeholder="Número de formas T/R" style="display: none;"/>
                                        <div id="tiraje_div" class="checkbox checkbox-primary" style="display: none;">
                                            <input id="tirajeTR" name="tirajeTR" class="styled" type="checkbox">
                                            <label for="tirajeTR">
                                                T/R
                                            </label>
                                        </div>
                                        <br />

                                        <!--MODAL: start popup-->
                                        <button type="button" id="startJobbtnModal"
                                                class="btn btn-md btn-light btn-block"
                                                style="background-color: #3B0B2E; color: #ffffff">COMIENZO</button>
                                        <!--MODAL-->
                                        <div class="modal fade" id="startJobmodal" role="dialog" tabindex="-1">
                                            <div class="modal-dialog modal-md" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h4 class="modal-title">Empezar trabajo</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>Por favor, confirme para comenzar el trabajo seleccionado.
                                                        </p>
                                                        <input class="btn btn-md btn-light btn-block" type="submit" name="start"
                                                               value="START" style="background-color: #3B0B2E; color: #ffffff" />
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-light" data-dismiss="modal"
                                                                style="background-color: #8A0868; color: #ffffff">Atras</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!--END MODAL-->
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="/operator/job/end" method="POST" class="form-group">
                                    <h5>Corriente: ${jobType.type}</h5>
                                    <!--MODAL: start popup-->
                                    <button type="button" id="stopJobbtnModal"
                                            class="btn btn-md btn-light btn-block"
                                            style="background-color: #8A0868; color: #ffffff">DETENER</button>
                                    <!--MODAL-->
                                    <div class="modal fade" id="stopJobmodal" role="dialog" tabindex="-1">
                                        <div class="modal-dialog modal-md" role="document">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title">Detener trabajo</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Por favor, confirme para detener el trabajo seleccionado.</p>
                                                    <input class="btn btn-md btn-light btn-block" type="submit" value="STOP"
                                                           style="background-color: #3B0B2E; color: #ffffff" />
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-light" data-dismiss="modal"
                                                            style="background-color: #8A0868; color: #ffffff">Atras</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END MODAL-->
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="footer mt-auto text-center">
    <div class="inner">
        <p>Developed by students of <a href="https://www.rug.nl/">University of Groningen</a></p>
    </div>
</footer>
</body>

</html>