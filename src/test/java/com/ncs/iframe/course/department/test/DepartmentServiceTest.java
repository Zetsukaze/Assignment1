package com.ncs.iframe.course.department.test;

import static org.junit.Assert.*;

import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe.course.department.service.DepartmentService;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"/spring/testing-beans.xml" ,"/test-system-infrastructure-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

public class DepartmentServiceTest {
  @Autowired
  private DepartmentService deptSvc;

  // Getters

  public DepartmentService getDepartmentService() {
    return deptSvc;
  }

  // Setters

  public void addDepartmentService(DepartmentService deptSvc) {
    this.deptSvc = deptSvc;
  }

  // Tests

  @Test
  public void testCRUD() {
    DepartmentTO dept = new DepartmentTO();
    dept.setID("testID");
    dept.setName("testName");
    dept.setDesc("testDesc");
    dept = deptSvc.add(dept);

    assertNotNull(dept);
  }

  @Test
  public void testFindByName() {
    DepartmentTO dept = new DepartmentTO();
    dept.setID("testID");
    dept.setName("testName");
    dept.setDesc("testDesc");
    dept = deptSvc.add(dept);
    ListAndPagingInfo<DepartmentTO> result = deptSvc.findByName("testName");

    assertNotNull(result);
  }

}
