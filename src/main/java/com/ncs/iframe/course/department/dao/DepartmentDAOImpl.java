package com.ncs.iframe.course.department.dao;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.ncs.iframe.course.department.to.DepartmentTO;
import com.ncs.iframe4.commons.logging.Logger;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;
import com.ncs.iframe4.commons.pagination.PaginationContext;
import com.ncs.iframe4.commons.tools.StringUtil;
import com.ncs.iframe4.hibernate.IframeHibernatePaginationDaoSupport;

public class DepartmentDAOImpl extends IframeHibernatePaginationDaoSupport implements DepartmentDAO {

  private static final long serialVersionUID = -8713976812246799563L;
  private transient Logger log = Logger.getLogger(getClass());

  // Constructors

  public DepartmentDAOImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  // Create

  public void save(DepartmentTO dept) {
    this.getCurrentSession().save(dept);
    log.info("Saved departmentTO successful: " + dept);
  }

  // Read

  public ListAndPagingInfo<DepartmentTO> findByName(String name) {
    log.debug("Calling DepartmentDAOImpl findByName method, name: " + name);
    DetachedCriteria criteria = DetachedCriteria.forClass(DepartmentTO.class);

    if (!StringUtil.isEmptyString(name)) {
      criteria.add(
        Restrictions.like("name", name, MatchMode.ANYWHERE)
      );
    }

    if (PaginationContext.getPaginationSortOrderData() != null && PaginationContext.getPaginationSortOrderData().getSortValue() == null) {
      PaginationContext.getPaginationSortOrderData().setSortValue("name");
      PaginationContext.getPaginationSortOrderData().setAscending(true);
    }
    return findByCriteria4Page(criteria);
  }

  public ListAndPagingInfo<DepartmentTO> findByExactName(String name) {
    log.debug("Calling DepartmentDAOImpl findByExactName method, name: " + name);
    DetachedCriteria criteria = DetachedCriteria.forClass(DepartmentTO.class);

    if (!StringUtil.isEmptyString(name)) {
      criteria.add(
        Restrictions.like("name", name, MatchMode.EXACT)
      );
    }

    if (PaginationContext.getPaginationSortOrderData() != null && PaginationContext.getPaginationSortOrderData().getSortValue() == null) {
      PaginationContext.getPaginationSortOrderData().setSortValue("name");
      PaginationContext.getPaginationSortOrderData().setAscending(true);
    }
    return findByCriteria4Page(criteria);
  }

  public DepartmentTO findById(String id) {
    log.info("Calling DepartmentDAOImpl findById ID: " + id);
    Session session = getCurrentSession();
    session.setCacheMode(CacheMode.REFRESH);
    DepartmentTO instance = (DepartmentTO) session.get(DepartmentTO.class, id);
    return instance;
  }

  // Update

  public DepartmentTO update(DepartmentTO dept) {
    DepartmentTO updateDepartmentTO = (DepartmentTO) getCurrentSession().get(DepartmentTO.class, dept.getId());
    BeanUtils.copyProperties(dept, updateDepartmentTO);
    log.info("Updated DepartmentTO with id: " + dept.getId());
    return updateDepartmentTO;
  }

  // Delete

  public void delete(DepartmentTO dept) {
    log.info("DepartmentDAOImpl delete: " + dept);
    getCurrentSession().delete(dept);
  }
}
