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
    // Create
    DepartmentTO dept = new DepartmentTO();
    dept.setName("test");
    dept.setDesc("testDesc");
    dept = deptSvc.add(dept);

    assertNotNull(dept);
    assertNotNull(dept.getId());

    // Update
    dept.setName("testU");
    dept = deptSvc.update(dept);
    dept = deptSvc.findById(dept.getId());
    assertNotNull(dept);
    assertNotNull("testUpdateName", dept.getName());
  }

  @Test
  public void testFindByName() {
    // Create
    DepartmentTO dept1 = new DepartmentTO();
    dept1.setName("test1");
    dept1.setDesc("testDesc1");
    dept1 = deptSvc.add(dept1);

    DepartmentTO dept2 = new DepartmentTO();
    dept2.setName("test2");
    dept2.setDesc("testDesc2");
    dept2 = deptSvc.add(dept2);

    // Full match test
    ListAndPagingInfo<DepartmentTO> result1 = deptSvc.findByName("test1");
    assertNotNull(result1);
    assertNotNull(result1.getResult());
    assert(result1.getResult().size() > 0);

    // Partial match
    ListAndPagingInfo<DepartmentTO> result2 = deptSvc.findByName("testN");
    assertNotNull(result2);
    assertNotNull(result2.getResult());
    assert(result2.getResult().size() > 0);
  }
}
