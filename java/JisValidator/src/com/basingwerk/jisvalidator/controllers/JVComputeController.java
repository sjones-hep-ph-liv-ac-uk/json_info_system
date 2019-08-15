package com.basingwerk.jisvalidator.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;

//import org.everit.json.schema.*;
import com.basingwerk.jisvalidator.checkers.ComputeChecker;
import com.basingwerk.jisvalidator.checkers.Result;
import com.basingwerk.jisvalidator.schema.SchemaHashMap;

/**
 * Servlet implementation class JVMainController
 */
@WebServlet("/JVComputeController")
public class JVComputeController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public JVComputeController() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RequestDispatcher rd = null;
    Logger logger = Logger.getLogger(JVComputeController.class);

    String json = request.getParameter("jistext");
    HttpSession session = request.getSession(true);
    String schemaVersion = (String) session.getAttribute("schemaVersion");
    SchemaHashMap shm = new SchemaHashMap("crrschema_([\\d.]+)\\.json");
    Schema schema = shm.get(schemaVersion);

    ComputeChecker checker = new ComputeChecker(json, schema);
    Result result = checker.check();

    if (result.getCode() == Result.OK) {
      request.setAttribute("theMessage", "No errors");
      rd = request.getRequestDispatcher("/JVResultPage.jsp");
      rd.forward(request, response);
      return;

    } else {
      if (result.getDescription().length() > 0) 
        request.setAttribute("theMessage", result.getDescription());
      else 
        request.setAttribute("theMessage", "Unknown error");
      rd = request.getRequestDispatcher("/JVErrorPage.jsp");
      rd.forward(request, response);
      return;

    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGet(request, response);
  }
}
