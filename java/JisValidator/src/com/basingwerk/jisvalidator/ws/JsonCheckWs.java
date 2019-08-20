package com.basingwerk.jisvalidator.ws;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import com.basingwerk.jisvalidator.checkers.ComputeChecker;
import com.basingwerk.jisvalidator.checkers.Result;
import com.basingwerk.jisvalidator.checkers.StorageChecker;
import com.basingwerk.jisvalidator.schema.CrrFinder;
import com.basingwerk.jisvalidator.schema.SchemaDb;
import com.basingwerk.jisvalidator.schema.SchemaHolder;
import com.basingwerk.jisvalidator.schema.SrrFinder;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

@Path("/jsoncheckws")
public class JsonCheckWs {

  public JsonCheckWs() {
    super();
    this.logger = Logger.getLogger(JsonCheckWs.class);
  }

  private Logger logger;

  @POST
  @Path("/crr")
  @Consumes("multipart/form-data")
  @Produces("application/json")
  public Response uploadCrrFile(@QueryParam("ver") String ver, @QueryParam("integrity") String integrity,
      MultipartFormDataInput input) {

    Result result;

    CrrFinder finder = CrrFinder.getInstance(); 
    SchemaDb db = finder.getSchemaDb();
    
    if (ver == null)
      ver = db.findLatestVersion();
      
    if (integrity == null)
      integrity = "no";
 
    SchemaHolder sh = db.get(ver);
    if (sh == null) {
      result = new Result(20, "Schema version not found ");
      return Response.status(200).entity(result).build();
    }
    Schema schema = sh.getSchema();
    
    Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
    List<InputPart> inputParts = uploadForm.get("jsonfile");
    if (inputParts == null)
      return Response.status(Status.NOT_FOUND).build();

    for (InputPart inputPart : inputParts) {

      try {

        // Handle the body of that part with an InputStream
        InputStream istream = inputPart.getBody(InputStream.class, null);

        String json = convertStreamToString(istream);
        ComputeChecker checker = new ComputeChecker(json, schema, integrity);
        result = checker.check();
        return Response.status(200).entity(result).build();
      } catch (IOException e) {
      }
    }
    return Response.status(404).build();
  }

  @POST
  @Path("/srr")
  @Consumes("multipart/form-data")
  @Produces("application/json")

  public Response uploadSrrFile(@QueryParam("ver") String ver, @QueryParam("integrity") String integrity,
      MultipartFormDataInput input) {

    Result result;

    SrrFinder finder = SrrFinder.getInstance(); 
    SchemaDb db = finder.getSchemaDb();

    if (ver == null)
      ver = db.findLatestVersion();
    
    if (integrity == null)
      integrity = "no";

    SchemaHolder sh = db.get(ver);
    if (sh == null) {
      result = new Result(20, "Schema version not found ");
      return Response.status(200).entity(result).build();
    }
    Schema schema = sh.getSchema();

    Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
    List<InputPart> inputParts = uploadForm.get("jsonfile");
    if (inputParts == null)
      return Response.status(Status.NOT_FOUND).build();

    for (InputPart inputPart : inputParts) {

      try {

        // Handle the body of that part with an InputStream
        InputStream istream = inputPart.getBody(InputStream.class, null);
        String json = convertStreamToString(istream);
        StorageChecker checker = new StorageChecker(json, schema, integrity);
        result = checker.check();
        return Response.status(200).entity(result).build();

      } catch (IOException e) {
      }
    }
    return Response.status(404).build();
  }

  static String convertStreamToString(java.io.InputStream is) throws IOException {
    String s = IOUtils.toString(is, StandardCharsets.UTF_8);
    return s;
  }
}
