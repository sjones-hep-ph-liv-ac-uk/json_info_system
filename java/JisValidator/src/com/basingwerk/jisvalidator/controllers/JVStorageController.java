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
import com.basingwerk.jisvalidator.checkers.StorageChecker;
import com.basingwerk.jisvalidator.schema.SchemaHolder;
import com.basingwerk.jisvalidator.schema.CrrFinder;
import com.basingwerk.jisvalidator.schema.SchemaDb;
import com.basingwerk.jisvalidator.schema.SrrFinder;

import com.basingwerk.jisvalidator.checkers.ComputeChecker;
import com.basingwerk.jisvalidator.checkers.Result;

/**
 * Servlet implementation class JVMainController
 */
@WebServlet("/JVStorageController")
public class JVStorageController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public JVStorageController() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Logger logger = Logger.getLogger(JVStorageController.class);
    RequestDispatcher rd = null;
 
    String json = request.getParameter("jistext");

    HttpSession session = request.getSession(true);
    String schemaVersion = (String) session.getAttribute("schemaVersion");
    
    SrrFinder s = SrrFinder.getInstance();
    SchemaDb db = s.getSchemaDb();
    SchemaHolder sc = db.get(schemaVersion);
    Schema schema = sc.getSchema(); 
    
    String integrity = (String) session.getAttribute("checkSrrIntegrity");
    StorageChecker checker = new StorageChecker(json, schema, integrity);

    Result result = checker.check();

    if (result.getCode() == Result.OK) {
      request.setAttribute("theMessage", result.getDescription());
      rd = request.getRequestDispatcher("/JVResultPage.jsp");
      rd.forward(request, response);
      return;

    } else {
      if (result.getDescription().length() > 0)
        request.setAttribute("theMessage", result.getDescription());
      else
        request.setAttribute("theMessage", "Unknown error");
      request.setAttribute("theMessage", result.getDescription());
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
