package com.basingwerk.jisvalidator.schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.basingwerk.jisvalidator.utils.VersionSorter;

public class SchemaHashMap extends HashMap<String, Schema> {
  
  private Pattern filePattern;

  public SchemaHashMap(String regex) {
    super();
    // "srrschema_([\\d.]+)\\.json"
    // "crrschema_([\\d.]+)\\.json"
    filePattern = Pattern.compile(regex);
    refresh();
  }

  public void refresh() {

    for (File f : getResourceFolderFiles("./")) {

      InputStream is;
      String fname = f.getName();

      Matcher m = filePattern.matcher(fname);

      if (m.find()) {
        String version = m.group(1);
        try {
          is = new FileInputStream(f);
          JSONObject rawSchema = new JSONObject(new JSONTokener(is));
          Schema s = SchemaLoader.load(rawSchema);
          is.close();
          this.put(version, s);
        } catch (IOException e) {
        }
      }
    }
  }
  
  public  String  findLatestVersion() {
    Set<String> ks = this.keySet();
    String[] keys= ks.toArray(new String[0]);
    Arrays.sort(keys, new VersionSorter());
    return keys[0];
  }

  private static File[] getResourceFolderFiles(String folder) {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    URL url = loader.getResource(folder);
    String path = url.getPath();
    return new File(path).listFiles();
  }

  public static void main(String[] args) {
    SchemaHashMap shm = new SchemaHashMap("srrschema_([\\d.]+)\\.json");
    String latest = shm.findLatestVersion();
    System.out.println("latest version:" + latest);
    Schema schema = shm.get(latest);
    System.out.println(schema.toString());
    
    
  }
}
