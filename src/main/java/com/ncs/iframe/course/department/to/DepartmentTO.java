package com.ncs.iframe.course.department.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import com.ncs.iframe.course.staff.to.StaffTO;

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
  @OneToMany(fetch=FetchType.EAGER, mappedBy="departmentTO")
  @Cascade(value={org.hibernate.annotations.CascadeType.SAVE_UPDATE,CascadeType.DELETE})
  private List<StaffTO> staffList = new ArrayList<StaffTO>();

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

  public List<StaffTO> getStaffList() {
    return staffList;
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

  public void setStaffList(List<StaffTO> staffList) {
    this.staffList = staffList;
  }
}
