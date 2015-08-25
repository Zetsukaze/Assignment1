package com.ncs.iframe.course.department.to;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Audited
@Entity
@Table(name="tbl_departments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DepartmentTO implements Serializable {

  private static final long serialVersionUID = -4559728782253321910L;
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Column(name = "id")
  private String id;


  @Column(name="dept_name")
  private String name;
  @Column(name="dept_desc")
  private String desc;
  @Version
  private Integer version;

  // Getters

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDesc() {
    return desc;
  }

  public Integer getVersion() {
    return version;
  }

  @Override
  public String toString() {
    return "DepartmentTO : {" + "ID: " + id + ", Name: " + name + ", Description: " + desc + "}";
  }

  // Setters

  public void setId(String id) {
    this.id = id;
  }
  public void setName(String name) {
    this.name = name;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}
