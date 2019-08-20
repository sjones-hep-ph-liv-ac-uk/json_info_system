package com.basingwerk.jisvalidator.checkers;

import java.util.Collection;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.jsfr.json.JsonSurfer;
import com.basingwerk.jisvalidator.jsonsurferhelpers.DataStore;
import com.basingwerk.jisvalidator.jsonsurferhelpers.StorageCapacity;
import com.basingwerk.jisvalidator.jsonsurferhelpers.StorageEndpoint;
import com.basingwerk.jisvalidator.jsonsurferhelpers.StorageShare;
import com.basingwerk.jisvalidator.utils.Utils;

public class StorageIntegrityChecker {

  private Logger logger;
  private JsonSurfer surfer;
  String theJson;

  public StorageIntegrityChecker(String json) throws CheckerException {
    logger = Logger.getLogger(StorageIntegrityChecker.class);
    theJson = json;
    surfer = JsonSurfer.gson();
  }

  public String check() {

    try {
      Object o = surfer.collectOne(theJson, Object.class, "$.storageservice");
    } catch (NullPointerException n) {
      return "First element is not storageservice";
    }

    try {
      Object o = surfer.collectOne(theJson, Object.class, "$.storageservice.storagecapacity");
      if (o != null) {
        StorageCapacity online = surfer.collectOne(theJson, StorageCapacity.class, "$.storageservice.storagecapacity.online");
        if (online.validValues() != true) {
          return "online storagecapacity is invalid";
        }

        StorageCapacity offline = surfer.collectOne(theJson, StorageCapacity.class, "$.storageservice.storagecapacity.offline");
        if (offline.validValues() != true) {
          return "offline storagecapacity is invalid";
        }

        StorageCapacity nearline = surfer.collectOne(theJson, StorageCapacity.class, "$.storageservice.storagecapacity.nearline");
        if (nearline.validValues() != true) {
          return "nearline storagecapacity is invalid";
        }
      }

    } catch (NullPointerException n) {
      return "problem in storagecapacity section";
    }

    Collection<StorageEndpoint> seps = surfer.collectAll(theJson, StorageEndpoint.class, "$.storageservice.storageendpoints[*]");
    Collection<StorageShare> sss = surfer.collectAll(theJson, StorageShare.class, "$.storageservice.storageshares[*]");
    Collection<DataStore> dss = surfer.collectAll(theJson, DataStore.class, "$.storageservice.datastores[*]");

    // Check storageendpoints
    HashMap<String, Boolean> sepNamesUnique = new HashMap<String, Boolean>();
    HashMap<String, Boolean> sepIdsUnique = new HashMap<String, Boolean>();

    for (StorageEndpoint sep : seps) {
      if (sepNamesUnique.containsKey(sep.getName()))
        return "storageendpoint name " + sep.getName() + " is not unique";
      sepNamesUnique.put(sep.getName(), true);

      if (sepIdsUnique.containsKey(sep.getId()))
        return "storageendpoint id " + sep.getId() + " is not unique";
      sepIdsUnique.put(sep.getId(), true);
    }

    // Check storageshares
    HashMap<String, Boolean> ssNamesUnique = new HashMap<String, Boolean>();
    HashMap<String, Boolean> ssIdsUnique = new HashMap<String, Boolean>();

    for (StorageShare ss : sss) {
      if (ssNamesUnique.containsKey(ss.getName()))
        return "storageshare name " + ss.getName() + " is not unique";
      ssNamesUnique.put(ss.getName(), true);

      if (ssIdsUnique.containsKey(ss.getId()))
        return "storageshare id " + ss.getId() + " is not unique";
      ssIdsUnique.put(ss.getId(), true);

      // Check VOs
      String voDups = Utils.getDuplicates(ss.getVos());
      if (voDups.length() > 0)
        return "In storageshare " + ss.getName() + ", vos are not unique; " + voDups;

      // Check path(s)
      String pathDups = Utils.getDuplicates(ss.getPath());
      if (pathDups.length() > 0)
        return "In storageshare " + ss.getName() + ", path is not unique; " + pathDups;

      // Check accessmodes
      String[] am = ss.getAccessmode();
      if (am != null) {
        String amDups = Utils.getDuplicates(am);
        if (amDups.length() > 0)
          return "In storageshare " + ss.getName() + ", accessmode  is not unique: " + amDups;
      }
    }

    // Check datastores
    HashMap<String, Boolean> dsNamesUnique = new HashMap<String, Boolean>();
    HashMap<String, Boolean> dsIdsUnique = new HashMap<String, Boolean>();

    for (DataStore ds : dss) {
      if (dsNamesUnique.containsKey(ds.getName()))
        return "datastore name " + ds.getName() + " is not unique";
      dsNamesUnique.put(ds.getName(), true);

      if (dsIdsUnique.containsKey(ds.getId()))
        return "datastore id " + ds.getId() + " is not unique";
      dsIdsUnique.put(ds.getId(), true);

    }

    // For each storageendpoint, ensure one assignedshare "all",
    // or that each assignedshare is amongst storageshares
    for (StorageEndpoint se : seps) {
      String assignedShares[] = se.getAssignedshares();

      HashMap<String, Boolean> asUnique = new HashMap<String, Boolean>();
      for (String as : assignedShares) {
        if (asUnique.containsKey(as)) {
          return "In storageendpoint " + se.getName() + ", assignedshare " + as + " is not unique";
        }
        asUnique.put(as, true);

        if (as.equals("all")) {
          if (assignedShares.length != 1) {
            return "In storageendpoint " + se.getName() + ", assignedshare has all as well as discrete values";
          }
        } else {

          if (ssNamesUnique.containsKey(as) != true) {
            return "In storageendpoint " + se.getName() + " assignedshare " + as + " does not exist as a storageshare";
          }
        }
      }
    }

    // For each storageshare, ensure one assignedendpoint "all",
    // or that each assignedendpoint is amongst storageendpoints
    for (StorageShare ss : sss) {
      String assignedEndpoints[] = ss.getAssignedendpoints();

      HashMap<String, Boolean> aepUnique = new HashMap<String, Boolean>();
      for (String aep : assignedEndpoints) {
        if (aepUnique.containsKey(aep)) {
          return "In storageshare " + ss.getName() + ", assignedendpoint " + aep + " is not unique";
        }
        aepUnique.put(aep, true);

        if (aep.equals("all")) {
          if (assignedEndpoints.length != 1) {
            return "In storageshare " + ss.getName() + ", assignedendpoint has all as well as discrete values";
          }
        } else {

          if (sepNamesUnique.containsKey(aep) != true) {
            return "In storageshare " + ss.getName() + " assignedendpoint " + aep + " does not exist as a storageendpoints ";
          }
        }
      }
    }
    return "";
  }
}
