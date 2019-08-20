package com.basingwerk.jisvalidator.checkers;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.json.JSONObject;

public class ComputeChecker {

  private String jsonToCheck;
  private Schema schema;
  private Logger logger;
  private Boolean checkIntegrity = false;

  public ComputeChecker(String j, Schema s, String integrity) {
    this.jsonToCheck = j;
    this.schema = s;
    logger = Logger.getLogger(ComputeChecker.class);
    if (integrity.equals("yes"))
      checkIntegrity = true;
  }

  public Result check() {

    String error = "No error";
    try {
      schema.validate(new JSONObject(jsonToCheck));
    } catch (Exception e) {
      error = "Validator error: " + e.getMessage();
      return new Result(Result.SCHEMAFAULT, error);
    }

    if (checkIntegrity == false)
      return new Result(Result.OK, "Validator found no errors");

    // It's well formed JSON, and it complies with the schema. Does it have semantic
    // integrity?
    try {
      ComputeIntegrityChecker checker = new ComputeIntegrityChecker(jsonToCheck);
      String result = checker.check();

      if (result.length() != 0)
        return new Result(Result.INTEGRITYFAULT, "Integrity error: " + result);

      return new Result(Result.OK, "Validator and integrity checker found no errors");

    } catch (Exception e) {
      error = e.getMessage();
      return new Result(Result.PROGRAMFAULT, "Program error: " + error);
    }
  }
}
