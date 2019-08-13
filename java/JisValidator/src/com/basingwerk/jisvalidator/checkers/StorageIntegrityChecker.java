package com.basingwerk.jisvalidator.checkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.basingwerk.jisvalidator.utils.Utils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class StorageIntegrityChecker {

  private JsonParser jParser;

  public StorageIntegrityChecker(String json) {
    JsonFactory jfactory = new JsonFactory();

    try {
      jParser = jfactory.createParser(json);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String check() {
    int currentLevel = -1;
    HashMap<String, Boolean> allResources = new HashMap<String, Boolean>();
    Boolean inResourceVOs = false;
    Boolean inShareVOs = false;
    Boolean inApsShares = false;
    Boolean inShares = false;
    Boolean inAps = false;

    ArrayList<String> resourceLevelVOs = new ArrayList<String>();
    ArrayList<String> sharesLevelVOs = new ArrayList<String>();
    ArrayList<String> apsLevelShares = new ArrayList<String>();
    ArrayList<String> resourceLevelShares = new ArrayList<String>();
    ArrayList<String> resourceLevelAps = new ArrayList<String>();
    ArrayList<String> alist = new ArrayList<String>();

    String currentResource = "";

    try {
      while (jParser.nextToken() != null) {
        JsonToken cj = jParser.currentToken();
        String text = jParser.getText();

        if (currentLevel == 0) {
          if (cj == JsonToken.FIELD_NAME) {
            if (text.equals("computingresources") != true) {
              return ("First tag must be computingresources");
            }
          }
        }

        // The resource name comes on level 1. Only thing to check is uniqueness.
        if (currentLevel == 1) {
          if (cj == JsonToken.FIELD_NAME) {
            currentResource = text;
            if (allResources.containsKey(currentResource)) {
              return ("duplicate resource " + text);
            }
            allResources.put(text, true);

            resourceLevelVOs.clear();
            sharesLevelVOs.clear();
            apsLevelShares.clear();
            resourceLevelShares.clear();
            resourceLevelAps.clear();

            inResourceVOs = false;
            inShareVOs = false;
            inApsShares = false;
            inShares = false;
            inAps = false;
          }
        }

        if (currentLevel == 2) {
          if (cj == JsonToken.END_OBJECT) {
            // When exiting a resource make sure no VOs in the shares are extrinsic to the
            // VOs in the resource section (if any)
            if (resourceLevelVOs.isEmpty() != true) {
              String problemVOs = Utils.getExtrinsics(resourceLevelVOs, sharesLevelVOs);
              if (problemVOs.length() != 0) {
                return ("inconsistent assigned VOs in resource " + currentResource + ", check resource and share VOs for "
                    + problemVOs);
              }
            }

            if (resourceLevelShares.isEmpty() != true) {
              String problemShares = Utils.getExtrinsics(resourceLevelShares, apsLevelShares);
              if (problemShares.length() != 0) {
                return ("inconsistent shares for resource " + currentResource + ", check shares for " + problemShares);
              }
            }
          } else if (cj == JsonToken.FIELD_NAME) {
            if (text.equals("assigned_vos")) {
              inResourceVOs = true;
              inAps = false;
              inApsShares = false;
              inShareVOs = false;
              inShares = false;

            } else if (text.equals("shares")) {
              inShares = true;
              inAps = false;
              inApsShares = false;
              inResourceVOs = false;
              inShareVOs = false;
            } else if (text.equals("accesspoints")) {
              inAps = true;
              inApsShares = false;
              inResourceVOs = false;
              inShareVOs = false;
              inShares = false;
            }
          }
        }

        if (currentLevel == 3) {
          if (inShares) {
            if (cj == JsonToken.FIELD_NAME) {
              String share = text;
              if (resourceLevelShares.contains(share)) {
                return ("duplicate share " + share + " in resource " + text);
              }
              resourceLevelShares.add(share);
            }
          }
          if (inAps) {
            if (cj == JsonToken.FIELD_NAME) {
              String ap = text;
              if (resourceLevelAps.contains(ap)) {
                return ("duplicate accesspoint " + ap + " in resource " + text);
              }
              resourceLevelAps.add(ap);
            }
          }
        }
        if (currentLevel == 4) {
          if (cj == JsonToken.FIELD_NAME) {
            // Find out if we should be collecting, e.g. share level VOs or aps level
            // shares.
            if (inShares) {
              if (text.equals("assigned_vos")) {
                inShareVOs = true;
                inAps = false;
              }
            } else if (inAps) {
              if (text.equals("shares")) {
                inApsShares = true;
                inShares = false;
              }
            }
          }
        }
        if (cj == JsonToken.VALUE_STRING) {
          if (inResourceVOs) {
            // System.out.printf("cl: %d, token: %s %s\n", currentLevel, text,
            // cj.toString());
            resourceLevelVOs.add(text);
          } else if (inShareVOs) {
            sharesLevelVOs.add(text);
          } else if (inApsShares) {
            apsLevelShares.add(text);
          }
        }
        // Finish any arrays in one go so I don't need to worry
        if (cj == JsonToken.END_ARRAY) {
          inResourceVOs = false;
          inShareVOs = false;
          inApsShares = false;
        }

        // ---------------------------------------
        // ---------------------------------------
        // Wind it through the levels
        if (text.equals("{")) {
          currentLevel++;
        }
        if (text.equals("}")) {
          currentLevel--;
        }
      }
      jParser.close();
    } catch (IOException e) {
      return "Some kind of parse error: " + e.getMessage()
          + "\nPerhaps json file is not well formed. Please validate the json _before_ using this program.";
    }
    return "";
  }

}
