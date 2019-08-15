<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.basingwerk.jisvalidator.schema.SchemaHashMap"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head>
<title>JVMain</title>
</head>

<body>
	<%
	  SchemaHashMap srrshm = new SchemaHashMap("srrschema_([\\d.]+)\\.json");
	  List srrkeys = srrshm.getKeys();
	  request.setAttribute("srrkeys", srrkeys);
	  SchemaHashMap crrshm = new SchemaHashMap("crrschema_([\\d.]+)\\.json");
	  List crrkeys = crrshm.getKeys();
	  request.setAttribute("crrkeys", crrkeys);
	%>

	<form name="JVMain" action="JVMainController" method="post">
		<h2>Welcome to the JSON information system validation website</h2>
		<h3>Options available are as follows.</h3>
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
				</tr>
			</table>
			</label> <input type="submit" value="Chose" />
		</form>
</body>
</html>

