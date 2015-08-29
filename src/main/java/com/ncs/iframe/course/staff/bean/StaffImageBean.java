package com.ncs.iframe.course.staff.bean;

import java.io.ByteArrayInputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ncs.iframe.course.staff.service.StaffService;
import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.logging.Logger;

public class StaffImageBean {

  private transient Logger log = Logger.getLogger(getClass());
  private StaffService staffSvc;
  private StreamedContent displayPicture;

  // Getters

  public StaffService getStaffService() {
    return staffSvc;
  }

  public StreamedContent getDisplayPicture() {
    FacesContext context = FacesContext.getCurrentInstance();

    if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
      log.info("StaffImageBean loading page");
      return new DefaultStreamedContent();
    } else {
      try {
        String staffId = context.getExternalContext().getRequestParameterMap().get("staffId");
        StaffTO staff = staffSvc.findById(staffId);
        byte[] buffer = staff.getPhoto();
        String mimeType = "image/jpg";
        log.info("StaffImage before generating image for staff: " + staff + "with photo: " + buffer);
        this.displayPicture = new DefaultStreamedContent(new ByteArrayInputStream(buffer));
        return this.displayPicture;
        // return buffer == null ? new DefaultStreamedContent() : new DefaultStreamedContent(new ByteArrayInputStream(buffer), mimeType);
      } catch (Exception e) {
        log.info("StaffImageBean getDisplayPicture error: " + e.getStackTrace());
      }
      log.info("StaffImageBean getDisplayPicture should not reach here!");
      return new DefaultStreamedContent();
    }
  }

  // Setters

  public void setStaffService(StaffService staffSvc) {
    this.staffSvc = staffSvc;
  }

}
