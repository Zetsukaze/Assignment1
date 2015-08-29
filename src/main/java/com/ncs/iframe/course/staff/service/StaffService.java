package com.ncs.iframe.course.staff.service;

import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public interface StaffService {

  // Validators

  public StaffTO checkDuplicateStaffNumExists(StaffTO staff);

  public StaffTO checkDuplicateLoginIdExists(StaffTO staff);

  // Create

  public StaffTO add(StaffTO staff) throws InterruptedException;

  // Read

  public ListAndPagingInfo<StaffTO> findByName(String name);

  public StaffTO findById(String id);

  public ListAndPagingInfo<StaffTO> findByReportingOfficer(String roId);

  // Update

  public StaffTO update(StaffTO staff);

  // Delete

  public void delete(StaffTO[] staffArray);

}
