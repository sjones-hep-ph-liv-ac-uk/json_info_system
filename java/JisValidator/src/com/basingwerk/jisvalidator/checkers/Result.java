package com.basingwerk.jisvalidator.checkers;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "result")
public class Result implements Serializable {

  private static final long serialVersionUID = 1L;

  public static int OK = 0;
  public static int PROGRAMFAULT = 1;
  public static int SCHEMAFAULT = 2;
  public static int INTEGRITYFAULT = 3;

  @XmlAttribute(name = "code")
  private final int code;

  @XmlElement(name = "description")
  private final String description;

  public Result(int code, String description) {
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
