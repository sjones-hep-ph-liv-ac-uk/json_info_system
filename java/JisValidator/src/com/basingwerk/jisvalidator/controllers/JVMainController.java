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

import org.apache.log4j.Logger;

//import org.everit.json.schema.Schema;
//import org.everit.json.schema.ValidationException;
//import org.everit.json.schema.loader.SchemaLoader;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//
//import com.basingwerk.jisvalidator.checkers.Checker;
//import com.basingwerk.jisvalidator.checkers.ComputeIntegrityChecker;
//import com.basingwerk.jisvalidator.checkers.Result;
//
//import org.apache.tomcat.util.http.fileupload.IOUtils;


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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		Logger logger = Logger.getLogger(JVMainController.class);
		// logger.fatal(json);

		String opt = request.getParameter("choiceofoption");
	    // compare selected value 
	    if ("Check compute JSON file".equals(opt)) {
			rd = request.getRequestDispatcher("/JVCompute.jsp");
			rd.forward(request, response);
			return;
	    }
	    else if ("View the current schema".equals(opt)) {
			InputStream inputStream = this.getClass().getResourceAsStream("/schema.json");
			Scanner s = new Scanner(inputStream).useDelimiter("\\A");
			String result = s.hasNext() ? s.next() : "";
			request.setAttribute("theSchema", result);
			rd = request.getRequestDispatcher("/JVViewSchema.jsp");
			rd.forward(request, response);
			return;
			
	    }
	    else {
	    	response.getWriter().println("Not impleneted yet");
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

