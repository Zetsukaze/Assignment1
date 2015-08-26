package com.ncs.iframe.course.department.dao;

import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public interface DepartmentDAO {

  // Create
  public void save(DepartmentTO dept);

  // Read
  public ListAndPagingInfo<DepartmentTO> findByName(String name);
  
  public ListAndPagingInfo<DepartmentTO> findByExactName(String name);

  public DepartmentTO findById(String id);

  // Update
  public DepartmentTO update(DepartmentTO dept);

  // Delete
  public void delete(DepartmentTO dept);
}
