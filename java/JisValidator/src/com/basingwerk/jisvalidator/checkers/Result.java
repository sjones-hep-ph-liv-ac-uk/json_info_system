package com.basingwerk.jisvalidator.checkers;

public class Result {

  public static int OK = 0;
  public static int SCHEMAFAULT = 1;
  public static int INTEGRITYFAULT = 2;

  private final int code;

  private final String description;

  Result(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getCode() {
    return code;
  }

  @Override
  public String toString() {
    return code + ": " + description;
  }
}
