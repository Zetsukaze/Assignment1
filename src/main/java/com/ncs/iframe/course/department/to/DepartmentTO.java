package com.ncs.iframe.course.department.to;

import java.io.Serializable;

public class DepartmentTO implements Serializable {

  private static final long serialVersionUID = -4559728782253321910L;
  private String id;
  private String name;
  private String desc;


  // Getters
  public String getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  // Setters

  public void setID(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
