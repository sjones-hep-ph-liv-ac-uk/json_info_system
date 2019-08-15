package com.basingwerk.jisvalidator.checkers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.basingwerk.jisvalidator.utils.Utils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class ComputeSemanticChecker {

  private JsonParser jParser;

  public ComputeSemanticChecker(String json) throws CheckerException {
    JsonFactory jfactory = new JsonFactory();

    try {
      jParser = jfactory.createParser(json);
    } catch (IOException e) {
      throw new CheckerException("Could not create a json parser");
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

    ArrayList<String> resourceVOs = new ArrayList<String>();
    ArrayList<String> shareVOs = new ArrayList<String>();
    ArrayList<String> accessPointShares = new ArrayList<String>();
    ArrayList<String> resourceShares = new ArrayList<String>();
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

            resourceVOs.clear();
            resourceShares.clear();
            resourceLevelAps.clear();

            shareVOs.clear();
            accessPointShares.clear();

            inResourceVOs = false;
            inShareVOs = false;
            inApsShares = false;
            inShares = false;
            inAps = false;
          }
        }

        if (currentLevel == 2) {
          if (cj == JsonToken.END_OBJECT) {
            // When existing a resource, ensure no duplication
            String duplicateResourceVOs = Utils.getDuplicates(resourceVOs);
            if (duplicateResourceVOs.length() != 0)
              return ("Duplicate resource VOs in " + currentResource);

            String duplicateShareVOs = Utils.getDuplicates(shareVOs);
            if (duplicateShareVOs.length() != 0)
              return ("Duplicate share VOs in " + currentResource);

            String duplicateResourceShares = Utils.getDuplicates(resourceShares);
            if (duplicateResourceShares.length() != 0)
              return ("Duplicate resource shares in " + currentResource);

            String duplicateAccessPointShares = Utils.getDuplicates(accessPointShares);
            if (duplicateAccessPointShares.length() != 0)
              return ("Duplicate accesspoiunt shares in " + currentResource);

            // When exiting a resource make sure no VOs in the shares are extrinsic to the
            // VOs in the resource section

            String illegalShareVOs = Utils.getExtrinsics(resourceVOs, shareVOs);
            if (illegalShareVOs.length() != 0) {
              return ("inconsistent assigned VOs in resource " + currentResource + ", check resource and share VOs for "
                  + illegalShareVOs);
            }

            // When exiting a resource make sure no Shares in the accesspoints are extrinsic
            // to the shares in the resource section
            String illegalAccessPointShares = Utils.getExtrinsics(resourceShares, accessPointShares);
            if (illegalAccessPointShares.length() != 0) {
              return ("inconsistent shares for resource " + currentResource + ", check shares for " + illegalAccessPointShares);
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
              if (resourceShares.contains(share)) {
                return ("duplicate share " + share + " in resource " + text);
              }
              resourceShares.add(share);
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
            resourceVOs.add(text);
          } else if (inShareVOs) {
            shareVOs.add(text);
          } else if (inApsShares) {
            accessPointShares.add(text);
          }
        }
        // Finish any arrays in one go so I don't need to worry
        if (cj == JsonToken.END_ARRAY) {
          inResourceVOs = false;
          inShareVOs = false;
          inApsShares = false;
        }

        // ---------------------------------------
        // Wind it through the levels
        if (text.equals("{"))
          currentLevel++;
        if (text.equals("}"))
          currentLevel--;

      }
      jParser.close();
    } catch (IOException e) {
      return "Parse error: " + e.getMessage()
          + "\nPerhaps json file is not well formed. Please validate the json _before_ using this program.";
    }
    return "";
  }
}
