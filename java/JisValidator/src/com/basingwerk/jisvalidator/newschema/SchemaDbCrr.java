package com.basingwerk.jisvalidator.newschema;

import org.apache.log4j.Logger;

import com.basingwerk.jisvalidator.controllers.JVComputeController;

public class SchemaDbCrr extends SchemaDb{

  private SchemaDbCrr () {
    super("crrschema_([\\d.]+)\\.json");
    Logger logger = Logger.getLogger(SchemaDbCrr.class);
    logger.debug("Constructing the CRR SchemaDb");
  }

  private static SchemaDbCrr SINGLE_INSTANCE = null;

  public static SchemaDbCrr getInstance() {
    Logger logger = Logger.getLogger(SchemaDbCrr.class);
    logger.debug("Getting an instance of SchemaDbCrr");

    if (SINGLE_INSTANCE == null) {
      synchronized (SchemaDbCrr.class) {
        SINGLE_INSTANCE = new SchemaDbCrr();
      }
    }
    return SINGLE_INSTANCE;
  }
}
