package com.basingwerk.jisvalidator.schema;

import org.apache.log4j.Logger;

import com.basingwerk.jisvalidator.controllers.JVComputeController;

public class SchemaDbCrr {

  private SchemaDbHashMap sdb;

  private SchemaDbCrr() {
    Logger logger = Logger.getLogger(SchemaDbCrr.class);
    logger.fatal("Making the CRR SchemaDbHashMap ");
    sdb = new SchemaDbHashMap("crrschema_([\\d.]+)\\.json");
  }

  private static SchemaDbCrr SINGLE_INSTANCE = null;

  public static SchemaDbCrr getInstance() {
    Logger logger = Logger.getLogger(SchemaDbCrr.class);
    logger.fatal("Getting an instance of SchemaDbCrr");

    if (SINGLE_INSTANCE == null) {
      synchronized (SchemaDbCrr.class) {
        SINGLE_INSTANCE = new SchemaDbCrr();
      }
    }
    return SINGLE_INSTANCE;
  }

  public SchemaDbHashMap getSdb() {
    return sdb;
  }
}