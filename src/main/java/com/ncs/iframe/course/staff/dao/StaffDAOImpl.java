package com.ncs.iframe.course.staff.dao;

import java.sql.Timestamp;
import java.util.Calendar;

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
import com.ncs.iframe4.commons.to.SubjectBaseTO;
import com.ncs.iframe4.commons.to.SubjectLoginBaseTO;
import com.ncs.iframe4.commons.to.SubjectLoginBaseTOId;
import com.ncs.iframe4.commons.tools.StringUtil;
import com.ncs.iframe4.hibernate.IframeHibernatePaginationDaoSupport;
import com.ncs.itrust4.core.ITrustConstants;
import com.ncs.itrust4.core.service.AACRUDService;
import com.ncs.itrust4.crypto.PasswordService;

public class StaffDAOImpl extends IframeHibernatePaginationDaoSupport implements StaffDAO {

  private static final long serialVersionUID = -3268757818024500313L;
  private transient Logger log = Logger.getLogger(getClass());
  private AACRUDService aaCRUDService;

  // Constructors

  public StaffDAOImpl(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  // Getters

  public AACRUDService getAaCRUDService() {
    return aaCRUDService;
  }

  // Setters

  public void setAaCRUDService(AACRUDService aaCRUDService) {
    this.aaCRUDService = aaCRUDService;
  }

  // Create

  public void save(StaffTO staff) {
    Calendar dateTimeNow = Calendar.getInstance();
    Timestamp timestamp = new Timestamp(dateTimeNow.getTimeInMillis());
    String subjectId = staff.getId();
    log.info("StaffDAOImpl before saving staff: " + subjectId);
    SubjectBaseTO subject = aaCRUDService.getSubjectTOInstance();
    subject.setFirstName(staff.getName());
    subject.setEmail(staff.getEmail());
    subject.setStatus(ITrustConstants.ACTIVE_STATUS_VALUE);
    aaCRUDService.createSubject(subject);
    subjectId = subject.getSubjectId();

    log.info("StaffDAOImpl after saving staff iTrust: " + subjectId);
    SubjectLoginBaseTO subjectLogin = this.aaCRUDService.getSubjectLoginTOInstance();
    subjectLogin.setSubjectId(subjectId);
    subjectLogin.setLoginMechanism(ITrustConstants.PASSWORD_AUTH_METHOD);
    subjectLogin.setLoginName(staff.getLoginId());
    subjectLogin.setPasswordChangedDate(timestamp);
    String password = "password1";
    if(!StringUtil.isEmptyString(password)){
      subjectLogin.setPassword(PasswordService.encrypt(password));
    }
    aaCRUDService.createSubjectLogin(subjectLogin);
    log.info("StaffDAOImpl after saving staff iTrustLogin: " + subjectId);
    staff.setId(subjectId);
    this.getCurrentSession().save(staff);
    // StaffTO addStaffTO = (StaffTO) getCurrentSession().get(StaffTO.class, staff.getId());

    // BeanUtils.copyProperties(staff, addStaffTO);
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

  // Update

  public StaffTO update(StaffTO staff) {
    StaffTO updateStaffTO = (StaffTO) getCurrentSession().get(StaffTO.class, staff.getId());

    log.info("StaffDAOImpl update iTrust START: " + updateStaffTO);
    String subjectId = updateStaffTO.getId();
    String subjectName = staff.getName();
    String subjectEmail = staff.getEmail();
    String subjectLoginId = staff.getLoginId();

    SubjectBaseTO baseSubject = aaCRUDService.getSubjectTOInstance();
    log.info("StaffDAOImpl update iTrust line 129");
    baseSubject.setSubjectId(subjectId);
    SubjectBaseTO subject = aaCRUDService.getSubject(baseSubject);
    log.info("StaffDAOImpl update iTrust line 132");
    subject.setFirstName(subjectName);
    log.info("StaffDAOImpl update iTrust line 134");
    subject.setEmail(subjectEmail);
    log.info("StaffDAOImpl update iTrust line 136");
    aaCRUDService.updateSubject(subject);
    log.info("StaffDAOImpl update iTrust line 138");

    log.info("StaffDAOImpl update iTrust LOGIN update: " + subject);
    SubjectLoginBaseTOId baseSubjectLoginId = aaCRUDService.getSubjectLoginTOIdInstance();
    baseSubjectLoginId.setSubjectId(subjectId);
    baseSubjectLoginId.setLoginMechanism(ITrustConstants.PASSWORD_AUTH_METHOD);
    SubjectLoginBaseTO baseSubjectLogin = aaCRUDService.getSubjectLoginTOInstance();
    baseSubjectLogin.setId(baseSubjectLoginId);
    SubjectLoginBaseTO subjectLogin = aaCRUDService.getSubjectLogin(baseSubjectLogin);
    subjectLogin.setLoginName(subjectLoginId);
    aaCRUDService.updateSubjectLogin(subjectLogin);
    log.info("StaffDAOImpl iTrust COMPLETED");

    log.info("StaffDAOImpl about to update staff: " + updateStaffTO);
    BeanUtils.copyProperties(staff, updateStaffTO);
    log.info("StaffDAOImpl updated staff: " + updateStaffTO);
    return updateStaffTO;
  }

  // Delete

  public void delete(StaffTO staff) {
    StaffTO deleteStaffTO = (StaffTO) getCurrentSession().get(StaffTO.class, staff.getId());
    String subjectId = deleteStaffTO.getId();
    SubjectLoginBaseTO subjectLogin = this.aaCRUDService.getSubjectLoginTOInstance();
    subjectLogin.setSubjectId(subjectId);
    aaCRUDService.deleteSubjectLogin(subjectLogin);
    SubjectBaseTO subject = aaCRUDService.getSubjectTOInstance();
    subject.setSubjectId(subjectId);
    aaCRUDService.deleteSubject(subject, true);

    getCurrentSession().delete(deleteStaffTO);
    log.info("StaffDAOImpl delete: " + staff);
  }
}
