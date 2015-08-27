package com.ncs.iframe.course.staff.service;

import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public interface StaffService {

  // Create

  public StaffTO add(StaffTO staff);

  // Read

  public ListAndPagingInfo<StaffTO> findByName(String name);

  public StaffTO findById(String id);

  // Update

  public StaffTO update(StaffTO staff);

  // Delete

  public void delete(StaffTO[] staffArray);

}
