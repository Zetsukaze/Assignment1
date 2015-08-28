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

import com.ncs.iframe.course.department.service.DepartmentService;
import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe.course.staff.service.StaffService;
import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.logging.Logger;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;
import com.ncs.iframe4.jsf.message.MessageUtils;
import com.ncs.iframe4.jsf.pagination.PaginationDataModel;
import com.ncs.iframe4.jsf.util.JSFTools;

public class StaffFormBean {

  private static final String MESSAGE_PROPS = "com.ncs.iframe4.jsf.i18n.SampleMessages";
  private transient Logger log = Logger.getLogger(getClass());
  private String name;
  private StaffService staffSvc;
  private LazyDataModel<StaffTO> staffList;
  private StaffTO[] selectedStaff;
  private StaffTO staff = new StaffTO();
  private DepartmentService deptSvc;


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
        FacesContext.getCurrentInstance().addMessage(null, MessageUtils.getMessage(MESSAGE_PROPS, FacesMessage.SEVERITY_INFO, "msg.staff.photo.uploaded", fileName));
      } catch (IOException e) {
        log.info("StaffFormBean handleFileUpload failure: " + e.toString());
        JSFTools.processMessage(MESSAGE_PROPS, "msg.staff.photo.uploadfailed", FacesMessage.SEVERITY_WARN);
      }
    }
  }

  // Create

  public void addStaffProcess() {
    try {
      String loginId = this.staff.getLoginId();
      String deptId = this.staff.getDeptId();
      String roId = this.staff.getRoId();
      this.staff.setEmail(loginId + "@corp.com.sg");
      this.staff.setDepartment(deptSvc.findById(deptId));
      this.staff.setReportingOfficer(staffSvc.findById(roId));
      StaffTO addition = staffSvc.add(this.staff);
      if (addition != null) {
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
          log.info("StaffFormBean InterruptedException: " + e.toString());
          break;
      }
    } catch (Exception e) {
      log.info("StaffFormBean Exception: " + e.toString());
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
    staffSvc.update(this.staff);
  }

  // Delete

  public void deleteStaffProcess(StaffTO[] deleteStaff) {
    staffSvc.delete(deleteStaff);
  }

}
