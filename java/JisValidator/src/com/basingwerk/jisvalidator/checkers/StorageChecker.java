package com.basingwerk.jisvalidator.checkers;

import java.io.InputStream;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class StorageChecker {

  private String jsonToCheck;
  private Schema schema;
  private Logger logger;
  private Boolean checkIntegrity = false;

  public StorageChecker(String j, Schema s, String integrity) {
    this.jsonToCheck = j;
    this.schema = s;
    logger = Logger.getLogger(StorageChecker.class);
    if (integrity.equals("yes"))
      checkIntegrity = true;
  }

  public Result check() {

    String error = "No error";
    try {
      schema.validate(new JSONObject(jsonToCheck));
    } catch (Exception e) {
      error = e.getMessage();
      return new Result(Result.SCHEMAFAULT, "Validator error: " + error);
    }

    // It's well formed JSON, and it complies with the schema. Does it have
    // referential integrity?
    if (checkIntegrity == false)
      return new Result(Result.OK, "Validator found no errors");

    try {
      StorageIntegrityChecker rc = new StorageIntegrityChecker(jsonToCheck);
      String result = rc.check();

      if (result.length() != 0)
        return new Result(Result.INTEGRITYFAULT, "Integrity error: " + result);

      return new Result(Result.OK, "Validator and integrity checker found no errors");

    } catch (CheckerException e) {
      error = e.getMessage();
      return new Result(Result.PROGRAMFAULT, "Program error: " + error);
    }
  }
}
