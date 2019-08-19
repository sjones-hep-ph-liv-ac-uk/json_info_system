package com.basingwerk.jisvalidator.schema;

import org.apache.log4j.Logger;

import com.basingwerk.jisvalidator.controllers.JVComputeController;

public class CrrFinder {
  // SIngleton to find the CRR schemas anywhere in application 

  private SchemaDb sdb;

  private CrrFinder() {
    Logger logger = Logger.getLogger(CrrFinder.class);
    logger.debug("Making the CRR SchemaDbHashMap ");
    sdb = new SchemaDb("crrschema_([\\d.]+)\\.json");
  }

  private static CrrFinder SINGLE_INSTANCE = null;

  public static CrrFinder getInstance() {
    Logger logger = Logger.getLogger(CrrFinder.class);
    logger.debug("Getting an instance of SchemaDbCrr");

    if (SINGLE_INSTANCE == null) {
      synchronized (CrrFinder.class) {
        SINGLE_INSTANCE = new CrrFinder();
      }
    }
    return SINGLE_INSTANCE;
  }

  public SchemaDb getSchemaDb() {
    return sdb;
  }
}
