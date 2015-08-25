package com.ncs.iframe.course.department.bean;

import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import com.ncs.iframe.course.department.service.DepartmentService;
import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;
import com.ncs.iframe4.jsf.message.MessageUtils;
import com.ncs.iframe4.jsf.pagination.PaginationDataModel;
import com.ncs.iframe4.jsf.util.JSFTools;

public class DepartmentFormBean {

  private static final String MESSAGE_PROPS = "com.ncs.iframe4.jsf.i18n.SampleMessages";
  private LazyDataModel<DepartmentTO> departmentList;
  private DepartmentTO[] selectedDepartments;
  private DepartmentService deptSvc;
  private DepartmentTO dept = new DepartmentTO();
  private String name;

  // Constructor

  public DepartmentFormBean() {
    departmentList = new PaginationDataModel<DepartmentTO>() {

      public ListAndPagingInfo loadPaginationData(Map filters) {
        return new ListAndPagingInfo();
      }

      public DepartmentTO getRowData(String rowKey) {
        return null;
      }

      public Object getRowKey(DepartmentTO object) {
        return null;
      }
    };
  }

  // Getters

  public String getName() {
    return name;
  }

  public DepartmentService getDepartmentService() {
    return deptSvc;
  }

  public LazyDataModel<DepartmentTO> getDepartmentList() {
    return departmentList;
  }

  public DepartmentTO[] getSelectedDepartments() {
    return selectedDepartments;
  }

  public DepartmentTO getDepartment() {
    return dept;
  }

  // Setters

  public void setName(String name) {
    this.name = name;
  }

  public void setDepartmentService(DepartmentService deptSvc) {
    this.deptSvc = deptSvc;
  }

  public void setDepartmentList(LazyDataModel<DepartmentTO> departmentList) {
    this.departmentList = departmentList;
  }

  public void setSelectedDepartments(DepartmentTO[] selectedDepartments) {
    this.selectedDepartments = selectedDepartments;
  }

  public void setDepartment(DepartmentTO dept) {
    this.dept = dept;
  }

  // Create

  public void addDepartmentProcess() {
    deptSvc.add(this.dept);
    this.dept = new DepartmentTO();
    JSFTools.processMessage(MESSAGE_PROPS, "msg.added", FacesMessage.SEVERITY_INFO);
  }

  // Read

  public void searchDepartmentsProcess(final String departmentName) {
    PaginationDataModel<DepartmentTO> refreshedLazyDataModel = new PaginationDataModel<DepartmentTO>() {
      ListAndPagingInfo<DepartmentTO> listAndPagingInfo = null;

      public ListAndPagingInfo loadPaginationData(Map filters) {
        listAndPagingInfo = deptSvc.findByName(departmentName);
        return listAndPagingInfo;
      }

      public DepartmentTO getRowData(String rowKey) {
        if (listAndPagingInfo != null) {
          List<DepartmentTO> list = listAndPagingInfo.getResult();
          for (int i = 0; i < list.size(); i++) {
            DepartmentTO departmentTO = list.get(i);
            String id = departmentTO.getId();
            if (id.equals(rowKey)) {
              return departmentTO;
            }
          }
        }
        return null;
      }
      public Object getRowKey(DepartmentTO object) {
        return object.getId();
      }
    };

    int size = refreshedLazyDataModel.getPageSize();
    int pageSize = (size == 0) ? 1 : size;
    refreshedLazyDataModel.initialWrappedData(0, pageSize);
    this.departmentList = refreshedLazyDataModel;
  }

  // Update

  public void initEditDepartment(String departmentId) {
    this.dept = this.getDepartmentService().findById(departmentId);
  }

  public void updateDepartment() {
    this.dept = getDepartmentService().update(dept);
    JSFTools.processMessage(MESSAGE_PROPS, "msg.sample.updatesuccess", FacesMessage.SEVERITY_INFO);
  }

  // Delete

  public void deleteDepartmentsProcess() {
    if (selectedDepartments == null || selectedDepartments.length == 0) {
      FacesMessage message = MessageUtils.getMessage(MESSAGE_PROPS, FacesMessage.SEVERITY_ERROR, "label.deleteinfo", null);
      message.setSeverity(FacesMessage.SEVERITY_ERROR);
      FacesContext.getCurrentInstance().addMessage(null, message);
      return;
    }

    this.getDepartmentService().delete(selectedDepartments);
    FacesMessage message = MessageUtils.getMessage(MESSAGE_PROPS, FacesMessage.SEVERITY_INFO, "label.deleteok", null);
    message.setSeverity(FacesMessage.SEVERITY_INFO);
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

}
