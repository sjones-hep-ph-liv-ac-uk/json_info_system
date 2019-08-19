package com.basingwerk.jisvalidator.schema;

import org.apache.log4j.Logger;

public class SchemaDbSrr {

  private SchemaDbHashMap sdb;
  
  private SchemaDbSrr() {
    Logger logger = Logger.getLogger(SchemaDbSrr.class);
    logger.fatal("Making the SRR SchemaDbHashMap ");
    sdb = new SchemaDbHashMap("srrschema_([\\d.]+)\\.json");
  }

  private static SchemaDbSrr SINGLE_INSTANCE = null;

  public static SchemaDbSrr getInstance() {
    Logger logger = Logger.getLogger(SchemaDbCrr.class);
    logger.fatal("Getting an instance of SchemaDbSrr");
    if (SINGLE_INSTANCE == null) {
      synchronized (SchemaDbSrr.class) {
        SINGLE_INSTANCE = new SchemaDbSrr();
      }
    }
    return SINGLE_INSTANCE;
  }

  public SchemaDbHashMap getSdb() {
    return sdb;
  }
}