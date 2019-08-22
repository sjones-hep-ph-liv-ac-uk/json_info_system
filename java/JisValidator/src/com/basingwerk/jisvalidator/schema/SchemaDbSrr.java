package com.basingwerk.jisvalidator.schema;

import org.apache.log4j.Logger;

public class SchemaDbSrr extends SchemaDb {

  private static final long serialVersionUID = 1L;

  private SchemaDbSrr() {
    super("srrschema_([\\d.]+)\\.json");
    Logger logger = Logger.getLogger(SchemaDbSrr.class);
    logger.debug("Constructing the SRR SchemaDb");
  }

  private static SchemaDbSrr SINGLE_INSTANCE = null;

  public static SchemaDbSrr getInstance() {
    Logger logger = Logger.getLogger(SchemaDbCrr.class);
    logger.debug("Getting an instance of SchemaDbSrr");
    if (SINGLE_INSTANCE == null) {
      synchronized (SchemaDbSrr.class) {
        SINGLE_INSTANCE = new SchemaDbSrr();
      }
    }
    return SINGLE_INSTANCE;
  }
}
