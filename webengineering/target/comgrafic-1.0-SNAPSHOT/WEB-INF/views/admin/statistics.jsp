<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="nl.rug.comgrafic.controller.statistics.Statistics" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Estadistica</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="<spring:url value="/static/css/style.css"/>"/> <link rel="stylesheet"
        href="<spring:url value="/static/css/admin/admin.css"/>"/> <script type="text/javascript"
        src="<spring:url value="/static/js/admin/admin.js"/>"> </script> <script>
            function showModal(type, dataMonth) {
                let xId = type + "modal" + dataMonth.value.toString();
                $("#" + xId).modal('show');
            }
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


    <!-- ............................................................................................... -->
    <div class="container mt-4">
        <div class="row">
            <!--row0-->

            <div class="col">
            </div>

            <div class="col-lg-12 mt-5">
                <!--col1-->
                <h2 class="display-3 text-center">Estadistica</h2>

                <div class="row">
                    </br>
                    </hr>
                </div>


                <div class="row justify-content-md-center">
                    <!--row1-->

                    <!--JOBS STATISTICS-->
                    <div class="col">
                        <form autocomplete="off" action="/admin/statistics/jobs" method="post" class="form-horizontal"
                            role="form">
                            <h3>Trabajos estadistica</h3>
                            <fieldset>
                                <select id="id" name="id" class="form-control">
                                    <option value="" disabled hidden selected>Seleccionar impresora o operador</option>
                                    <option value="" disabled>---Operadores---</option>
                                    <c:forEach var="user" items="${allOperators}">
                                        <option value="${user.id}">
                                            <c:out value="${user.name}" />
                                        </option>
                                    </c:forEach>
                                    <option value="" disabled>---Impresoras---</option>
                                    <c:forEach var="printer" items="${allPrinters}">
                                        <option value="${printer.id}">
                                            <c:out value="${printer.name}" />
                                        </option>
                                    </c:forEach>
                                    <option value="" disabled>-----------</option>
                                    <option value="-1">Todos impresoras/operadores</option>
                                </select>
                                <select name="jobTypes[]" multiple="multiple" tabindex="1">
                                    <c:forEach var="printer" items="${allJobTypes}">
                                        <option value="${printer.id}">
                                            <c:out value="${printer.type}" />
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="jan" value="1"
                                            autocomplete="off"> ene
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="feb" value="2"
                                            autocomplete="off"> feb
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="mar" value="3"
                                            autocomplete="off"> mar
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="apr" value="4"
                                            autocomplete="off"> abr
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="may" value="5"
                                            autocomplete="off"> may
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="jun" value="6"
                                            autocomplete="off"> jun
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="jul" value="7"
                                            autocomplete="off"> jul
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="aug" value="8"
                                            autocomplete="off"> ago
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="sep" value="9"
                                            autocomplete="off"> sep
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="oct" value="10"
                                            autocomplete="off"> oct
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="nov" value="11"
                                            autocomplete="off"> nov
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="job_months[]" id="dec" value="12 "
                                            autocomplete="off"> dic
                                    </label>
                                </div>

                                <label class="btn btn-outline-dark">
                                    <input type="checkbox" onclick="toggle(this, 'JOB');"> Alternar todo
                                </label>

                                <%--Year Overview--%>
                                <div class="checkbox checkbox-primary">
                                    <input id="yearOverview" name="yearOverview" class="styled" type="checkbox">
                                    <label for="yearOverview">
                                        Resumen a&ntilde;o
                                    </label>
                                </div>
                                <input type="number" id="year" name="year" onkeydown="return event.keyCode !== 69"
                                    class="form-control" placeholder="Year" value="2019" />
                                <button class="btn btn-lg btn-light btn-block" type="Submit"
                                    style="background-color: #3B0B2E; color: #ffffff">Seleccionar</button>
                            </fieldset>
                        </form>
                        <c:if test="${jobs != null}">
                            <form action="/admin/statistics/download/jobs" method="POST">
                                <button class="btn btn-lg btn-light btn-block" type="Submit"
                                    style="background-color: #8A0868; color: #ffffff">Descargar</button>
                            </form>
                        </c:if>
                    </div>

                    <div class="col">
                        <%--Mostrar tablas de trabajos--%>
                        <c:if test="${jobs != null}">
                            <br />
                            <h3>${jobs.downloadable.name} en el a&ntilde;o ${jobs.year}</h3>
                            <c:forEach var="data" items="${jobs.dataList}" varStatus="theMonth">
                                <div class="table-responsive">
                                    <table class="table table-striped" cellspacing="15">
                                        <caption style="caption-side: top">${data.description()}</caption>
                                        <%--Row header--%>
                                        <thead style="background-color:  #8A0868; color: #ffffff">
                                            <tr>
                                                <c:forEach var="normal_rH" items="${data.normal.rowHeader}">
                                                    <th>${normal_rH}</th>
                                                </c:forEach>
                                                <c:forEach var="aggregate_rH" items="${data.aggregate.rowHeader}">
                                                    <th class="aggregate">${aggregate_rH}</th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%--Column header + values--%>
                                            <c:forEach var="row" begin="0" end="${fn:length(jobs.columnHeader) - 1}">
                                                <tr>
                                                    <td>
                                                        <span style="font-weight:bold">${jobs.columnHeader[row]}</span>
                                                    </td>
                                                    <c:forEach var="normal_value" items="${data.normal.values[row]}">
                                                        <td bgcolor="${normal_value == Statistics.WEEKEND ? '#FAF4F8' : ''}">
                                                            <c:if test="${normal_value > 0}">FAF4F8
                                                                ${normal_value}
                                                            </c:if>
                                                        </td>
                                                    </c:forEach>
                                                    <td class="aggregate">
                                                        <c:if test="${data.aggregate.values[row][Statistics.SUM] > 0}">
                                                            ${data.aggregate.values[row][Statistics.SUM]}
                                                        </c:if>
                                                    </td>
                                                    <td class="aggregate">
                                                        <c:if test="${data.aggregate.values[row][Statistics.AVERAGE] > 0}">
                                                            ${data.aggregate.values[row][Statistics.AVERAGE]}
                                                        </c:if>
                                                    </td>
                                                    <td class="aggregate">
                                                        <c:if test="${data.aggregate.values[row][Statistics.PERCENTAGE] > 0}">
                                                            ${data.aggregate.values[row][Statistics.PERCENTAGE]}%
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>

                                            <%--Production hours--%>
                                            <tr>
                                                <td>
                                                    <span style="font-weight:bold">HORAS DE PRODUCCION</span>
                                                </td>
                                                <c:forEach var="normal_value" items="${data.normal.productionHours}">
                                                    <td bgcolor="${normal_value == Statistics.WEEKEND ? '#FAF4F8' : ''}">
                                                        <c:if test="${normal_value > 0}">
                                                            ${normal_value}
                                                        </c:if>
                                                    </td>
                                                </c:forEach>
                                                <td class="aggregate">
                                                    <c:if test="${data.aggregate.productionHours[Statistics.SUM] > 0}">
                                                        ${data.aggregate.productionHours[Statistics.SUM]}
                                                    </c:if>
                                                </td>
                                                <td class="aggregate">
                                                    <c:if test="${data.aggregate.productionHours[Statistics.AVERAGE] > 0}">
                                                        ${data.aggregate.productionHours[Statistics.AVERAGE]}
                                                    </c:if>
                                                </td>
                                                <td class="aggregate">
                                                    <c:if test="${data.aggregate.productionHours[Statistics.PERCENTAGE] > 0}">
                                                        ${data.aggregate.productionHours[Statistics.PERCENTAGE]}%
                                                    </c:if>
                                                </td>
                                            </tr>

                                            <%--Working hours--%>
                                            <tr>
                                                <td>
                                                    <span style="font-weight:bold">HORAS DE PRODUCCION</span>
                                                </td>
                                                <c:forEach var="normal_value" items="${data.normal.workingHours}">
                                                    <td bgcolor="${normal_value == Statistics.WEEKEND ? '#FAF4F8' : ''}">
                                                        <c:if test="${normal_value > 0}">
                                                            ${normal_value}
                                                        </c:if>
                                                    </td>
                                                </c:forEach>
                                                <td class="aggregate">
                                                    <c:if test="${data.aggregate.workingHours[Statistics.SUM] > 0}">
                                                        ${data.aggregate.workingHours[Statistics.SUM]}
                                                    </c:if>
                                                </td>
                                                <td class="aggregate">
                                                    <c:if test="${data.aggregate.workingHours[Statistics.AVERAGE] > 0}">
                                                        ${data.aggregate.workingHours[Statistics.AVERAGE]}
                                                    </c:if>
                                                </td>
                                                <td class="aggregate">
                                                    <c:if test="${data.aggregate.workingHours[Statistics.PERCENTAGE] > 0}">
                                                        ${data.aggregate.workingHours[Statistics.PERCENTAGE]}%
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <!--MODAL-->
                                <div class="modal fade" id="Jobmodal${data.month}" role="dialog" tabindex="-1">
                                    <div class="modal-dialog modal-xl" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">Trabajo estadistica</h4>
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphNormal}" style="width:100%">
                                            </div>
                                            <hr>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphSum}" style="width:100%">
                                            </div>
                                            <hr>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphAverage}" style="width:100%">
                                            </div>
                                            <hr>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphPercentage}" style="width:100%">
                                            </div>
                                            <hr>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-light" data-dismiss="modal"
                                                    style="background-color: #610B4B; color: #ffffff">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-lg btn-light btn-block"
                                    style="background-color: #610B4B; color: #ffffff" onclick="showModal('Job', this);"
                                    value="${data.month}">Mostrar grafica</button>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
                <!--row1-->

                <div class="row">
                    </br>
                    </hr>
                </div>

                <div class="row justify-content-md-center">
                    <!--row1-->
                    <!--SHIFTS STATISTICS-->
                    <div class="col">
                        <form autocomplete="off" action="/admin/statistics/shifts" method="POST" class="form-horizontal"
                            role="form">
                            <h3>Turnos estadistica</h3>
                            <fieldset>
                                <select name="operators[]" multiple="multiple" tabindex="1">
                                    <c:forEach var="printer" items="${allOperators}">
                                        <option value="${printer.id}">
                                            <c:out value="${printer.name}" />
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_jan" value="1"
                                            autocomplete="off"> ene
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_feb" value="2"
                                            autocomplete="off"> feb
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_mar" value="3"
                                            autocomplete="off"> mar
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_apr" value="4"
                                            autocomplete="off"> abr
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_may" value="5"
                                            autocomplete="off"> may
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_jun" value="6"
                                            autocomplete="off"> jun
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_jul" value="7"
                                            autocomplete="off"> jul
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_aug" value="8"
                                            autocomplete="off"> ago
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_sep" value="9"
                                            autocomplete="off"> sep
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_oct" value="10"
                                            autocomplete="off"> oct
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_nov" value="11"
                                            autocomplete="off"> nov
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="shift_months[]" id="shift_dec" value="12 "> dic
                                    </label>
                                </div>
                                <label class="btn btn-outline-dark">
                                    <input type="checkbox" onclick="toggle(this, 'SHIFT');"> Alternar todo
                                </label>

                                <%--Year Overview--%>
                                <div class="checkbox checkbox-primary">
                                    <input id="shift_yearOverview" name="shift_yearOverview" class="styled"
                                        type="checkbox">
                                    <label for="shift_yearOverview">
                                        Resumen a&ntilde;o
                                    </label>
                                </div>
                                <input type="number" id="year1" name="year" onkeydown="return event.keyCode !== 69"
                                    class="form-control" placeholder="Year" value="2019" />
                                <button class="btn btn-lg btn-light btn-block" type="Submit"
                                    style="background-color: #3B0B2E; color: #ffffff">Seleccionar</button>
                            </fieldset>
                        </form>
                        <c:if test="${shifts != null}">
                            <form action="/admin/statistics/download/shifts" method="POST">
                                <button class="btn btn-lg btn-light btn-block" type="Submit"
                                    style="background-color: #8A0868; color: #ffffff">Descargar</button>
                            </form>
                        </c:if>
                    </div>

                    <div class="col">
                        <%--Show shift tables--%>
                        <c:if test="${shifts != null}">
                            <br />
                            <h3>Operadores en a&ntilde;o ${shifts.year}</h3>
                            <c:forEach var="data" items="${shifts.dataList}">
                                <div class="table-responsive">
                                    <table class="table table-striped" cellspacing="15">
                                        <caption style="caption-side: top">${data.description()}</caption>
                                        <%--Row header--%>
                                        <thead style="background-color:  #8A0868; color: #ffffff">
                                            <tr>
                                                <c:forEach var="normal_rH" items="${data.normal.rowHeader}">
                                                    <th>${normal_rH}</th>
                                                </c:forEach>
                                                <c:forEach var="aggregate_rH" items="${data.aggregate.rowHeader}">
                                                    <th class="aggregate">${aggregate_rH}</th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%--Column header + values--%>
                                            <c:forEach var="row" begin="0" end="${fn:length(shifts.columnHeader) - 1}">
                                                <tr>
                                                    <td>
                                                        <span
                                                            style="font-weight:bold">${shifts.columnHeader[row]}</span>
                                                    </td>
                                                    <c:forEach var="normal_value" items="${data.normal.values[row]}">
                                                        <td bgcolor="${normal_value == Statistics.WEEKEND ? '#FAF4F8' : ''}">
                                                            <c:if test="${normal_value > 0}">
                                                                ${normal_value}
                                                            </c:if>
                                                        </td>
                                                    </c:forEach>
                                                    <c:forEach var="aggregate_value" begin="0" end="1"
                                                        items="${data.aggregate.values[row]}">
                                                        <td class="aggregate">
                                                            <c:if test="${aggregate_value > 0}">
                                                                ${aggregate_value}
                                                            </c:if>
                                                        </td>
                                                    </c:forEach>
                                                    <c:forEach var="aggregate_value" begin="2" end="2"
                                                        items="${data.aggregate.values[row]}">
                                                        <td class="aggregate">
                                                            <c:if test="${aggregate_value > 0}">
                                                                ${aggregate_value}%
                                                            </c:if>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--MODAL-->
                                <div class="modal fade" id="Shiftmodal${data.month}" role="dialog" tabindex="-1">
                                    <div class="modal-dialog modal-xl" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">Trabajo estadistica</h4>
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphNormal}" style="width:100%">
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphSum}" style="width:100%">
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphAverage}" style="width:100%">
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphPercentage}" style="width:100%">
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-light" data-dismiss="modal"
                                                    style="background-color: #610B4B; color: #ffffff">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-lg btn-light btn-block"
                                    style="background-color: #610B4B; color: #ffffff"
                                    onclick="showModal('Shift', this);" value="${data.month}">Mostrar grafica</button>
                            </c:forEach>
                        </c:if>
                    </div>

                </div>
                <!--row1-->

                <div class="row">
                    </br>
                    </hr>
                </div>

                <div class="row justify-content-md-center">
                    <!--row1-->
                    <!--SHIFTS STATISTICS-->
                    <div class="col">
                        <form autocomplete="off" action="/admin/statistics/overwork" method="POST"
                            class="form-horizontal" role="form">
                            <h3>Estadisticas exceso de trabajo</h3>
                            <fieldset>
                                <select name="printers[]" multiple="multiple" tabindex="1">
                                    <c:forEach var="printer" items="${allPrinters}">
                                        <option value="${printer.id}">
                                            <c:out value="${printer.name}" />
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="btn-group" data-toggle="buttons">
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_jan" value="1"
                                            autocomplete="off"> ene
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_feb" value="2"
                                            autocomplete="off"> feb
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_mar" value="3"
                                            autocomplete="off"> mar
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_apr" value="4"
                                            autocomplete="off"> abr
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_may" value="5"
                                            autocomplete="off"> may
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_jun" value="6"
                                            autocomplete="off"> jun
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_jul" value="7"
                                            autocomplete="off"> jul
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_aug" value="8"
                                            autocomplete="off"> ago
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_sep" value="9"
                                            autocomplete="off"> sep
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_oct" value="10"
                                            autocomplete="off"> oct
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_nov" value="11"
                                            autocomplete="off"> nov
                                    </label>
                                    <label class="btn btn-outline-dark">
                                        <input type="checkbox" name="overwork_months[]" id="overwork_dec" value="12 ">
                                        dic
                                    </label>
                                </div>
                                <label class="btn btn-outline-dark">
                                    <input type="checkbox" onclick="toggle(this, 'OVERWORK');"> Alternar todo
                                </label>

                                <%--&lt;%&ndash;Year Overview&ndash;%&gt;
                                <div class="checkbox checkbox-primary">
                                    <input id="overwork_yearOverview" name="overwork_yearOverview" class="styled" type="checkbox">
                                    <label for="overwork_yearOverview">
                                        Resumen a&ntilde;o
                                    </label>
                                </div>--%>
                                <input type="number" id="year1" name="year" onkeydown="return event.keyCode !== 69"
                                    class="form-control" placeholder="Year" value="2019" />
                                <button class="btn btn-lg btn-light btn-block" type="Submit"
                                    style="background-color: #3B0B2E; color: #ffffff">Seleccionar</button>
                            </fieldset>
                        </form>
                        <c:if test="${overworks != null}">
                            <form action="/admin/statistics/download/overwork" method="POST">
                                <button class="btn btn-lg btn-light btn-block" type="Submit"
                                    style="background-color: #8A0868; color: #ffffff">Descargar</button>
                            </form>
                        </c:if>
                    </div>

                    <div class="col">
                        <%--Show shift tables--%>
                        <c:if test="${overworks != null}">
                            <br />
                            <h3>Operadores en a&ntilde;o ${overworks.year}</h3>
                            <c:forEach var="data" items="${overworks.dataList}">
                                <div class="table-responsive">
                                    <table class="table table-striped" cellspacing="15">
                                        <caption style="caption-side: top">${data.description()}</caption>
                                        <%--Row header--%>
                                        <thead style="background-color:  #8A0868; color: #ffffff">
                                            <tr>
                                                <c:forEach var="normal_rH" items="${data.normal.rowHeader}">
                                                    <th>${normal_rH}</th>
                                                </c:forEach>
                                                <c:forEach var="aggregate_rH" items="${data.aggregate.rowHeader}">
                                                    <th class="aggregate">${aggregate_rH}</th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%--Column header + values--%>
                                            <c:forEach var="row" begin="0"
                                                end="${fn:length(overworks.columnHeader) - 1}">
                                                <tr>
                                                    <td>
                                                        <span
                                                            style="font-weight:bold">${overworks.columnHeader[row]}</span>
                                                    </td>
                                                    <c:forEach var="normal_value" items="${data.normal.values[row]}">
                                                        <td bgcolor="${normal_value == Statistics.WEEKEND ? '#FAF4F8' : ''}">
                                                            <c:if test="${normal_value != null && normal_value != Statistics.WEEKEND}">
                                                                ${normal_value}
                                                            </c:if>
                                                        </td>
                                                    </c:forEach>
                                                    <c:forEach var="aggregate_value" begin="0" end="1"
                                                        items="${data.aggregate.values[row]}">
                                                        <td class="aggregate">
                                                            ${aggregate_value}
                                                        </td>
                                                    </c:forEach>
                                                    <c:forEach var="aggregate_value" begin="2" end="2"
                                                        items="${data.aggregate.values[row]}">
                                                        <td class="aggregate">
                                                            ${aggregate_value}%
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--MODAL-->
                                <div class="modal fade" id="Overworkmodal${data.month}" role="dialog" tabindex="-1">
                                    <div class="modal-dialog modal-xl" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">Trabajo estadistica</h4>
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphNormal}" style="width:100%">
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphSum}" style="width:100%">
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphAverage}" style="width:100%">
                                            </div>
                                            <div class="modal-body mb-0 p-0">
                                                <img src="${data.graphPercentage}" style="width:100%">
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-light" data-dismiss="modal"
                                                    style="background-color: #610B4B; color: #ffffff">Cerrar</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-lg btn-light btn-block"
                                    style="background-color: #610B4B; color: #ffffff"
                                    onclick="showModal('Overwork', this);" value="${data.month}">Mostrar
                                    grafica</button>
                            </c:forEach>
                        </c:if>
                    </div>

                </div>
                <!--row1-->

            </div>
            <!--col1-->

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