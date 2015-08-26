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
    List<DepartmentTO> duplicateList = departmentDAO.findByExactName(dept.getName()).getResult();
    if (duplicateList.size() == 0) {
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
    List<DepartmentTO> duplicateList = departmentDAO.findByExactName(dept.getName()).getResult();
    if (duplicateList.size() == 0) {
      DepartmentTO updated = departmentDAO.update(dept);
      return updated;
    }
    return null;
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
