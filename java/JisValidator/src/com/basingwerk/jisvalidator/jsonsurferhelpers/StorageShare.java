package com.basingwerk.jisvalidator.jsonsurferhelpers;

import java.util.Arrays;
import java.util.HashMap;

public class StorageShare {

  String name;
  String id;
  String policyrules[];
  String servingstate;
  String accessmode[];
  String accesslatency;
  String retentionpolicy;
  HashMap<String, String> lifetime;
  int timestamp;
  float totalsize;
  float usedsize;
  float numberoffiles;
  String path[];
  String assignedendpoints[];
  String vos[];
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

  public String[] getPolicyrules() {
    return policyrules;
  }

  public void setPolicyrules(String[] policyrules) {
    this.policyrules = policyrules;
  }

  public String getServingstate() {
    return servingstate;
  }

  public void setServingstate(String servingstate) {
    this.servingstate = servingstate;
  }

  public String[] getAccessmode() {
    return accessmode;
  }

  public void setAccessmode(String[] accessmode) {
    this.accessmode = accessmode;
  }

  public String getAccesslatency() {
    return accesslatency;
  }

  public void setAccesslatency(String accesslatency) {
    this.accesslatency = accesslatency;
  }

  public String getRetentionpolicy() {
    return retentionpolicy;
  }

  public void setRetentionpolicy(String retentionpolicy) {
    this.retentionpolicy = retentionpolicy;
  }

  public HashMap<String, String> getLifetime() {
    return lifetime;
  }

  public void setLifetime(HashMap<String, String> lifetime) {
    this.lifetime = lifetime;
  }

  public int getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(int timestamp) {
    this.timestamp = timestamp;
  }

  public float getTotalsize() {
    return totalsize;
  }

  public void setTotalsize(float totalsize) {
    this.totalsize = totalsize;
  }

  public float getUsedsize() {
    return usedsize;
  }

  public void setUsedsize(float usedsize) {
    this.usedsize = usedsize;
  }

  public float getNumberoffiles() {
    return numberoffiles;
  }

  public void setNumberoffiles(float numberoffiles) {
    this.numberoffiles = numberoffiles;
  }

  public String[] getPath() {
    return path;
  }

  public void setPath(String[] path) {
    this.path = path;
  }

  public String[] getAssignedendpoints() {
    return assignedendpoints;
  }

  public void setAssignedendpoints(String[] assignedendpoints) {
    this.assignedendpoints = assignedendpoints;
  }

  public String[] getVos() {
    return vos;
  }

  public void setVos(String[] vos) {
    this.vos = vos;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "StorageShare [name=" + name + ", id=" + id + ", policyrules=" + Arrays.toString(policyrules) + ", servingstate="
        + servingstate + ", accessmode=" + Arrays.toString(accessmode) + ", accesslatency=" + accesslatency + ", retentionpolicy="
        + retentionpolicy + ", lifetime=" + lifetime + ", timestamp=" + timestamp + ", totalsize=" + totalsize + ", usedsize="
        + usedsize + ", numberoffiles=" + numberoffiles + ", path=" + Arrays.toString(path) + ", assignedendpoints="
        + Arrays.toString(assignedendpoints) + ", vos=" + Arrays.toString(vos) + ", message=" + message + "]";
  }
}
