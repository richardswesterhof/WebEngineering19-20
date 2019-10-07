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
    <title>Admin Tools</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="<spring:url value="/static/css/style.css"/>"/>
    <script type="text/javascript" src="<spring:url value="/static/js/admin.js"/>"></script>
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
        <form action="/logout" method="POST" class="form-inline my-2 my-lg-0">
            <button class="btn btn-outline-light my-2 my-sm-0" type="submit" style="background-color: #610B4B; color: #ffffff">Logout</button>
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
            <h2 class="display-3 text-center">Admin Tools</h2>

            <div class="row justify-content-md-center">

                <!--row1-->
                </br>


                <!-------- REGISTRATE USER -------->
                <form action="/admin/registration" method="POST" class="form-signin">
                    <h3>Add user</h3>

                    <div class="form-group">
                        <select id="role" name="role" class="form-control">
                            <!-- A drop down list allows user to select which role  -->
                            <option value="PRINTER">OPERATOR</option>
                            <option value="ADMIN">ADMIN</option>
                        </select>
                        <input type="text" id="username" name="username" class="form-control mt-3" required=""
                               placeholder="New admin/printer" />
                        <input type="text" id="password" name="password" class="form-control mt-3" required=""
                               placeholder="Password" />
                        <button class="btn btn-lg btn-light btn-block" type="Submit" style="background-color: #610B4B; color: #ffffff">Register</button>
                        <p>${regMes}</p>
                    </div>
                </form>

                <div class="col-md-1"></div>

                <!-------- ADD OPERATORS -------->
                <form autocomplete="off" action="/admin/addOperator" method="POST"
                      class="form-horizontal" role="form">
                    <h3>Add operator</h3>

                    <input type="text" name="name" id="name" class="form-control mt-3"
                           placeholder="Insert operator name" />
                    <button class="btn btn-lg btn-light btn-block" type="Submit" style="background-color: #3B0B2E; color: #ffffff">Insert</button>

                    <p>${opMes}</p>
                </form>

                <div class="col-md-1"></div>

                <!-------- MANAGE OPERATORS -------->
                <form autocomplete="off" action="/admin/manage" method="POST"
                      class="form-horizontal" role="form">
                    <h3>Manage operators</h3>
                    <fieldset>
                        <select id="managedOperatorId" name="managedOperatorId" class="form-control">
                            <option value="" disabled hidden selected>Select operator</option>
                            <c:forEach var="operator" items="${allOperators}">
                                <option value="${operator.id}"><c:out value="${operator.name}"/></option>
                            </c:forEach>
                        </select>
                        <input type="text" name="newName" id="newName" class="form-control mt-3"
                               placeholder="New Operator Name"/>
                    </fieldset>

                    <button class="btn btn-lg btn-light btn-block" type="Submit" name="action" value="RENAME" style="background-color: #3B0B2E; color: #ffffff">Rename</button>
                    <button type="button" id="removebtnModal" class="btn btn-lg btn-light btn-block" style="background-color: #8A0868; color: #ffffff">Remove</button>
                    <!--MODAL: confirm to remove-->
                    <div class="modal fade" id="removemodal" role="dialog" tabindex="-1">
                        <div class="modal-dialog modal-md" role="document">
                            <div class="modal-content" >
                                <div class="modal-header">
                                    <h4 class="modal-title">Caution! Operator removal</h4>
                                </div>
                                <div class="modal-body">
                                    <p>Please, confirm to remove the operator selected.</p>
                                    <button class="btn btn-md btn-light btn-block" type="Submit" name="action" value="REMOVE" style="background-color: #3B0B2E; color: #ffffff">Confirm removal</button>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-light" data-dismiss="modal" style="background-color: #8A0868; color: #ffffff">Back</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--END MODAL-->
                    <!--<button class="btn btn-lg btn-light btn-block" type="Submit" name="action" value="REMOVE" style="background-color: #8A0868; color: #ffffff">Remove</button>-->
                </form>
            </div>
            <!--row1-->

            <div class="row">
                </br>
                </hr>
            </div>


            <div class="row justify-content-md-center">
                <!--row1-->

                <!--JOBS STATISTICS-->
                <div class="col">
                    <form autocomplete="off" action="/admin/statistics/jobs" method="POST"
                          class="form-horizontal" role="form">
                        <h3>Jobs statistics</h3>
                        <fieldset>
                            <select id="id" name="id" class="form-control">
                                <option value="" disabled hidden selected>Select printer or operator</option>
                                <option value="" disabled>---Operators---</option>
                                <c:forEach var="operator" items="${allOperators}">
                                    <option value="${operator.id}"><c:out value="${operator.name}"/></option>
                                </c:forEach>
                                <option value="" disabled>---Printers---</option>
                                <c:forEach var="printer" items="${allPrinters}">
                                    <option value="${printer.id}"><c:out value="${printer.name}"/></option>
                                </c:forEach>
                                <option value="" disabled>-----------</option>
                                <option value="-1">All printers/operators</option>
                            </select>
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="jan" value="1" autocomplete="off"> jan
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="feb" value="2" autocomplete="off"> feb
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="mar" value="3" autocomplete="off"> mar
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="apr" value="4" autocomplete="off"> apr
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="may" value="5" autocomplete="off"> may
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="jun" value="6" autocomplete="off"> jun
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="jul" value="7" autocomplete="off"> jul
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="aug" value="8" autocomplete="off"> aug
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="sep" value="9" autocomplete="off"> sep
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="oct" value="10" autocomplete="off"> oct
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="nov" value="11" autocomplete="off"> nov
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="dec" value="12 " autocomplete="off"> dec
                                </label>
                            </div>
                            <input type="number" id="year" name="year" onkeydown="return event.keyCode !== 69"
                                   class="form-control" placeholder="Year" value="2019"/>
                            <button class="btn btn-lg btn-light btn-block" type="Submit" style="background-color: #3B0B2E; color: #ffffff">Select</button>
                        </fieldset>
                    </form>
                    <c:if test="${jobs != null}">
                        <button type="button" id="JobbtnModal" class="btn btn-lg btn-light btn-block" style="background-color: #610B4B; color: #ffffff">Show graph</button>
                        <!--MODAL-->
                        <div class="modal fade" id="Jobmodal" role="dialog" tabindex="-1">
                            <div class="modal-dialog modal-xl" role="document">
                                <div class="modal-content" >
                                    <div class="modal-header">
                                        <h4 class="modal-title">Jobs statistics</h4>
                                    </div>
                                    <div class="modal-body mb-0 p-0">
                                        <img src="${link}" style="width:100%">
                                    </div>
                                    <div class="modal-body mb-0 p-0">
                                        <img src="${link2}" style="width:100%">
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-light" data-dismiss="modal" style="background-color: #610B4B; color: #ffffff">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--END MODAL-->

                        <form action="/admin/download/jobs" method="POST">
                            <button class="btn btn-lg btn-light btn-block" type="Submit" style="background-color: #8A0868; color: #ffffff">Download</button>
                        </form>
                    </c:if>
                </div>

                <div class="col">
                    <c:if test="${jobs != null}">
                        <br/>
                        <h3>${jobs.downloadable.name}:
                            ${jobs.month==null?'':jobs.month} ${jobs.year}</h3>
                        <table class="table table-striped" id="job_table" cellspacing="15">
                            <tr>
                                <c:forEach var="data" items="${jobs.table.rowHeader}">
                                    <th>${data}</th>
                                </c:forEach>
                            </tr>
                            <c:forEach var="row" begin="0" end="${fn:length(jobs.table.columnHeader) - 1}">
                                <tr>
                                    <td>
                                        <span style="font-weight:bold">${jobs.table.columnHeader[row]}</span>
                                    </td>
                                    <c:forEach var="hour" items="${jobs.table.data[row]}">
                                        <td>
                                            <c:if test="${hour == -1}">X</c:if>
                                            <c:if test="${hour > 0}">${hour}</c:if>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>
            </div><!--row1-->

            <div class="row">
                </br>
                </hr>
            </div>

            <div class="row justify-content-md-center"><!--row1-->
                <!--SHIFTS STATISTICS-->
                <div class="col">
                    <form autocomplete="off" action="/admin/statistics/shifts" method="POST"
                          class="form-horizontal" role="form">
                        <h3>Shifts statistics</h3>
                        <fieldset>
                            <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="jan1" value="1" autocomplete="off"> jan
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="feb1" value="2" autocomplete="off"> feb
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="mar1" value="3" autocomplete="off"> mar
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="apr1" value="4" autocomplete="off"> apr
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="may1" value="5" autocomplete="off"> may
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="jun1" value="6" autocomplete="off"> jun
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="jul1" value="7" autocomplete="off"> jul
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="aug1" value="8" autocomplete="off"> aug
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="sep1" value="9" autocomplete="off"> sep
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="oct1" value="10" autocomplete="off"> oct
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="nov1" value="11" autocomplete="off"> nov
                                </label>
                                <label class="btn btn-outline-dark">
                                    <input type="radio" name="month" id="dec1" value="12" autocomplete="off"> dec
                                </label>
                            </div>
                            <input type="number" id="year1" name="year" onkeydown="return event.keyCode !== 69"
                                   class="form-control" placeholder="Year" value="2019"/>
                            <button class="btn btn-lg btn-light btn-block" type="Submit" style="background-color: #3B0B2E; color: #ffffff">Select</button>
                        </fieldset>
                    </form>
                    <c:if test="${shifts != null}">
                        <button type="button" id="ShiftbtnModal" class="btn btn-lg btn-light btn-block" style="background-color: #610B4B; color: #ffffff">Show graph</button>
                        <!--MODAL-->
                        <div class="modal fade" id="Shiftmodal" role="dialog" tabindex="-1">
                            <div class="modal-dialog modal-xl" role="document">
                                <div class="modal-content">
                                    <!--<div class="modal-header">
                                        <h4 class="modal-title">Shifts statistics</h4>
                                    </div> -->
                                    <div class="modal-body mb-0 p-0">
                                        <img src="${link}" style="width:100%">
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-light" data-dismiss="modal" style="background-color: #610B4B; color: #ffffff">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--END MODAL-->

                        <form action="/admin/download/shifts" method="POST">
                            <button class="btn btn-lg btn-light btn-block" type="Submit" style="background-color: #8A0868; color: #ffffff">Download</button>
                        </form>
                    </c:if>
                </div>

                <div class="col">
                    <c:if test="${shifts != null}">
                        <br/>
                        <h3>${shifts.month} ${shifts.year}</h3>
                        <table class="table table-striped" id="shift_table" cellspacing="15">
                            <tr>
                                <c:forEach var="data" items="${shifts.table.rowHeader}">
                                    <th>${data}</th>
                                </c:forEach>
                            </tr>
                            <c:forEach var="row" begin="0" end="${fn:length(shifts.table.columnHeader) - 1}">
                                <tr>
                                    <td>
                                        <span style="font-weight:bold"> ${shifts.table.columnHeader[row]}</span>
                                    </td>
                                    <c:forEach var="hour" items="${shifts.table.data[row]}">
                                        <td>
                                            <c:if test="${hour == -1}">X</c:if>
                                            <c:if test="${hour > 0}">${hour}</c:if>
                                        </td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </div>

            </div> <!--row1-->

            <div class="row">
                </br>
                </hr>
            </div>
        </div><!--col1-->

        <div class="col">
        </div>

    </div><!--row0-->
</div><!--container-->

<hr/>

<!--FOOTER-->
<footer class="footer mt-auto text-center">
    <div class="inner">
        <p>Developed by students of <a href="https://www.rug.nl/">University of Groningen</a></p>
    </div>
</footer>

</body>

</html>