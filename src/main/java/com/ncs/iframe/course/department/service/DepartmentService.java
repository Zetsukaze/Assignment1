package com.ncs.iframe.course.department.service;

import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public interface DepartmentService {

  public DepartmentTO add(DepartmentTO department);

  public DepartmentTO update(DepartmentTO department);

  public DepartmentTO delete(DepartmentTO department);

  public ListAndPagingInfo<DepartmentTO> findByName(String name);

  public DepartmentTO findById(String id);

}
