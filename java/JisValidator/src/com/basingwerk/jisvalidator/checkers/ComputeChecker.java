package com.basingwerk.jisvalidator.checkers;


import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.json.JSONObject;

import com.basingwerk.jisvalidator.schema.SchemaHashMap;

public class ComputeChecker {

  private String jsonToCheck;
  private Schema schema;
  private Logger logger;

  public ComputeChecker(String j, Schema s) {
    this.jsonToCheck = j;
    this.schema = s;
    logger = Logger.getLogger(ComputeChecker.class);
  }

  public Result check() {

    
    String error = "No error";
    try {
      schema.validate(new JSONObject(jsonToCheck));
    } catch (Exception e) {
      error = e.getMessage();
      return new Result(Result.SCHEMAFAULT, error);
    }

    // It's well formed JSON, and it complies with the schema. Does it have semantic
    // integrity?
    try {
      ComputeSemanticChecker checker = new ComputeSemanticChecker(jsonToCheck);
      String result = checker.check();

      if (result.length() != 0)
        return new Result(Result.INTEGRITYFAULT, result);

      return new Result(Result.OK, "no errors");

    } catch (Exception e) {
      error = e.getMessage();
      return new Result(Result.PROGRAMFAULT, error);
    }
  }
}
