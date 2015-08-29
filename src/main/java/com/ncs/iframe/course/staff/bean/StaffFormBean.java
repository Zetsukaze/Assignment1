package com.ncs.iframe.course.staff.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.ncs.iframe.course.department.service.DepartmentService;
import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe.course.staff.service.StaffService;
import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.logging.Logger;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;
import com.ncs.iframe4.commons.to.SubjectBaseTO;
import com.ncs.iframe4.commons.to.SubjectLoginBaseTO;
import com.ncs.iframe4.commons.tools.StringUtil;
import com.ncs.iframe4.jsf.message.MessageUtils;
import com.ncs.iframe4.jsf.pagination.PaginationDataModel;
import com.ncs.iframe4.jsf.util.JSFTools;
import com.ncs.itrust4.core.ITrustConstants;
import com.ncs.itrust4.core.service.AACRUDService;
import com.ncs.itrust4.crypto.PasswordService;

public class StaffFormBean {

  private static final String MESSAGE_PROPS = "com.ncs.iframe4.jsf.i18n.SampleMessages";
  private transient Logger log = Logger.getLogger(getClass());
  private String name;
  private StaffService staffSvc;
  private LazyDataModel<StaffTO> staffList;
  private StaffTO[] selectedStaff;
  private StaffTO staff = new StaffTO();
  private DepartmentService deptSvc;
  private AACRUDService aaCRUDService;

  // Constructors

  public StaffFormBean() {
    staffList = new PaginationDataModel<StaffTO>() {

      public ListAndPagingInfo loadPaginationData(Map filters) {
        return new ListAndPagingInfo();
      }

      public StaffTO getRowData(String rowKey) {
        return null;
      }

      public Object getRowKey(StaffTO object) {
        return null;
      }
    };
  }

  // Getters

  public String getName() {
    return this.name;
  }

  public StaffService getStaffService() {
    return this.staffSvc;
  }

  public LazyDataModel<StaffTO> getStaffList() {
    return staffList;
  }

  public StaffTO[] getSelectedStaff() {
    return selectedStaff;
  }

  public StaffTO getStaff() {
    return staff;
  }

  public DepartmentService getDepartmentService() {
    return deptSvc;
  }

  public List<DepartmentTO> getAllDepartments() {
    ListAndPagingInfo<DepartmentTO> listAndPageInfo = deptSvc.findByName("");
    if (listAndPageInfo != null) {
      return listAndPageInfo.getResult();
    }
    return null;
  }

  public List<StaffTO> getAllStaff() {
    ListAndPagingInfo<StaffTO> listAndPageInfo = staffSvc.findByName("");
    if (listAndPageInfo != null) {
      return listAndPageInfo.getResult();
    }
    return null;
  }

  public AACRUDService getAaCRUDService() {
    return aaCRUDService;
  }

  // Setters

  public void setName(String name) {
    this.name = name;
  }

  public void setStaffService(StaffService staffSvc) {
    this.staffSvc = staffSvc;
  }

  public void setStaffList(LazyDataModel<StaffTO> staffList) {
    this.staffList = staffList;
  }

  public void setSelectedStaff(StaffTO[] selectedStaff) {
    this.selectedStaff = selectedStaff;
  }

  public void setStaff(StaffTO staff) {
    this.staff = staff;
  }

  public void setDepartmentService(DepartmentService deptSvc) {
    this.deptSvc = deptSvc;
  }

  public void setAaCRUDService(AACRUDService aaCRUDService) {
    this.aaCRUDService = aaCRUDService;
  }

  // Validators

  public String getDate16yAgo() {
    Calendar dateTimeNow = Calendar.getInstance();
    dateTimeNow.add(Calendar.YEAR, -16);
    dateTimeNow.set(Calendar.HOUR_OF_DAY, 0);
    dateTimeNow.set(Calendar.MINUTE, 0);
    dateTimeNow.set(Calendar.SECOND, 0);
    dateTimeNow.set(Calendar.MILLISECOND, 0);
    Date returnDate = dateTimeNow.getTime();
    String returnString = String.format("%1$td/%1$tm/%1$tY", returnDate);
    log.info("Date 16 years ago: " + returnString);
    return "" + returnString;
  }

  public String getDate100yAgo() {
    Calendar dateTimeNow = Calendar.getInstance();
    dateTimeNow.add(Calendar.YEAR, -100);
    dateTimeNow.set(Calendar.HOUR_OF_DAY, 0);
    dateTimeNow.set(Calendar.MINUTE, 0);
    dateTimeNow.set(Calendar.SECOND, 0);
    dateTimeNow.set(Calendar.MILLISECOND, 0);
    Date returnDate = dateTimeNow.getTime();
    String returnString = String.format("%1$td/%1$tm/%1$tY", returnDate);
    log.info("Date 100 years ago: " + returnString);
    return "" + returnString;
  }

  public String getDayAfterDob() {
    Calendar dateTimeNow = Calendar.getInstance();
    Date dob = staff.getDob();
    if (dob != null) {
      dateTimeNow.setTimeInMillis(dob.getTime());
      dateTimeNow.add(Calendar.YEAR, 16);
      Date returnDate = dateTimeNow.getTime();
      String returnString = String.format("%1$td/%1$tm/%1$tY", returnDate);
      log.info("Mindate for joining: " + returnString);
      return "" + returnString;
    }
    return "";
  }

  public void handleFileUpload(FileUploadEvent event) {
    UploadedFile file = event.getFile();
    if(file != null) {
      try {
        String fileName = file.getFileName();
        long fileSize = file.getSize();
        int size = (int) fileSize;
        byte[] buffer = new byte[size];
        InputStream stream = file.getInputstream();
        stream.read(buffer);
        stream.close();
        this.staff.setPhoto(buffer);
        log.info("StaffFormBean updated photo: " + buffer);
        FacesContext.getCurrentInstance().addMessage(null, MessageUtils.getMessage(MESSAGE_PROPS, FacesMessage.SEVERITY_INFO, "msg.staff.photo.uploaded", fileName));
      } catch (IOException e) {
        log.info("StaffFormBean handleFileUpload failure: " + e.toString());
        JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.photo.uploadfailed", FacesMessage.SEVERITY_WARN);
      }
    }
  }

  public String checkViewStaffProcess(String staffId) {
    String redirectList = "/xhtml/staff/list.xhtml";
    String redirectView = "/xhtml/staff/view.xhtml";
    StaffTO checkStaff = staffSvc.findById(staffId);
    if (checkStaff == null) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.missing", FacesMessage.SEVERITY_WARN);
      return redirectList;
    }
    return redirectView;
  }

  public String checkUpdateStaffProcess(String staffId) {
    String redirectList = "/xhtml/staff/list.xhtml";
    String redirectUpdate = "/xhtml/staff/update.xhtml";
    StaffTO checkStaff = staffSvc.findById(staffId);
    if (checkStaff == null) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.missing", FacesMessage.SEVERITY_WARN);
      return redirectList;
    }
    return redirectUpdate;
  }

  public String checkForUpdatingStaffProcess(StaffTO staff) {
    log.info("StaffFormBean checkForUpdatingStaffProcess START: " + staff);
    String redirectUpdate = "/xhtml/staff/update.xhtml";
    String redirectView = "/xhtml/staff/view.xhtml";
    try {
      StaffTO duplicatedStaff = staffSvc.checkExactStaffExists(staff);
      log.info("StaffFormBean checkForUpdatingStaffProcess checkExactStaffExists: " + staff);
      String deptId = staff.getDeptId();
      log.info("StaffFormBean checkForUpdatingStaffProcess 1");
      String roId = staff.getRoId();
      log.info("StaffFormBean checkForUpdatingStaffProcess 2");

      if (deptId != null && !deptId.equals("null")) {
        log.info("StaffFormBean checkForUpdatingStaffProcess department exists");
        DepartmentTO departmentExists = deptSvc.findById(staff.getDeptId());
        if (departmentExists == null) {
          throw new InterruptedException("3");
        }
        staff.setDepartment(departmentExists);
      }

      if (roId != null && !roId.equals("null")) {
        log.info("StaffFormBean checkForUpdatingStaffProcess for cyclic RO");
        StaffTO reportingOfficerExists = staffSvc.findById(roId);
        if (reportingOfficerExists == null) {
          throw new InterruptedException("MISSING RO OFFICER");
        }
        staff.setReportingOfficer(reportingOfficerExists);
        staffSvc.checkCyclicReportingOfficer(staff, staff);
      }
      log.info("StaffFormBean checkForUpdatingStaffProcess DONE");
      return redirectView;
    } catch (InterruptedException e) {
      int errorType = Integer.parseInt(e.getMessage());
      switch (errorType) {
        case 1:
          log.info("StaffFormBean found duplicate staff number!");
          break;
        case 2:
          log.info("StaffFormBean found duplicate staff login!");
          break;
        case 3:
          log.info("StaffFormBean found missing department!");
          break;
        case 4:
          log.info("StaffFormBean found cyclic relationship!");
          break;
        case 5:
          log.info("StaffFormBean found missing staff!");
          break;
        default:
          log.info("StaffFormBean checkForUpdatingStaffProcess InterruptedException: " + e.toString());
          break;
      }
      return redirectUpdate;
    } catch (Exception e) {
      log.info("StaffFormBean checkForUpdatingStaffProcess Exception: " + e.toString());
      return redirectUpdate;
    }
  }

  // Create

  public void addStaffProcess() {
    try {
      String loginId = this.staff.getLoginId();
      String deptId = this.staff.getDeptId();
      String roId = this.staff.getRoId();
      this.staff.setEmail(loginId + "@corp.com.sg");
      if (deptId != null && !deptId.equals("null")) {
        log.info("StaffFormBean addStaffProcess check for department: " + deptId);
        this.staff.setDepartment(deptSvc.findById(deptId));
      }
      if (roId != null && !roId.equals("null")) {
        log.info("StaffFormBean addStaffProcess check for RO: " + roId);
        this.staff.setReportingOfficer(staffSvc.findById(roId));
      }
      StaffTO addition = staffSvc.add(this.staff);
      if (addition != null) {
        // iTrust stuff
        SubjectBaseTO subject = aaCRUDService.getSubjectTOInstance();
        subject.setFirstName(this.staff.getName());
        subject.setEmail(this.staff.getEmail());
        subject.setStatus(ITrustConstants.ACTIVE_STATUS_VALUE);
        aaCRUDService.createSubject(subject);

        String subjectId = subject.getSubjectId();

        SubjectLoginBaseTO subjectLogin = this.aaCRUDService.getSubjectLoginTOInstance();
        subjectLogin.setSubjectId(subjectId);
        subjectLogin.setLoginMechanism(ITrustConstants.PASSWORD_AUTH_METHOD);
        subjectLogin.setLoginName(this.staff.getLoginId());
        String password = "password1";
        if(!StringUtil.isEmptyString(password)){
          subjectLogin.setPassword(PasswordService.encrypt(password));
        }
        aaCRUDService.createSubjectLogin(subjectLogin);

        JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.add.ok", FacesMessage.SEVERITY_INFO);
        this.staff = new StaffTO();
      }
    } catch (InterruptedException e) {
      int errorType = Integer.parseInt(e.getMessage());
      switch (errorType) {
        case 1:
          JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.staffnum.duplicate", FacesMessage.SEVERITY_WARN);
          break;
        case 2:
          JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.loginid.duplicate", FacesMessage.SEVERITY_WARN);
          break;
        case 3:
          JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.department.invalid", FacesMessage.SEVERITY_WARN);
          break;
        default:
          log.info("StaffFormBean addStaffProcess InterruptedException: " + e.toString());
          break;
      }
    } catch (Exception e) {
      log.info("StaffFormBean addStaffProcess Exception: " + e.toString());
      JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.add.error", FacesMessage.SEVERITY_ERROR);
    }
  }

  // Read

  public void searchStaffProcess(final String staffName) {
    PaginationDataModel<StaffTO> refreshedLazyDataModel = new PaginationDataModel<StaffTO>() {
      ListAndPagingInfo<StaffTO> listAndPagingInfo = null;

      public ListAndPagingInfo loadPaginationData(Map filters) {
        listAndPagingInfo = staffSvc.findByName(staffName);
        return listAndPagingInfo;
      }

      public StaffTO getRowData(String rowKey) {
        if (listAndPagingInfo != null) {
          List<StaffTO> list = listAndPagingInfo.getResult();
          for (int i = 0; i < list.size(); i++) {
            StaffTO staffTO = list.get(i);
            String id = staffTO.getId();
            if (id.equals(rowKey)) {
              return staffTO;
            }
          }
        }
        return null;
      }
      public Object getRowKey(StaffTO object) {
        return object.getId();
      }
    };

    int size = refreshedLazyDataModel.getPageSize();
    int pageSize = (size == 0) ? 1 : size;
    refreshedLazyDataModel.initialWrappedData(0, pageSize);
    this.staffList = refreshedLazyDataModel;
  }

  // Update

  public void initEditStaff(String staffId) {
    this.staff = staffSvc.findById(staffId);
  }

  public void updateStaff() {
    try {
      log.info("StaffFormBean updateStaff");
      String loginId = this.staff.getLoginId();
      String deptId = this.staff.getDeptId();
      String roId = this.staff.getRoId();
      this.staff.setEmail(loginId + "@corp.com.sg");
      if (deptId != null && !deptId.equals("null")) {
        this.staff.setDepartment(deptSvc.findById(deptId));
      }
      if (roId != null && !roId.equals("null")) {
        this.staff.setReportingOfficer(staffSvc.findById(roId));
      }
      log.info("StaffFormBean about to update staff: " + this.staff);
      StaffTO updated = staffSvc.update(this.staff);
      if (updated != null) {
        JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.update.ok", FacesMessage.SEVERITY_INFO);
        this.staff = staffSvc.findById(updated.getId());
      }
    } catch (InterruptedException e) {
      int errorType = Integer.parseInt(e.getMessage());
      switch (errorType) {
        case 1:
          JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.staffnum.duplicate", FacesMessage.SEVERITY_WARN);
          break;
        case 2:
          JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.loginid.duplicate", FacesMessage.SEVERITY_WARN);
          break;
        case 3:
          JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.department.invalid", FacesMessage.SEVERITY_WARN);
          break;
        case 4:
          JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.reportingofficer.invalid", FacesMessage.SEVERITY_WARN);
          break;
        case 5:
          JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.missing", FacesMessage.SEVERITY_WARN);
          break;
        default:
          log.info("StaffFormBean updateStaff InterruptedException: " + e.toString());
          break;
      }
    } catch (Exception e) {
      log.info("StaffFormBean updateStaff Exception: " + e.toString());
      JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.add.error", FacesMessage.SEVERITY_ERROR);
    }
  }

  // Delete

  public void deleteStaffProcess(StaffTO[] deleteStaff) {
    log.info("StaffFormBean deleteStaffProcess: " + deleteStaff.length);
    if (deleteStaff == null || deleteStaff.length == 0) {
      JSFTools.processMessage(MESSAGE_PROPS, "label.delete.info", FacesMessage.SEVERITY_WARN);
      return;
    }
    try {
      int length = deleteStaff.length;
      staffSvc.delete(deleteStaff);
      FacesContext.getCurrentInstance().addMessage(null, MessageUtils.getMessage(MESSAGE_PROPS, FacesMessage.SEVERITY_INFO, "msg.staff.delete.ok", length));
    } catch (HibernateOptimisticLockingFailureException e) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.missing", FacesMessage.SEVERITY_WARN);
    } catch (Exception e) {
      log.info("StaffFormBean deleteStaffProcess Exception: " + e.toString());
      JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.delete.error", FacesMessage.SEVERITY_ERROR);
    }
  }
}
