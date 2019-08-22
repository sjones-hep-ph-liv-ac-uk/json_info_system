<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.basingwerk.jisvalidator.schema.SchemaDbCrr"%>
<%@page import="com.basingwerk.jisvalidator.schema.SchemaDbSrr"%>
<%@page import="com.basingwerk.jisvalidator.schema.SchemaDb"%>
<%@page import="com.basingwerk.jisvalidator.schema.SchemaHolder"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>

      <head>
        <title>JVMain</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap -->
        <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <!-- Le styles -->
    <link href=href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"  rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href=href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"  rel="stylesheet">
  </head>

<body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">JSON Information System Validation</a>
        </div>
      </div>
    </div>

   <div class="container">

		<%
		  SchemaDbCrr crrDb = SchemaDbCrr.getInstance();
		  List crrkeys = crrDb.getKeys();
		  request.setAttribute("crrkeys", crrkeys);

		  SchemaDbSrr srrDb = SchemaDbSrr.getInstance();
		  List srrkeys = srrDb.getKeys();
		  request.setAttribute("srrkeys", srrkeys);
		%>

		<form name="JVMain" action="JVMainController" method="post">
		<h2>Welcome to the JSON information system validation website</h2>
		<h3>Chose an action, the schema version to use, and whether to make an optional integrity check</h3>
		<br>

		<form name="JVMain" action="JVMainController" method="post">
			<table border=1>
				<tr>
					<td><label class='radiolabel'> <input type="radio"
							name="choiceofoption" required="yes"
							value="Check compute JSON file" />Check compute JSON file</td>
					<td>
					<select id="useCrrSchema" name="useCrrSchema">
							<c:forEach var="crrkey" items="${crrkeys}">
								<option value="${crrkey}">${crrkey}</option>
							</c:forEach>
					</select>
					</td>
					<td>Check integrity <input type="checkbox" name="checkCrrIntegrity" value="yes"> </td>
				</tr>
				<tr>
					<td>
				</label>
				<label class='radiolabel'> <input type="radio"
					name="choiceofoption" value="Check storage JSON file" />Check
					storage JSON file 
				</td>
					<td>
					<select id="useSrrSchema" name="useSrrSchema">
							<c:forEach var="srrkey" items="${srrkeys}">
								<option value="${srrkey}">${srrkey}</option>
							</c:forEach>
					</select>
					</td>
					<td>Check integrity <input type="checkbox" name="checkSrrIntegrity" value="yes"> </td>
				</tr>
				<tr>
					<td></label> <label class='radiolabel'> <input type="radio"
							name="choiceofoption" value="View the compute schema" />View the
							compute schema </td>
					<td>
					<select id="seeCrrSchema" name="seeCrrSchema">
							<c:forEach var="crrkey" items="${crrkeys}">
								<option value="${crrkey}">${crrkey}</option>
							</c:forEach>
					</select>
					</td>
					<td> </td>
				</tr>

				<tr>
					<td>
				</label>
				<label class='radiolabel'> <input type="radio"
					name="choiceofoption" value="View the storage schema" />View the
					storage schema 
				</td>
					<td>
					<select id="seeSrrSchema" name="seeSrrSchema">
							<c:forEach var="srrkey" items="${srrkeys}">
								<option value="${srrkey}">${srrkey}</option>
							</c:forEach>
					</select>
					</td>
					<td></td>
				</tr>
			</table>
			</label> <input type="submit" value="Chose" />
		</form>
		
     </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/bootstrap/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-transition.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-alert.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-modal.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-dropdown.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-scrollspy.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-tab.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-tooltip.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-popover.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-button.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-collapse.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-carousel.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap-typeahead.js"></script>
		
</body>
</html>
