package com.basingwerk.jisvalidator.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class ListUtils {
  
  public static String getExtrinsics(ArrayList<String> bigList, ArrayList<String> smallList) {
    // All in small list must exist in big list, else return list of missing
    
    String extrinsics = "";
    for (String small : smallList) {
      Boolean itExists = false;
      for (String big : bigList) {
        if (small.equals(big)) {
          itExists = true;
        }
      }
      if (itExists != true) {
        extrinsics = extrinsics + small + " ";
      }
    }
    return extrinsics;
  }
  
  public static String getDuplicates(ArrayList<String> list) {
    String[] a = new String[list.size()];
    return getDuplicates(list.toArray(a));
  }

  public static String getDuplicates(String[] list) {
    // Return any duplicates in a list
    String duplicates = "";
    HashMap<String, Boolean> h = new HashMap<String, Boolean>();
    for (String item : list) {
      if (h.containsKey(item)) {
        duplicates = duplicates + item + " ";
      } else {
        h.put(item, true);
      }
    }
    return duplicates;
  }
}
