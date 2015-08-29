package com.ncs.iframe.course.staff.service;

import java.util.List;

import com.ncs.iframe.course.department.dao.DepartmentDAO;
import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe.course.staff.dao.StaffDAO;
import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.logging.Logger;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public class StaffServiceImpl implements StaffService {

  private transient Logger log = Logger.getLogger(getClass());
  private StaffDAO staffDAO;
  private DepartmentDAO departmentDAO;

  // Getters

  public StaffDAO getStaffDAO() {
    return staffDAO;
  }

  public DepartmentDAO getDepartmentDAO() {
    return departmentDAO;
  }

  // Setters

  public void setStaffDAO(StaffDAO staffDAO) {
    this.staffDAO = staffDAO;
  }

  public void setDepartmentDAO(DepartmentDAO departmentDAO) {
    this.departmentDAO = departmentDAO;
  }

  // Validators

  public StaffTO checkDuplicateStaffNumExists(StaffTO staff) {
    List<StaffTO> duplicateList = staffDAO.findByStaffNum(staff.getStaffNum()).getResult();
    if (duplicateList.size() == 0) {
      return staff;
    }
    return null;
  }

  public StaffTO checkDuplicateLoginIdExists(StaffTO staff) {
    log.info("StaffServiceImpl checking for duplicate loginId: " + staff.getLoginId());
    List<StaffTO> duplicateList = staffDAO.findByLoginId(staff.getLoginId()).getResult();
    if (duplicateList.size() == 0) {
      return staff;
    }
    return null;
  }

  public boolean checkCyclicReportingOfficer(StaffTO staff) {
    StaffTO changedRo = staff.getReportingOfficer();
    if (changedRo != null) {
      if (changedRo.getId().equals(staff.getId())) {
        return true;
      }
      checkCyclicReportingOfficer(changedRo);
    }
    return false;
  }

  // Create

  public StaffTO add(StaffTO staff) throws InterruptedException {
    StaffTO duplicateStaffNum = checkDuplicateStaffNumExists(staff);
    StaffTO duplicateLoginId = checkDuplicateLoginIdExists(staff);
    DepartmentTO departmentExists = staff.getDepartment();

    if (duplicateStaffNum == null) {
      throw new InterruptedException("1");
    }

    if (duplicateLoginId == null) {
      throw new InterruptedException("2");
    }

    if (departmentExists != null) {
      DepartmentTO department = departmentDAO.findById(departmentExists.getId());
      if (department == null) {
        throw new InterruptedException("3");
      }
    }

    staffDAO.save(staff);
    return staff;
  }

  // Read

  public ListAndPagingInfo<StaffTO> findByName(String name) {
    return staffDAO.findByName(name);
  }

  public StaffTO findById(String id) {
    StaffTO staff = staffDAO.findById(id);
    return staff;
  }

  public ListAndPagingInfo<StaffTO> findByReportingOfficer(String roId) {
    return staffDAO.findByReportingOfficer(roId);
  }

  // Update

  public StaffTO update(StaffTO staff) {
    StaffTO updated = staffDAO.update(staff);
    return updated;
  }

  // Delete

  public void delete(StaffTO[] staffArray) {
    if (staffArray != null && staffArray.length > 0) {
      for (int i = 0; i < staffArray.length; i++) {
        StaffTO staff = staffArray[i];
        staffDAO.delete(staff);
      }
    }
  }

}
