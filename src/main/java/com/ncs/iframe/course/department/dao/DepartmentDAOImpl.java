package com.ncs.iframe.course.department.dao;

import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;
import com.ncs.iframe4.commons.pagination.PaginationContext;
import com.ncs.iframe4.commons.tools.StringUtil;
import com.ncs.iframe4.hibernate.IframeHibernatePaginationDaoSupport;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

public class DepartmentDAOImpl extends IframeHibernatePaginationDaoSupport implements DepartmentDAO {

  private static final long serialVersionUID = -8713976812246799563L;

  public DepartmentDAOImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  // Create
  public void save(DepartmentTO dept) {
    this.getCurrentSession().save(dept);
  }

  // Read
  public ListAndPagingInfo<DepartmentTO> findByName(String name) {
    DetachedCriteria criteria = DetachedCriteria.forClass(DepartmentTO.class);

    if (!StringUtil.isEmptyString(name)) {
      criteria.add(Restrictions.or(
        Restrictions.like("name", name, MatchMode.ANYWHERE)
      ));
    }

    if (PaginationContext.getPaginationSortOrderData() != null && PaginationContext.getPaginationSortOrderData().getSortValue() == null) {
      PaginationContext.getPaginationSortOrderData().setSortValue("name");
      PaginationContext.getPaginationSortOrderData().setAscending(false);
    }
    return findByCriteria4Page(criteria);
  }

  public DepartmentTO findById(String id) {
    DepartmentTO instance = (DepartmentTO) getCurrentSession().get(DepartmentTO.class, id);
    return instance;
  }

  // Update
  public DepartmentTO update(DepartmentTO dept) {
    DepartmentTO updateDepartmentTO = (DepartmentTO) getCurrentSession().get(DepartmentTO.class, dept.getId());
    BeanUtils.copyProperties(dept, updateDepartmentTO);
    getCurrentSession().update(updateDepartmentTO);
    return updateDepartmentTO;
  }

  // Delete
  public void delete(DepartmentTO dept) {
    getCurrentSession().delete(dept);
  }
}
