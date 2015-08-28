package com.ncs.iframe.course.staff.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

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

  // Validators

  public String getDateYesterday() {
    Calendar dateTimeNow = Calendar.getInstance();
    dateTimeNow.add(Calendar.DATE, -1);
    dateTimeNow.set(Calendar.HOUR_OF_DAY, 0);
    dateTimeNow.set(Calendar.MINUTE, 0);
    dateTimeNow.set(Calendar.SECOND, 0);
    dateTimeNow.set(Calendar.MILLISECOND, 0);
    Date returnDate = dateTimeNow.getTime();
    String returnString = String.format("%1$td/%1$tm/%1$tY", returnDate);
    log.info("Date yesterday: " + returnString);
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

  // Create

  public void addStaffProcess() {
    staffSvc.add(this.staff);
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
