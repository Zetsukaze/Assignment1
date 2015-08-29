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

  public void checkCyclicReportingOfficer(StaffTO staff, final StaffTO firstStaff) throws InterruptedException {
    log.info("StaffServiceImpl STARTING cyclic check");
    StaffTO ro = staff.getReportingOfficer();
    if (ro == null) {
      log.info("StaffServiceImpl did not find a cyclic relationship!");
      return;
    }
    String roId = ro.getId();
    staff.setRoId(roId);
    log.info("StaffServiceImpl checking staff id: " + firstStaff.getId() + " for cyclic relationship with RO: " + roId + " Name: " + ro.getName());
    if (roId.equals(firstStaff.getId())) {
      log.info("StaffServiceImpl found cyclic relationship!");
      throw new InterruptedException("4");
    }
    log.info("StaffServiceImpl ENDING cyclic check");
    checkCyclicReportingOfficer(ro, firstStaff);
  }

  public StaffTO checkExactStaffExists(StaffTO staff) throws InterruptedException {
    StaffTO checkExist = staffDAO.findById(staff.getId());
    if (checkExist != null) {
      List<StaffTO> duplicateStaffNum = staffDAO.findByStaffNum(checkExist.getStaffNum()).getResult();
      List<StaffTO> duplicateStaffLoginId = staffDAO.findByLoginId(checkExist.getLoginId()).getResult();
      if (duplicateStaffNum.size() == 0 || duplicateStaffNum.size() == 1 && duplicateStaffNum.get(0).getId().equals(checkExist.getId())) {
        if (duplicateStaffLoginId.size() == 0 || duplicateStaffLoginId.size() == 1 && duplicateStaffLoginId.get(0).getId().equals(checkExist.getId())) {
          return checkExist;
        } else {
          throw new InterruptedException("2");
        }
      } else {
        throw new InterruptedException("1");
      }
    }
    throw new InterruptedException("5");
  }

  // Create

  public StaffTO add(StaffTO staff) throws InterruptedException {
    StaffTO duplicateStaffNum = checkDuplicateStaffNumExists(staff);
    StaffTO duplicateLoginId = checkDuplicateLoginIdExists(staff);
    String deptId = staff.getDeptId();

    if (duplicateStaffNum == null) {
      throw new InterruptedException("1");
    }

    if (duplicateLoginId == null) {
      throw new InterruptedException("2");
    }

    if (deptId != null) {
      DepartmentTO departmentExists = departmentDAO.findById(staff.getDeptId());
      if (departmentExists != null) {
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

  // Update

  public StaffTO update(StaffTO staff) throws InterruptedException {
    StaffTO duplicateAnything = checkExactStaffExists(staff);
    DepartmentTO tempDept = staff.getDepartment();
    if (tempDept != null) {
      String deptId = tempDept.getId();
      if (deptId != null) {
        DepartmentTO departmentExists = departmentDAO.findById(deptId);
        if (departmentExists == null) {
          throw new InterruptedException("3");
        }
      }
    }

    log.info("StaffServiceImpl BEFORE cyclic check");
    checkCyclicReportingOfficer(staff, staff);
    log.info("StaffServiceImpl AFTER cyclic check");

    StaffTO updated = staffDAO.update(staff);
    return updated;
  }

  // Delete

  public void delete(StaffTO[] staffArray) {
    if (staffArray != null && staffArray.length > 0) {
      for (int i = 0; i < staffArray.length; i++) {
        StaffTO staff = staffArray[i];
        List<StaffTO> directReports = staff.getStaffList();
        for (int i1 = 0; i1 < directReports.size(); i1++) {
          StaffTO underling = directReports.get(i1);
          underling.setReportingOfficer(null);
          underling.setRoId(null);
          staffDAO.update(underling);
        }
        staffDAO.delete(staff);
      }
    }
  }

}
