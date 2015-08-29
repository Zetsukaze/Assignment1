package com.ncs.iframe.course.staff.dao;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.logging.Logger;
import com.ncs.iframe4.commons.pagination.ListAndPagingInfo;
import com.ncs.iframe4.commons.pagination.PaginationContext;
import com.ncs.iframe4.commons.tools.StringUtil;
import com.ncs.iframe4.hibernate.IframeHibernatePaginationDaoSupport;

public class StaffDAOImpl extends IframeHibernatePaginationDaoSupport implements StaffDAO {

  private static final long serialVersionUID = -3268757818024500313L;
  private transient Logger log = Logger.getLogger(getClass());

  // Constructors

  public StaffDAOImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  // Create

  public void save(StaffTO staff) {
    this.getCurrentSession().save(staff);
    log.info("StaffDAOImpl saved StaffTO successful: " + staff);
  }

  // Read

  public ListAndPagingInfo<StaffTO> findByName(String name) {
    log.debug("StaffDAOImpl findByName: " + name);
    DetachedCriteria criteria = DetachedCriteria.forClass(StaffTO.class);

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

  public ListAndPagingInfo<StaffTO> findByStaffNum(String staffNum) {
    log.debug("StaffDAOImpl findByStaffNo: " + staffNum);
    DetachedCriteria criteria = DetachedCriteria.forClass(StaffTO.class);

    if (!StringUtil.isEmptyString(staffNum)) {
      criteria.add(
        Restrictions.like("staffNum", staffNum, MatchMode.EXACT)
      );
    }

    if (PaginationContext.getPaginationSortOrderData() != null && PaginationContext.getPaginationSortOrderData().getSortValue() == null) {
      PaginationContext.getPaginationSortOrderData().setSortValue("staffNum");
      PaginationContext.getPaginationSortOrderData().setAscending(true);
    }
    return findByCriteria4Page(criteria);
  }

  public ListAndPagingInfo<StaffTO> findByLoginId(String loginId) {
    log.debug("StaffDAOImpl findByLoginId: " + loginId);
    DetachedCriteria criteria = DetachedCriteria.forClass(StaffTO.class);

    if (!StringUtil.isEmptyString(loginId)) {
      criteria.add(
        Restrictions.like("loginId", loginId, MatchMode.EXACT)
      );
    }

    if (PaginationContext.getPaginationSortOrderData() != null && PaginationContext.getPaginationSortOrderData().getSortValue() == null) {
      PaginationContext.getPaginationSortOrderData().setSortValue("loginId");
      PaginationContext.getPaginationSortOrderData().setAscending(true);
    }
    return findByCriteria4Page(criteria);
  }

  public StaffTO findById(String id) {
    log.info("StaffDAOImpl findById: " + id);
    Session session = getCurrentSession();
    session.setCacheMode(CacheMode.REFRESH);
    StaffTO instance = (StaffTO) session.get(StaffTO.class, id);
    return instance;
  }

  public ListAndPagingInfo<StaffTO> findByReportingOfficer(String roId) {
    log.info("StaffDAOImpl findByReportingOfficer: " + roId);
    DetachedCriteria criteria = DetachedCriteria.forClass(StaffTO.class);

    if (!StringUtil.isEmptyString(roId)) {
      log.info("StaffDAOImpl findByReportingOfficer step1");
      criteria.add(
        Restrictions.like("reportingOfficer", roId, MatchMode.EXACT)
      );
    }

    if (PaginationContext.getPaginationSortOrderData() != null && PaginationContext.getPaginationSortOrderData().getSortValue() == null) {
      log.info("StaffDAOImpl findByReportingOfficer step2");
      PaginationContext.getPaginationSortOrderData().setSortValue("name");
      PaginationContext.getPaginationSortOrderData().setAscending(true);
      log.info("StaffDAOImpl findByReportingOfficer step3");
    }
    ListAndPagingInfo<StaffTO> returnStuff = findByCriteria4Page(criteria);
    log.info("StaffDAOImpl findByReportingOfficer step4");
    return returnStuff;
  }

  // Update

  public StaffTO update(StaffTO staff) {
    StaffTO updateStaffTO = (StaffTO) getCurrentSession().get(StaffTO.class, staff.getId());
    BeanUtils.copyProperties(staff, updateStaffTO);
    log.info("Updated StaffTO with id: " + staff.getId());
    return updateStaffTO;
  }

  // Delete

  public void delete(StaffTO staff) {
    log.info("StaffDAOImpl delete: " + staff);
    getCurrentSession().delete(staff);
  }
}
