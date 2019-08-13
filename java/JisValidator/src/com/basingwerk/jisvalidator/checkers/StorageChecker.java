package com.basingwerk.jisvalidator.checkers;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.basingwerk.jisvalidator.schema.CombinedSchemas;
import com.basingwerk.jisvalidator.schema.SchemaHashMap;

public class StorageChecker {

  private String json;
  private Logger logger;

  public StorageChecker(String j) {
    logger = Logger.getLogger(StorageChecker.class);
    this.json = j;
  }

  public Result check() {

//    InputStream is = this.getClass().getResourceAsStream("/srrschema_4.1.json");
//    JSONObject rawSchema = new JSONObject(new JSONTokener(is));
//    Schema schema = SchemaLoader.load(rawSchema);

    SchemaHashMap shm = new SchemaHashMap("srrschema_([\\d.]+)\\.json");
    String latest = shm.findLatestVersion();
    Schema schema = shm.get(latest);

    String error = "No error";
    try {
      schema.validate(new JSONObject(json));
    } catch (Exception e) {
      error = e.getMessage();
      return new Result(Result.SCHEMAFAULT, error);
    }

    // It's well formed JSON, and it complies with the schema. Does it have
    // referential integrity?
    try {
      StorageSemanticChecker rc = new StorageSemanticChecker(json);
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
