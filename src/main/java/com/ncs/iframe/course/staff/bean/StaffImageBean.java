package com.ncs.iframe.course.staff.bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ncs.iframe.course.staff.service.StaffService;
import com.ncs.iframe.course.staff.to.StaffTO;
import com.ncs.iframe4.commons.logging.Logger;

public class StaffImageBean {

  private transient Logger log = Logger.getLogger(getClass());
  private StaffService staffSvc;

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
        log.info("StaffImage before generating image for staff: " + staff + " with photo: " + buffer);
        if (buffer != null) {
          return new DefaultStreamedContent(new ByteArrayInputStream(buffer), mimeType);
        }
        BufferedImage placeholderImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = placeholderImage.createGraphics();
        graphics.drawString("There is no image found", 28, 10);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(placeholderImage, "jpg", os);
        return new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), mimeType);
      } catch (Exception e) {
        log.info("StaffImageBean getDisplayPicture error: " + e.getCause());
        return new DefaultStreamedContent();
      }
    }
  }

  // Setters

  public void setStaffService(StaffService staffSvc) {
    this.staffSvc = staffSvc;
  }

}
