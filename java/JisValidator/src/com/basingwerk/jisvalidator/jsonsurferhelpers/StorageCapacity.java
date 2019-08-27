package com.basingwerk.jisvalidator.jsonsurferhelpers;

public class StorageCapacity {
  private long totalsize;
  private long usedsize;
  private long reservedsize;
  
  public long getTotalsize() {
    return totalsize;
  }

  public long getUsedsize() {
    return usedsize;
  }

  public long getReservedsize() {
    return reservedsize;
  }

  public boolean validValues() {

    if (reservedsize + usedsize > totalsize)
      return false;

    if (reservedsize < -1 | usedsize < -1 | totalsize < -1)
      return false;

    return true;
  }
}
