package com.ncs.iframe.course.staff.service;

import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public interface StaffService {

  // Validators

  public StaffTO checkDuplicateStaffNumExists(StaffTO staff);

  public StaffTO checkDuplicateLoginIdExists(StaffTO staff);
  
  public boolean checkCyclicReportingOfficer(StaffTO staff);
  
  public StaffTO checkExactStaffExists(StaffTO staff) throws InterruptedException;

  // Create

  public StaffTO add(StaffTO staff) throws InterruptedException;

  // Read

  public ListAndPagingInfo<StaffTO> findByName(String name);

  public StaffTO findById(String id);

  // Update

  public StaffTO update(StaffTO staff)throws InterruptedException;

  // Delete

  public void delete(StaffTO[] staffArray);

}
