package com.basingwerk.jisvalidator.jsonsurferhelpers;

import java.util.Arrays;

public class StorageEndpoint {

  private String name;
  private String id;
  private String endpointurl;
  private String interfacetype;
  private String interfaceversion;
  private String capabilities[];
  private String qualitylevel;
  private String assignedshares[];
  private String message;

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

  public String getEndpointurl() {
    return endpointurl;
  }

  public void setEndpointurl(String endpointurl) {
    this.endpointurl = endpointurl;
  }

  public String getInterfacetype() {
    return interfacetype;
  }

  public void setInterfacetype(String interfacetype) {
    this.interfacetype = interfacetype;
  }

  public String getInterfaceversion() {
    return interfaceversion;
  }

  public void setInterfaceversion(String interfaceversion) {
    this.interfaceversion = interfaceversion;
  }

  public String[] getCapabilities() {
    return capabilities;
  }

  public void setCapabilities(String[] capabilities) {
    this.capabilities = capabilities;
  }

  public String getQualitylevel() {
    return qualitylevel;
  }

  public void setQualitylevel(String qualitylevel) {
    this.qualitylevel = qualitylevel;
  }

  public String[] getAssignedshares() {
    return assignedshares;
  }

  public void setAssignedshares(String[] assignedshares) {
    this.assignedshares = assignedshares;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "StorageEndpoint [name=" + name + ", id=" + id + ", endpointurl=" + endpointurl + ", interfacetype=" + interfacetype
        + ", interfaceversion=" + interfaceversion + ", capabilities=" + Arrays.toString(capabilities) + ", qualitylevel="
        + qualitylevel + ", assignedshares=" + Arrays.toString(assignedshares) + ", message=" + message + "]";
  }
}
