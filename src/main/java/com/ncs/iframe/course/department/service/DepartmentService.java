package com.ncs.iframe.course.department.service;

import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public interface DepartmentService {

  // Validators

  public DepartmentTO checkDuplicateNameExists(DepartmentTO dept);

  public DepartmentTO checkExactDeptExists(DepartmentTO dept) throws NullPointerException;

  // Create

  public DepartmentTO add(DepartmentTO department);

  // Read

  public ListAndPagingInfo<DepartmentTO> findByName(String name);

  public DepartmentTO findById(String id);

  //Update

  public DepartmentTO update(DepartmentTO department);

  // Delete

  public void delete(DepartmentTO[] departmentArray);
}
