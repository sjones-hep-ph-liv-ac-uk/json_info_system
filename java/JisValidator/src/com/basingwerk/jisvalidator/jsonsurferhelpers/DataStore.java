package com.basingwerk.jisvalidator.jsonsurferhelpers;

import java.util.HashMap;

public class DataStore {
  
  String name;
  String id;
  String datastoretype;
  String accesslatency;
  float totalsize;
  String vendor;
  HashMap<String, String> bandwith;
  String message;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getDatastoretype() {
    return datastoretype;
  }

  public void setDatastoretype(String datastoretype) {
    this.datastoretype = datastoretype;
  }

  public String getAccesslatency() {
    return accesslatency;
  }

  public void setAccesslatency(String accesslatency) {
    this.accesslatency = accesslatency;
  }

  public float getTotalsize() {
    return totalsize;
  }

  public void setTotalsize(float totalsize) {
    this.totalsize = totalsize;
  }

  public String getVendor() {
    return vendor;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public HashMap<String, String> getBandwith() {
    return bandwith;
  }

  public void setBandwith(HashMap<String, String> bandwith) {
    this.bandwith = bandwith;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public String toString() {
    return "DataStore [name=" + name + ", id=" + id + ", datastoretype=" + datastoretype + ", accesslatency=" + accesslatency
        + ", totalsize=" + totalsize + ", vendor=" + vendor + ", bandwith=" + bandwith + ", message=" + message + "]";
  }

}
