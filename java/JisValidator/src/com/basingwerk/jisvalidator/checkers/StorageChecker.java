package com.basingwerk.jisvalidator.checkers;

import java.io.InputStream;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class StorageChecker {

	private String json;

	public StorageChecker(String j) {
		this.json = j;
	}

	public Result check() {

		InputStream is = this.getClass().getResourceAsStream("/srrschema.json");

		String error = "No error";
		try {
			JSONObject rawSchema = new JSONObject(new JSONTokener(is));
			Schema schema = SchemaLoader.load(rawSchema);
			schema.validate(new JSONObject(json));
		} catch (Exception e) {
			error = e.getMessage();
			return new Result(Result.SCHEMAFAULT, error);
		}
		// It's well formed JSON, and it complies with the schema. Does it have
		// referential integrity?
		ComputeIntegrityChecker rc = new ComputeIntegrityChecker(json);
		String result = rc.check();
		if (result.length() == 0) {
			return new Result(Result.OK, "no errors");
		} else {
			return new Result(Result.INTEGRITYFAULT, result);

		}

	}
}
