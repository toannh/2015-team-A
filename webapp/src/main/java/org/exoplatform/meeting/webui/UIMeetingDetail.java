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
import org.exoplatform.codefest.service.MeetingService;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIContainer;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:tuyennt@exoplatform.com">Tuyen Nguyen The</a>.
 */
@ComponentConfig(template = "app:/groovy/meetingschedule/webui/UIMeetingDetail.gtmpl", events = {
        @EventConfig(listeners = UIMeetingDetail.BackActionListener.class),
        @EventConfig(listeners = UIMeetingDetail.ScheduleNewMeetingActionListener.class),
        @EventConfig(listeners = UIMeetingDetail.VoteActionListener.class)
})
public class UIMeetingDetail extends UIContainer {
  private Meeting meeting;
  public UIMeetingDetail() {
  }

  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }

  public Meeting getMeeting() {
    return this.meeting;
  }

  public static class VoteActionListener extends EventListener<UIMeetingDetail> {
    @Override
    public void execute(Event<UIMeetingDetail> event) throws Exception {
      String optionId = event.getRequestContext().getRequestParameter(OBJECTID);
      UIMeetingDetail detail = event.getSource();
      Identity identity = ConversationState.getCurrent().getIdentity();
      boolean isVoted = detail.meeting.isVotedOption(identity.getUserId(), optionId);
      MeetingService service = detail.getApplicationComponent(MeetingService.class);
      Map<String, String> voted = new HashMap<String, String>();
      voted.put(optionId, isVoted ? "0" : "1");
      Meeting meeting1 = service.updateVote(detail.meeting, identity.getUserId(), voted);
      detail.setMeeting(meeting1);
    }
  }

  public static class BackActionListener extends EventListener<UIMeetingDetail> {
    @Override
    public void execute(Event<UIMeetingDetail> event) throws Exception {
      UIMeetingDetail detail = event.getSource();
      UIMeetingSchedulePortlet portlet = detail.getAncestorOfType(UIMeetingSchedulePortlet.class);
      detail.setRendered(false);
      portlet.getChild(UIListMeetingSchedule.class).setRendered(true);
      event.getRequestContext().addUIComponentToUpdateByAjax(portlet);
    }
  }

  public static class ScheduleNewMeetingActionListener extends EventListener<UIMeetingDetail> {
    @Override
    public void execute(Event<UIMeetingDetail> event) throws Exception {
      UIMeetingDetail ui = event.getSource();
      UIMeetingSchedulePortlet portlet = ui.getAncestorOfType(UIMeetingSchedulePortlet.class);
      ui.setRendered(false);
      portlet.getChild(UIListMeetingSchedule.class).setRendered(false);
      portlet.getChild(UINewMeetingSchedule.class).setRendered(true);

      event.getRequestContext().addUIComponentToUpdateByAjax(portlet);
    }
  }
}
