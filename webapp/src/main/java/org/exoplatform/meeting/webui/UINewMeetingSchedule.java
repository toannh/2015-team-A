/*
 * Copyright (C) 2015 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.meeting.webui;

import org.exoplatform.codefest.entity.Meeting;
import org.exoplatform.codefest.entity.TimeOption;
import org.exoplatform.codefest.service.MeetingService;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.web.application.AbstractApplicationMessage;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.web.application.RequestContext;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.ComponentConfigs;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIApplication;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.core.model.SelectItemOption;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormDateTimeInput;
import org.exoplatform.webui.form.UIFormStringInput;
import org.exoplatform.webui.form.UIFormTextAreaInput;
import org.exoplatform.webui.form.ext.UIFormComboBox;
import org.exoplatform.webui.organization.account.UIUserSelector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:tuyennt@exoplatform.com">Tuyen Nguyen The</a>.
 */
@ComponentConfigs({
        @ComponentConfig(
                lifecycle = UIFormLifecycle.class,
                template = "app:/groovy/meetingschedule/webui/UIFormScheduleMeeting.gtmpl",
                events = {
                        @EventConfig(listeners = UINewMeetingSchedule.BackActionListener.class),
                        @EventConfig(listeners = UINewMeetingSchedule.SaveActionListener.class),
                        @EventConfig(listeners = UINewMeetingSchedule.AddTimeOptionActionListener.class),
                        @EventConfig(listeners = UINewMeetingSchedule.InviteActionListener.class),
                        @EventConfig(listeners = UINewMeetingSchedule.RemoveTimeOptionActionListener.class),
                        @EventConfig(listeners = UINewMeetingSchedule.RemoveParticipantActionListener.class)
                }
        ),
        @ComponentConfig(
                id = "UIPopupWindowUserSelectEventFormForParticipant",
                type = UIPopupWindow.class,
                template =  "system:/groovy/webui/core/UIPopupWindow.gtmpl",
                events = {
                        @EventConfig(listeners = UIPopupWindow.CloseActionListener.class, name = "ClosePopup"),
                        @EventConfig(listeners = UINewMeetingSchedule.AddActionListener.class, name = "Add", phase = Event.Phase.DECODE),
                        @EventConfig(listeners = UINewMeetingSchedule.CloseActionListener.class, phase = Event.Phase.DECODE )
                }
        )
})
public class UINewMeetingSchedule extends UIForm {

  private Set<String> participants = new HashSet<String>();
  private List<TimeOption> timeOptions = new ArrayList<TimeOption>();
  public Meeting editMeeting;

  public void setEditMeeting(Meeting editMeeting){
      this.editMeeting = editMeeting;
  }

  public Meeting getEditMeeting(){
      return editMeeting;
  }

  public UINewMeetingSchedule() throws Exception {
    addUIFormInput(new UIFormStringInput("title", "title", ""));
    addUIFormInput(new UIFormStringInput("location", "location", ""));
    //addUIFormInput(new UICheckBoxInput("isMultiChoice", "isMultiChoice", false));
    addUIFormInput(new UIFormTextAreaInput("description", "description", ""));

    List<SelectItemOption<String>> options = getTimeOptions("HH:mm", "HH:mm", 30);
    addUIFormInput(new UIFormDateTimeInput("from", null, new Date(), false));
    addUIFormInput(new UIFormDateTimeInput("to", null, new Date(), false));
    addUIFormInput(new UIFormComboBox("from_time", null, options));
    addUIFormInput(new UIFormComboBox("to_time", null, options));

    setActions(new String[]{"Back", "Save"});
  }

  public List<TimeOption> getTimeOptions() {
    if(editMeeting!=null) return editMeeting.getTimeOptions();
    return this.timeOptions;
  }

  public Set<String> getParticipants() {
    if(editMeeting!=null) editMeeting.getParticipant().toArray();
    return this.participants;
  }

  public void setParticipants(Set<String> participants ){
    this.participants = participants;
  }

  private List<SelectItemOption<String>> getTimeOptions(String labelFormat, String valueFormat, long timeInteval) {
    WebuiRequestContext context = RequestContext.getCurrentInstance() ;
    Locale locale = context.getParentAppRequestContext().getLocale() ;
    return getTimesSelectBoxOptions(labelFormat, valueFormat, timeInteval, locale);
  }

  public List<SelectItemOption<String>> getTimesSelectBoxOptions(String labelFormat, String valueFormat, long timeInteval, Locale locale) {
    List<SelectItemOption<String>> options = new ArrayList<SelectItemOption<String>>() ;
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("")); // get a GMT calendar
    cal.set(Calendar.HOUR_OF_DAY, 0) ;
    cal.set(Calendar.MINUTE, 0) ;
    cal.set(Calendar.MILLISECOND, 0) ;

    DateFormat dfLabel = new SimpleDateFormat(labelFormat, locale) ;
    dfLabel.setCalendar(cal) ;
    DateFormat dfValue = new SimpleDateFormat(valueFormat, locale) ;
    dfValue.setCalendar(cal) ;

    int day = cal.get(Calendar.DAY_OF_MONTH);
    while (day == cal.get(Calendar.DAY_OF_MONTH)) {
      options.add(new SelectItemOption<String>(dfLabel.format(cal.getTime()), dfValue.format(cal.getTime()))) ;
      cal.add(java.util.Calendar.MINUTE, (int)timeInteval) ;
    }
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.MILLISECOND, 59) ;
    options.add(new SelectItemOption<String>(dfLabel.format(cal.getTime()), dfValue.format(cal.getTime()))) ;
    return options ;
  }

  public static class AddTimeOptionActionListener extends EventListener<UINewMeetingSchedule> {
    @Override
    public void execute(Event<UINewMeetingSchedule> event) throws Exception {
      UINewMeetingSchedule form = event.getSource();
      Calendar from = ((UIFormDateTimeInput)form.getChildById("from")).getCalendar();
      Calendar to = ((UIFormDateTimeInput)form.getChildById("from")).getCalendar();
      String fromTime = ((UIFormComboBox)form.getChildById("from_time")).getValue();
      String toTime = ((UIFormComboBox)form.getChildById("to_time")).getValue();

      String[] fromTimes = fromTime.split(":");
      String[] toTimes = toTime.split(":");

      from.set(Calendar.HOUR_OF_DAY, Integer.parseInt(fromTimes[0]));
      from.set(Calendar.MINUTE, Integer.parseInt(fromTimes[1]));
      to.set(Calendar.HOUR_OF_DAY, Integer.parseInt(toTimes[0]));
      to.set(Calendar.MINUTE, Integer.parseInt(toTimes[1]));

      TimeOption timeOption = new TimeOption();
      timeOption.setFromDate(from.getTimeInMillis());
      timeOption.setToDate(to.getTimeInMillis());
      timeOption.setSelected(false);

      form.timeOptions.add(timeOption);
    }
  }

  public static class InviteActionListener extends EventListener<UINewMeetingSchedule> {
    @Override
    public void execute(Event<UINewMeetingSchedule> event) throws Exception {
//      System.out.println("InviteActionListener");

      UINewMeetingSchedule form = event.getSource();
      UIPopupWindow uiPopupWindow = form.getChild(UIPopupWindow.class) ;
      if(uiPopupWindow == null) uiPopupWindow = form.addChild(UIPopupWindow.class, "UIPopupWindowUserSelectEventFormForParticipant", "UIPopupWindowUserSelectEventFormForParticipant") ;
      UIUserSelector uiUserSelector = form.createUIComponent(UIUserSelector.class, null, null) ;
      uiUserSelector.setShowSearch(true);
      uiUserSelector.setShowSearchUser(true) ;
      uiUserSelector.setShowSearchGroup(true);
      uiPopupWindow.setUIComponent(uiUserSelector);
      uiPopupWindow.setShow(true);
      uiPopupWindow.setRendered(true);
      uiPopupWindow.setWindowSize(740, 400) ;
    }
  }

  public static class RemoveTimeOptionActionListener extends EventListener<UINewMeetingSchedule> {
    @Override
    public void execute(Event<UINewMeetingSchedule> event) throws Exception {
      String optionId = event.getRequestContext().getRequestParameter(OBJECTID);
      if (optionId == null || optionId.isEmpty()) {
        return;
      }

      UINewMeetingSchedule ui = event.getSource();
      int index = -1;
      for(int i = 0; i < ui.timeOptions.size(); i++) {
        TimeOption opt = ui.timeOptions.get(i);
        if (optionId.equals(opt.getId())) {
          index = i;
          break;
        }
      }
      if (index != -1) {
        ui.timeOptions.remove(index);
      }
    }
  }

  public static class RemoveParticipantActionListener extends EventListener<UINewMeetingSchedule> {
    @Override
    public void execute(Event<UINewMeetingSchedule> event) throws Exception {
      String participant = event.getRequestContext().getRequestParameter(OBJECTID);
      if (participant == null || participant.isEmpty()) {
        return;
      }
      UINewMeetingSchedule ui = event.getSource();
      ui.participants.remove(participant);
    }
  }

  public static class BackActionListener extends EventListener<UINewMeetingSchedule> {
    @Override
    public void execute(Event<UINewMeetingSchedule> event) throws Exception {
//      System.out.println("Test event listener");
      UINewMeetingSchedule ui = event.getSource();
      UIMeetingSchedulePortlet portlet = ui.getAncestorOfType(UIMeetingSchedulePortlet.class);
      //portlet.getChild(UIMeetingDetail.class).setRendered(false);
      portlet.getChild(UIListMeetingSchedule.class).setRendered(true);
      portlet.getChild(UINewMeetingSchedule.class).setRendered(false);

      event.getRequestContext().addUIComponentToUpdateByAjax(portlet);
    }
  }

  public static class SaveActionListener extends EventListener<UINewMeetingSchedule> {
    @Override
    public void execute(Event<UINewMeetingSchedule> event) throws Exception {
      UINewMeetingSchedule form = event.getSource();

      List<TimeOption> timeOptions = new ArrayList<TimeOption>(form.getTimeOptions());
      List<String> participants = new ArrayList<String>(new ArrayList<String>(form.participants));
      String title = form.getUIStringInput("title").getValue();
      String location = form.getUIStringInput("location").getValue();
      String description = form.getUIFormTextAreaInput("description").getValue();

      UIApplication ui = event.getRequestContext().getUIApplication();
      if (title == null || title.isEmpty()) {
        ui.addMessage(new ApplicationMessage("Title is required", new Object[0], AbstractApplicationMessage.ERROR));
        return;
      }

      Identity identity = ConversationState.getCurrent().getIdentity();
      Meeting meeting = new Meeting();
      if(form.getEditMeeting()!=null) meeting.setId(form.getEditMeeting().getId());
      meeting.setTitle(title);
      meeting.setDescription(description);
      meeting.setLocation(location);
      meeting.setTimeOptions(timeOptions);
      meeting.setParticipant(participants);
      meeting.setOwner(identity.getUserId());
      meeting.setStatus(0);

      MeetingService meetingService = form.getApplicationComponent(MeetingService.class);
      meeting = meetingService.save(meeting);

      ui.addMessage(new ApplicationMessage("Save meeting schedule successfully", new Object[0], AbstractApplicationMessage.INFO));
      clearForm(form);

      UIMeetingSchedulePortlet portlet = form.getAncestorOfType(UIMeetingSchedulePortlet.class);
      portlet.getChild(UINewMeetingSchedule.class).setRendered(false);
      portlet.getChild(UIListMeetingSchedule.class).setRendered(false);
      UIMeetingDetail detail = portlet.getChild(UIMeetingDetail.class).setRendered(true);
      detail.setMeeting(meeting);
    }
  }

  public static class AddActionListener extends EventListener<UIUserSelector> {
    @Override
    public void execute(Event<UIUserSelector> event) throws Exception {
      UIUserSelector userSelector = event.getSource();
      UINewMeetingSchedule form = userSelector.getAncestorOfType(UINewMeetingSchedule.class);
      String userNamesSelected = userSelector.getSelectedUsers();
      if (userNamesSelected != null && !userNamesSelected.isEmpty()) {
        String[] users = userNamesSelected.split(",");
        for (String u : users) {
          form.participants.add(u);
        }
      }

//      System.out.println("Selected: " + userNamesSelected);

      UIPopupWindow uiPopup = form.getChild(UIPopupWindow.class);
      uiPopup.setUIComponent(null);
      uiPopup.setShow(false);
      event.getRequestContext().addUIComponentToUpdateByAjax(form);
    }
  }

  public static class CloseActionListener extends EventListener<UIUserSelector> {
    @Override
    public void execute(Event<UIUserSelector> event) throws Exception {
      UIUserSelector uiForm = event.getSource();
      UINewMeetingSchedule form = uiForm.getAncestorOfType(UINewMeetingSchedule.class);
      UIPopupWindow uiPopup = form.getChild(UIPopupWindow.class);
      uiPopup.setUIComponent(null);
      uiPopup.setShow(false);
      event.getRequestContext().addUIComponentToUpdateByAjax(form);
    }
  }

  public void init(){
    UIFormStringInput titleComponent = this.getChildById("title");
    titleComponent.setValue(editMeeting != null ? editMeeting.getTitle() : "");

    UIFormStringInput locationComponent = this.getChildById("location");
    locationComponent.setValue(editMeeting!=null?editMeeting.getLocation():"");

    UIFormTextAreaInput descriptionComponent = this.getChildById("description");
    descriptionComponent.setValue(editMeeting != null ? editMeeting.getDescription() : "");

    List<SelectItemOption<String>> options = getTimeOptions("HH:mm", "HH:mm", 30);
    UIFormComboBox from_time = this.getChildById("from_time");
    from_time.setOptions(options);

    UIFormComboBox to_time = this.getChildById("to_time");
    to_time.setOptions(options);
    if(editMeeting!=null) setParticipants(new HashSet<String>(editMeeting.getParticipant()));
  }

  public static void clearForm(UINewMeetingSchedule form){
    form.timeOptions.clear();
    form.participants.clear();
    form.getUIStringInput("title").setValue("");
    form.getUIStringInput("location").setValue("");
    form.getUIFormTextAreaInput("description").setValue("");
  }
}
