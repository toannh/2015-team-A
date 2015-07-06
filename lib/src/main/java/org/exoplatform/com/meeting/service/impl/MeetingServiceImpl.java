package org.exoplatform.com.meeting.service.impl;

import org.exoplatform.com.meeting.service.MeetingService;
import org.exoplatform.com.meeting.service.entity.Meeting;
import org.exoplatform.com.meeting.service.entity.Page;
import org.exoplatform.com.meeting.service.entity.TimeOption;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import java.util.List;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * #comments here
 */
public class MeetingServiceImpl implements MeetingService{
  private Log log = ExoLogger.getExoLogger(MeetingService.class);


  @Override
  public List<Meeting> getMeeting(String username, Page page) {
    return null;
  }

  @Override
  public Meeting getMeeting(String id) {
    return null;
  }

  @Override
  public int getMeetingTotal(String username) {
    return 0;
  }

  @Override
  public Meeting save(Meeting meeting) {
    return null;
  }

  @Override
  public boolean delete(Meeting meeting) {
    return false;
  }

  @Override
  public TimeOption updateVote(TimeOption timeOption) {
    return null;
  }
}
