package com.basingwerk.jisvalidator.checkers;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.basingwerk.jisvalidator.schema.SchemaHashMap;

public class StorageChecker {

  private String jsonToCheck;
  private Schema schema;
  private Logger logger;

  public StorageChecker(String j, Schema s) {
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

    // It's well formed JSON, and it complies with the schema. Does it have
    // referential integrity?
    try {
      StorageSemanticChecker rc = new StorageSemanticChecker(jsonToCheck);
      String result = rc.check();

      if (result.length() != 0)
        return new Result(Result.INTEGRITYFAULT, result);

      return new Result(Result.OK, "no errors");

    } catch (CheckerException e) {
      error = e.getMessage();
      return new Result(Result.PROGRAMFAULT, error);
    }
  }
}
