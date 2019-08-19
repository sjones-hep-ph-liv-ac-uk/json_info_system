package com.basingwerk.jisvalidator.schema;

import org.apache.log4j.Logger;

public class SrrFinder {
  // SIngleton to find the SRR schemas anywhere in application 

  private SchemaDb sdb;
  
  private SrrFinder() {
    Logger logger = Logger.getLogger(SrrFinder.class);
    logger.fatal("Making the SRR SchemaDbHashMap ");
    sdb = new SchemaDb("srrschema_([\\d.]+)\\.json");
  }

  private static SrrFinder SINGLE_INSTANCE = null;

  public static SrrFinder getInstance() {
    Logger logger = Logger.getLogger(CrrFinder.class);
    logger.fatal("Getting an instance of SchemaDbSrr");
    if (SINGLE_INSTANCE == null) {
      synchronized (SrrFinder.class) {
        SINGLE_INSTANCE = new SrrFinder();
      }
    }
    return SINGLE_INSTANCE;
  }

  public SchemaDb getSchemaDb() {
    return sdb;
  }
}