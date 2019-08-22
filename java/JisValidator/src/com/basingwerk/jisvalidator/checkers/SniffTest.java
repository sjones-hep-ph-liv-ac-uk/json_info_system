package com.basingwerk.jisvalidator.checkers;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.jsfr.json.JsonSurfer;

public class SniffTest {

  public static String check(String json) {
    // Basic check just to see that it's well formed. No point bothering with anything else if it doesn't pass this.
    final JsonSurfer surfer = JsonSurfer.gson();
    try {
      surfer.collectAll(json, Object.class, "$");
    } catch (Exception ex) {
      String cause = ExceptionUtils.getRootCauseMessage(ex);
      if (cause.length() == 0) {
        cause = ex.getStackTrace().toString();
      }
        return cause;
    }
    return "";
  }
}
