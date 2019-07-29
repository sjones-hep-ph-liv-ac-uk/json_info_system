<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <html>
      <head>
        <title>JVCompute</title>
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

  <form name="JVMain" action="JVComputeController" method="post" >
         Please paste your crr JSON in the text box and click the button:
         <br>
         <textarea rows = "30" cols = "60" name = "jistext" class="span6 input-large search-query">
            ...replace this with the json you want to check ...
         </textarea><br>
         <input type = "submit" value = "Validate" />
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
    