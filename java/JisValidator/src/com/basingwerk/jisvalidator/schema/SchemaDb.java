package com.basingwerk.jisvalidator.schema;

import java.io.BufferedReader;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import com.basingwerk.jisvalidator.utils.VersionSorter;

public class SchemaDb extends HashMap<String, SchemaHolder> {

  private static final long serialVersionUID = 1L;
  private Pattern filePattern;
  private Logger logger;

  public SchemaDb(String regex) {
    super();
    logger = Logger.getLogger(SchemaDb.class);
    filePattern = Pattern.compile(regex);
    loadSchemas();
  }

  public void loadSchemas() {

    for (File f : getResourceFolderFiles("./")) {

      String fname = f.getName();

      Matcher m = filePattern.matcher(fname);

      if (m.find()) {
        String version = m.group(1);
        try {
          String content = readFileIn(f);

          JSONObject rawSchema = new JSONObject(content);
          
          Schema s = SchemaLoader.load(rawSchema);
          SchemaHolder sc = new SchemaHolder(s, content);
          this.put(version, sc);
        } catch (IOException e) {
          logger.debug("Error in loadSchemas " + e.getMessage());
        }
      }
    }
  }

  public String readFileIn(File file) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    StringBuilder stringBuilder = new StringBuilder();
    String line = null;
    String ls = System.getProperty("line.separator");
    while ((line = reader.readLine()) != null) {
      stringBuilder.append(line);
      stringBuilder.append(ls);
    }
    // delete the last new line separator
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    reader.close();

    String content = stringBuilder.toString();
    return content;
  }

  public List<String> getKeys() {
    logger.debug("getting list from getKeys in SchemaDbHashMap");
    Set<String> ks = this.keySet();
    List<String> list = convertSetToList(ks);
    Collections.sort(list, new VersionSorter());
    Collections.reverse(list);
    return list;
  }

  public String findLatestVersion() {
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
}
