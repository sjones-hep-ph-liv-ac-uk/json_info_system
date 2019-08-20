package com.basingwerk.jisvalidator.newschema;

import org.apache.log4j.Logger;

public class SchemaDbSrr {

  private SchemaDb sdb;
  
  private SchemaDbSrr() {
    Logger logger = Logger.getLogger(SchemaDbSrr.class);
    logger.debug("Making the SRR SchemaDbHashMap ");
    sdb = new SchemaDb ("srrschema_([\\d.]+)\\.json");
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

  public SchemaDb getSdb() {
    return sdb;
  }
}
