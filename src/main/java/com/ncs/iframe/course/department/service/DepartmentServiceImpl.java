package com.ncs.iframe.course.department.service;

import java.util.List;

import com.ncs.iframe.course.department.dao.DepartmentDAO;
import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;

public class DepartmentServiceImpl implements DepartmentService {

  private DepartmentDAO departmentDAO;

  // Getters

  public DepartmentDAO getDepartmentDAO() {
    return departmentDAO;
  }

  // Setters

  public void setDepartmentDAO(DepartmentDAO departmentDAO) {
    this.departmentDAO = departmentDAO;
  }

  // Create
  public DepartmentTO add(DepartmentTO dept) {
    boolean hasDuplicate = false;
    List<DepartmentTO> duplicateList = departmentDAO.findByName(dept.getName()).getResult();
    for (int i = 0; i < duplicateList.size(); i ++) {
      DepartmentTO duplicate = duplicateList.get(i);
      if (duplicate.getName().equals(dept.getName())) {
        hasDuplicate = true;
      }
    }
    if (!hasDuplicate) {
      departmentDAO.save(dept);
      return dept;
    }
    return null;
  }

  // Read
  public ListAndPagingInfo<DepartmentTO> findByName(String name) {
    return departmentDAO.findByName(name);
  }

  public DepartmentTO findById(String id) {
    DepartmentTO dept = departmentDAO.findById(id);
    return dept;
  }

  // Update
  public DepartmentTO update(DepartmentTO dept) {
    DepartmentTO updated = departmentDAO.update(dept);
    return updated;
  }

  public void delete(DepartmentTO[] departments) {
    if (departments != null && departments.length > 0) {
      for (int i = 0; i < departments.length; i++) {
        DepartmentTO department = departments[i];
        this.getDepartmentDAO().delete(department);
      }
    }
  }

}
