package com.ncs.iframe.course.staff.service;

import com.ncs.iframe.course.staff.dao.StaffDAO;
import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public class StaffServiceImpl implements StaffService {

  private StaffDAO staffDAO;

  // Getters

  public StaffDAO getStaffDAO() {
    return staffDAO;
  }

  // Setters

  public void setStaffDAO(StaffDAO staffDAO) {
    this.staffDAO = staffDAO;
  }

  // Validators

  // Create

  public StaffTO add(StaffTO staff) {
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
