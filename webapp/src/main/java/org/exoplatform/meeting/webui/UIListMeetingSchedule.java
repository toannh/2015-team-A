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

import org.exoplatform.codefest.service.MeetingService;
import org.exoplatform.codefest.entity.Meeting;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIContainer;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;

import java.util.List;

/**
 * @author <a href="mailto:tuyennt@exoplatform.com">Tuyen Nguyen The</a>.
 */
@ComponentConfig(template = "app:/groovy/meetingschedule/webui/UIListMeeting.gtmpl", events = {
        @EventConfig(listeners = UIListMeetingSchedule.ScheduleNewMeetingActionListener.class)
})
public class UIListMeetingSchedule extends UIContainer {
  private final MeetingService meetingService;

  public UIListMeetingSchedule() {
    this.meetingService = getApplicationComponent(MeetingService.class);
  }

  public List<Meeting> getUpcomingMeeting() {
    Identity identity = ConversationState.getCurrent().getIdentity();
    return meetingService.getMeetings(identity.getUserId(), 1, null);
  }

  public List<Meeting> getPendingMeeting() {
    Identity identity = ConversationState.getCurrent().getIdentity();
    return meetingService.getMeetings(identity.getUserId(), 0, null);
  }

  public List<Meeting> getIncomingMeeting() {
    Identity identity = ConversationState.getCurrent().getIdentity();
    return meetingService.getMeetings(identity.getUserId(), 1, null);
  }

  public static class ScheduleNewMeetingActionListener extends EventListener<UIListMeetingSchedule> {
    @Override
    public void execute(Event<UIListMeetingSchedule> event) throws Exception {
      System.out.println("Hello world");
      UIListMeetingSchedule ui = event.getSource();
      UIMeetingSchedulePortlet portlet = ui.getAncestorOfType(UIMeetingSchedulePortlet.class);
      portlet.getChild(UIListMeetingSchedule.class).setRendered(false);
      portlet.getChild(UINewMeetingSchedule.class).setRendered(true);

      event.getRequestContext().addUIComponentToUpdateByAjax(portlet);
    }
  }
}
