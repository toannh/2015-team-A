package org.exoplatform.com.meeting.service;

import org.exoplatform.com.meeting.service.entity.Meeting;
import org.exoplatform.com.meeting.service.entity.Page;
import org.exoplatform.com.meeting.service.entity.TimeOption;

import java.util.List;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * #comments here
 */
public interface MeetingService {

  public final String EXO_PROP_MEETING_TITLE        = "exo:meetingTitle";
  public final String EXO_PROP_MEETING_VALIDATION   = "exo:meetingValidation";
  public final String EXO_PROP_MEETING_DESCRIPTION  = "exo:meetingDescription";

  public final String EXO_MIX_TIME_OPTION           = "mix:timeOption";
  public final String EXO_MIX_PROP_OPTION_ID        = "exo:timeOptionId";
  public final String EXO_MIX_PROP_OPTION_FROM_DATE = "exo:timeOptionFromDate";
  public final String EXO_MIX_PROP_OPTION_TO_DATE   = "exo:timeOptionToDate";

  /**
   * Get all current of meeting
   * @param username: current PLF user
   * @return a list of all meeting relate to current username
   */
  public List<Meeting> getMeetings(String username, Page page);

  /**
   * Get all meeting by status
   * @param username
   * @param status
   * @param page
   * @return
   */
  public List<Meeting> getMeetings(String username, int status, Page page);

  /**
   * Get details a meeting
   * @param id
   * @return
   */
  public Meeting getMeeting(String id);

  /**
   * Get total meeting relate to current user
   * @param username
   * @return
   */
  public int getMeetingTotal(String username);


  /**
   * Save a new schedule meeting
   * @param meeting
   * @return
   */
  public Meeting save(Meeting meeting);

  /**
   *
   * @param meeting
   * @return
   */
  public boolean delete(Meeting meeting);

  /**
   *
   * @param timeOptionId
   * @return
   */
  public void updateVote(Meeting meeting, String username, String[] timeOptionId);

  public void finalMeeting(Meeting meeting, String[] timeOptionId);

  public Meeting addTimeOption(Meeting meeting, TimeOption option);

  public Meeting removeTimeOption(Meeting meeting, String timeOptionId);

  public Meeting addParticipant(Meeting meeting, String username);

  public Meeting removeParticipant(Meeting meeting, String username);
}
