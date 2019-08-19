package com.basingwerk.jisvalidator.schema;

import org.everit.json.schema.Schema;

public class SchemaHolder {
  // Holds text and processed versions of the schema
  
  private Schema schema;
  private String schemaText; 

  public SchemaHolder(Schema schema, String schemaText) {
    this.schema = schema;
    this.schemaText = schemaText;
  }
  public Schema getSchema() {
    return schema;
  }
  public void setSchema(Schema schema) {
    this.schema = schema;
  }
  public String getSchemaText() {
    return schemaText;
  }
  public void setSchemaText(String schemaText) {
    this.schemaText = schemaText;
  }

}
