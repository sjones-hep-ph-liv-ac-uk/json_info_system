package com.basingwerk.jisvalidator.schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
    loadSchemas();
  }

  public void loadSchemas() {

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

  public List getKeys() {
    Set<String> ks = this.keySet();
    List<String> list = convertSetToList(ks);
    Collections.sort(list, new VersionSorter());
    Collections.reverse(list);
    return list;
  }

  public String findLatestVersion() {
//    Set<String> keySet = this.keySet();
//    String[] keys= keySet.toArray(new String[0]);
//    Arrays.sort(keys, new VersionSorter());
//    return keys[0];
    Set<String> ks = this.keySet();
    List<String> list = convertSetToList(ks);
    Collections.sort(list, new VersionSorter());
    Collections.reverse(list);
    return list.get(0);

  }

  public static <T> List<T> convertSetToList(Set<T> set) {
    // create an empty list
    List<T> list = new ArrayList<>();

    // push each element in the set into the list
    for (T t : set)
      list.add(t);

    // return the list
    return list;
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

  public String sayHello() {
    return "Hello";
  }

  public int countSchemas() {
    List s = this.getKeys();
    return s.size();
  }

  public String getSchemaText(String schemaVersion) {
    Schema schema = this.get(schemaVersion);
    return schema.getDescription();
  }
}
