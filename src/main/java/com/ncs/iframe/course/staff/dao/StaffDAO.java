package com.ncs.iframe.course.staff.dao;

import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public interface StaffDAO {

  // Create

  public void save(StaffTO staff);

  // Read

  public ListAndPagingInfo<StaffTO> findByName(String name);

  public ListAndPagingInfo<StaffTO> findByStaffNum(String staffNum);

  public ListAndPagingInfo<StaffTO> findByLoginId(String loginId);

  public StaffTO findById(String id);

  // Update

  public StaffTO update(StaffTO staff);

  // Delete

  public void delete(StaffTO staff);
}
