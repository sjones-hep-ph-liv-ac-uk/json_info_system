package com.basingwerk.jisvalidator.jsonsurferhelpers;

public class StorageCapacity {
  private int totalsize;
  private int usedsize;
  private int reservedsize;

  public boolean validValues() {

    if (reservedsize + usedsize >= totalsize)
      return false;

    if (reservedsize < -1 | usedsize < -1 | totalsize < -1)
      return false;

    return true;
  }
}
