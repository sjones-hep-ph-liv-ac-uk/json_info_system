package com.basingwerk.jisvalidator.checkers;

import java.io.InputStream;
import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ComputeChecker {

  private String json;
  private Logger logger;

  public ComputeChecker(String j) {
    this.json = j;
    logger = Logger.getLogger(ComputeChecker.class);
  }

  public Result check() {

    InputStream is = this.getClass().getResourceAsStream("/crrschema.json");

    String error = "No error";
    try {
      JSONObject rawSchema = new JSONObject(new JSONTokener(is));
      Schema schema = SchemaLoader.load(rawSchema);
      schema.validate(new JSONObject(json));
    } catch (Exception e) {
      error = e.getMessage();
      return new Result(Result.SCHEMAFAULT, error);
    }
    
    // It's well formed JSON, and it complies with the schema. Does it have semantic integrity?
    try {
      ComputeSemanticChecker checker = new ComputeSemanticChecker(json);
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
