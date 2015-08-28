package com.ncs.iframe.course.staff.to;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import com.ncs.iframe.course.department.to.DepartmentTO;

@Audited
@Entity
@Table(name="tbl_staff")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StaffTO implements Serializable {

  private static final long serialVersionUID = 702347721425471803L;
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Column(name = "id")
  private String id;
  @Column(name = "staff_no")
  private String staffNum;
  @Column(name = "staff_name")
  private String name;
  @Column(name = "login_id")
  private String loginId;
  @Column(name = "email")
  private String email;
  @Column(name = "phone_no")
  private String phoneNum;
  @Column(name = "dob")
  private Date dob;
  @Column(name = "join_date")
  private Date joinDate;
  @Transient
  private String deptId;
  @ManyToOne
  @JoinColumn(name="dept_id")
  private DepartmentTO departmentTO;
  @Column(name = "designation")
  private String designation;
  @Column(name = "ro_id")
  private String roId;
  @Column(name = "photo")
  private String photo;
  @Column(name = "version")
  private Integer version;

  // Getters

  public String getId() {
    return this.id;
  }

  public String getStaffNum() {
    return this.staffNum;
  }

  public String getName() {
    return this.name;
  }

  public String getLoginId() {
    return this.loginId;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPhoneNum() {
    return this.phoneNum;
  }

  public Date getDob() {
    return this.dob;
  }

  public Date getJoinDate() {
    return this.joinDate;
  }

  public String getDeptId() {
    return this.deptId;
  }

  public DepartmentTO getDepartmentTO() {
    return departmentTO;
  }

  public String getDesignation() {
    return this.designation;
  }

  public String getRoId() {
    return this.roId;
  }

  public String getPhoto() {
    return this.photo;
  }

  public Integer getVersion() {
    return this.version;
  }

  @Override
  public String toString() {
    return "StaffTO : {" + "ID: " + this.id + ", Staff No: " + this.staffNum + ", Name: " + this.name + ", Login ID: " + this.loginId + ", Email: " + this.email + ", Phone No: " + this.phoneNum + ", DOB: " + String.format("%1$tY/%1$tm/%1$td", dob) + ", Join Date: " + String.format("%1$tY/%1$tm/%1$td", joinDate) + ", Department ID: " + this.deptId + ", Designation: " + this.designation + ", RO ID: " + this.roId + ", Photo: " + this.photo + ", Version: " + this.version + "}";
  }

  // Setters

  public void setId(String id) {
    this.id = id;
  }

  public void setStaffNum(String staffNum) {
    this.staffNum = staffNum;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public void setJoinDate(Date joinDate) {
    this.joinDate = joinDate;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public void setDepartmentTO(DepartmentTO departmentTO) {
    this.departmentTO = departmentTO;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public void setRoId(String roId) {
    this.roId = roId;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}
