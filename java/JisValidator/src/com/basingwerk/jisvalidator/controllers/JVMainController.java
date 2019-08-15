package com.basingwerk.jisvalidator.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;

import com.basingwerk.jisvalidator.schema.SchemaHashMap;

/**
 * Servlet implementation class JVMainController
 */
@WebServlet("/JVMainController")
public class JVMainController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public JVMainController() {
    super();
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Logger logger = Logger.getLogger(JVMainController.class);
    RequestDispatcher rd = null;

    String opt = request.getParameter("choiceofoption");

    if ("Check compute JSON file".equals(opt)) {
      String schemaVersion = request.getParameter("useCrrSchema");
      HttpSession sess=request.getSession();
      sess.setAttribute("schemaVersion",schemaVersion);
      rd = request.getRequestDispatcher("/JVCompute.jsp");
      rd.forward(request, response);
      return;
      
    } else if ("View the compute schema".equals(opt)) {
      String schemaVersion = request.getParameter("seeCrrSchema");
      HttpSession sess=request.getSession();
      sess.setAttribute("schemaVersion",schemaVersion);
      
      String schemaResource = "/crrschema_"+ schemaVersion + ".json";
      InputStream inputStream = this.getClass().getResourceAsStream(schemaResource);
      Scanner s = new Scanner(inputStream).useDelimiter("\\A");
      String result = s.hasNext() ? s.next() : "";
      
      request.setAttribute("theSchema", result);
      rd = request.getRequestDispatcher("/JVViewSchema.jsp");
      rd.forward(request, response);
      return;

    } else if ("Check storage JSON file".equals(opt)) {
      String schemaVersion = request.getParameter("useSrrSchema");
      HttpSession sess=request.getSession();
      sess.setAttribute("schemaVersion",schemaVersion);
      rd = request.getRequestDispatcher("/JVStorage.jsp");
      rd.forward(request, response);
      
    } else if ("View the storage schema".equals(opt)) {
      String schemaVersion = request.getParameter("seeSrrSchema");
      HttpSession sess=request.getSession();
      sess.setAttribute("schemaVersion",schemaVersion);

      String schemaResource = "/srrschema_"+ schemaVersion + ".json";
      InputStream inputStream = this.getClass().getResourceAsStream(schemaResource);
      Scanner s = new Scanner(inputStream).useDelimiter("\\A");
      String result = s.hasNext() ? s.next() : "";
      request.setAttribute("theSchema", result);
      rd = request.getRequestDispatcher("/JVViewSchema.jsp");
      rd.forward(request, response);
      return;
      
    } else {
      response.getWriter().println("Not implemented yet");
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
