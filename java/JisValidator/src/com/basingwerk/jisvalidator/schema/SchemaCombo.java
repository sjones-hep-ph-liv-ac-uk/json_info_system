package com.basingwerk.jisvalidator.schema;

import org.everit.json.schema.Schema;

public class SchemaCombo {
  public SchemaCombo(Schema schema, String schemaText) {
    super();
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
  private Schema schema;
  private String schemaText; 

}
