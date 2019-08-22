package com.basingwerk.jisvalidator.checkers;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    String sniffTest = SniffTest.check(jsonToCheck);
    if (sniffTest.length() > 0)
      return new Result(Result.NOTWELLFORMED, "JSON was not well formed " + sniffTest);

    try {
      schema.validate(new JSONObject(jsonToCheck));
    } catch (Exception e) {
      return new Result(Result.SCHEMAFAULT, "Validator error: " + e.getMessage());
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
      return new Result(Result.PROGRAMFAULT, "Program error: " + e.getMessage());
    }
  }

}
