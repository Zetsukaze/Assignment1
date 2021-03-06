package com.ncs.iframe.course.department.bean;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.ncs.iframe.course.department.service.DepartmentService;
import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe4.commons.logging.Logger;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;
import com.ncs.iframe4.jsf.message.MessageUtils;
import com.ncs.iframe4.jsf.pagination.PaginationDataModel;
import com.ncs.iframe4.jsf.util.JSFTools;

public class DepartmentFormBean {

  private static final String MESSAGE_PROPS = "com.ncs.iframe4.jsf.i18n.SampleMessages";
  private transient Logger log = Logger.getLogger(getClass());
  private String name;
  private DepartmentService deptSvc;
  private LazyDataModel<DepartmentTO> departmentList;
  private DepartmentTO[] selectedDepartments;
  private DepartmentTO dept = new DepartmentTO();

  // Constructors

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

  // Validators

  public String checkViewDepartmentProcess(String departmentId) {
    String redirectList = "/xhtml/department/list.xhtml";
    String redirectView = "/xhtml/department/view.xhtml";
    DepartmentTO checkDept = deptSvc.findById(departmentId);
    if (checkDept == null) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.department.missing", FacesMessage.SEVERITY_WARN);
      return redirectList;
    }
    return redirectView;
  }

  public String checkUpdateDepartmentProcess(String departmentId) {
    String redirectList = "/xhtml/department/list.xhtml";
    String redirectUpdate = "/xhtml/department/update.xhtml";
    DepartmentTO checkDept = deptSvc.findById(departmentId);
    if (checkDept == null) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.department.missing", FacesMessage.SEVERITY_WARN);
      return redirectList;
    }
    return redirectUpdate;
  }

  public String checkDuplicateNameUpdateDepartmentProcess(DepartmentTO checkDept) {
    String redirectUpdate = "/xhtml/department/update.xhtml";
    String redirectView = "/xhtml/department/view.xhtml";
    log.info("DepartmentFormBean checking for duplicates..");
    try {
      DepartmentTO checkExactDept = deptSvc.checkExactDeptExists(checkDept);
      if (checkExactDept == null) {
        log.info("DepartmentFormBean found duplicate!");
        // JSFTools.processMessage(MESSAGE_PROPS, "msg.department.duplicate", FacesMessage.SEVERITY_WARN);
        return redirectUpdate;
      }
      log.info("DepartmentFormBean found no duplicates, should be able to update CheckExact: " + checkExactDept);
      return redirectView;
    } catch (NullPointerException e) {
      log.info("DepartmentFormBean encountered NullPointerException while searching for duplicates...");
      // JSFTools.processMessage(MESSAGE_PROPS, "msg.department.missing", FacesMessage.SEVERITY_WARN);
      // this.dept = null;
      return redirectView;
    }
  }

  // Create

  public void addDepartmentProcess() {
    try {
      DepartmentTO addition = deptSvc.add(this.dept);
      if (addition != null) {
        JSFTools.processMessage(MESSAGE_PROPS, "msg.department.add.ok", FacesMessage.SEVERITY_INFO);
        this.dept = new DepartmentTO();
      } else {
        JSFTools.processMessage(MESSAGE_PROPS, "msg.department.duplicate", FacesMessage.SEVERITY_WARN);
      }
    } catch (Exception e) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.department.add.error", FacesMessage.SEVERITY_ERROR);
    }
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

  public String reloadView(String departmentId) {
    this.dept = deptSvc.findById(departmentId);
    return "/xhtml/department/view.xhtml";
  }

  // Update

  public void initEditDepartment(String departmentId) {
    this.dept = deptSvc.findById(departmentId);
  }

  public void updateDepartment() {
    try {
      DepartmentTO updated = deptSvc.update(this.dept);
      if (updated != null) {
        JSFTools.processMessage(MESSAGE_PROPS, "msg.department.update.ok", FacesMessage.SEVERITY_INFO);
      } else {
        JSFTools.processMessage(MESSAGE_PROPS, "msg.department.duplicate", FacesMessage.SEVERITY_WARN);
      }
    } catch (NullPointerException e) {
      log.info("DepartmentFormBean NullPointer: " + e.toString());
      JSFTools.processMessage(MESSAGE_PROPS, "msg.department.missing", FacesMessage.SEVERITY_WARN);
      this.dept = null;
    } catch (Exception e) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.department.update.error", FacesMessage.SEVERITY_ERROR);
      // JSFTools.processMessage(MESSAGE_PROPS, e.toString(), FacesMessage.SEVERITY_ERROR);
      this.dept = null;
    }
  }

  // Delete

  public void deleteDepartmentsProcess(DepartmentTO[] deleteDepartments) {
    log.info("DepartmentFormBean deleteDepartmentProcess: " + deleteDepartments.length);
    if (deleteDepartments == null || deleteDepartments.length == 0) {
      JSFTools.processMessage(MESSAGE_PROPS, "label.delete.info", FacesMessage.SEVERITY_WARN);
      return;
    }

    try {
      int length = deleteDepartments.length;
      deptSvc.delete(deleteDepartments);
      FacesContext.getCurrentInstance().addMessage(null, MessageUtils.getMessage(MESSAGE_PROPS, FacesMessage.SEVERITY_INFO, "msg.department.delete.ok", length));
    } catch (HibernateOptimisticLockingFailureException e) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.department.missing", FacesMessage.SEVERITY_WARN);
    } catch (InterruptedException e) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.department.delete.staff", FacesMessage.SEVERITY_WARN);
    } catch (Exception e) {
      JSFTools.processMessage(MESSAGE_PROPS, "msg.department.delete.error", FacesMessage.SEVERITY_ERROR);
      // JSFTools.processMessage(MESSAGE_PROPS, e.toString(), FacesMessage.SEVERITY_ERROR);
    }
  }
}
