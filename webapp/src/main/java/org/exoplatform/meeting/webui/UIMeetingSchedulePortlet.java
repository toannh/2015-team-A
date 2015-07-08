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

import org.exoplatform.commons.serialization.api.annotations.Serialized;
import org.exoplatform.portal.webui.portal.UIPortalComponentActionListener;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;

/**
 * @author <a href="mailto:tuyennt@exoplatform.com">Tuyen Nguyen The</a>.
 */
@ComponentConfig(lifecycle = UIApplicationLifecycle.class, template = "app:/groovy/meetingschedule/webui/UIMeetingSchedulePortlet.gtmpl", events = {
        @EventConfig(listeners = UIPortalComponentActionListener.ViewChildActionListener.class)
})
@Serialized
public class UIMeetingSchedulePortlet extends UIPortletApplication {
  public UIMeetingSchedulePortlet() throws Exception {
    super();
    addChild(UIListMeetingSchedule.class, null, "UIListMeetings");
    addChild(UINewMeetingSchedule.class, null, "UIMeetingForm").setRendered(false);
    addChild(UIMeetingDetail.class, null, "UIMeetingDetail").setRendered(false);
  }
}
