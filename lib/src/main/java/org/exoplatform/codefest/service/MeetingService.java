package org.exoplatform.codefest.service;

import org.exoplatform.codefest.entity.Meeting;
import org.exoplatform.codefest.entity.MeetingComment;
import org.exoplatform.codefest.entity.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * Meeting interface, declare all methods of Meeting service
 */
public interface MeetingService {

  public final String EXO_MEETING_DRIVE             = "Meeting";
  public final String EXO_COMMENT_NODE              = "exo:comments";
  public final String EXO_COMMENT_PROP_COMMENTOR    = "exo:commentor";
  public final String EXO_COMMENT_PROP_FULLNAME     = "exo:commentorFullName";
  public final String EXO_COMMENT_PROP_EMAIL        = "exo:commentorEmail";
  public final String EXO_COMMENT_PROP_SITE         = "exo:commentorSite";
  public final String EXO_COMMENT_PROP_DATE         = "exo:commentDate";
  public final String EXO_COMMENT_PROP_CONTENT      = "exo:commentContent";

  public final String EXO_MEETING                   = "exo:meeting";
  public final String EXO_PROP_MEETING_TITLE        = "exo:meetingTitle";
  public final String EXO_PROP_MEETING_VALIDATION   = "exo:meetingValidation";
  public final String EXO_PROP_MEETING_DATE_MODIFIED= "exo:meetingDateModified";
  public final String EXO_PROP_MEETING_DATE_CREATED = "exo:meetingDateCreated";
  public final String EXO_PROP_MEETING_DESCRIPTION  = "exo:meetingDescription";
  public final String EXO_PROP_MEETING_LOCATION     = "exo:meetingLocation";
  public final String EXO_PROP_MEETING_TIME_OPTION  = "exo:meetingTimeOptions";
  public final String EXO_PROP_MEETING_OWNER        = "exo:meetingOwner";
  public final String EXO_PROP_MEETING_TYPE         = "exo:meetingType";
  public final String EXO_PROP_MEETING_DOC_PATH     = "exo:meetingDocumentPath";
  public final String EXO_PROP_MEETING_PARTICIPANT  = "exo:meetingParticipant";
  public final String EXO_PROP_MEETING_USER_VOTED   = "exo:meetingUserVoted";
  public final String EXO_PROP_MEETING_STATUS       = "exo:meetingStatus";

  /**
   * Get all meeting relate to username
   * @param username: current PLF user
   * @return a list of all meeting relate to current username
   */
  public List<Meeting> getMeetings(String username, Page page);

  /**
   * Get all meeting by username & status
   * @param username
   * @param status values: 0: open for voting, 1: closed
   * @param page
   * @return a list of meetings relate to current username at status
   */
  public List<Meeting> getMeetings(String username, int status, Page page);
  
  /**
   * Get all meetings organized by a user
   * @param username
   * @param status values:  0: open for voting, 1: closed
   * @param page
   * @return a list of meetings owned by username at status
   */
  public List<Meeting> getMeetingsByOwner(String username, int status, Page page);

  /**
   * Get details a meeting
   * @param id
   * @return
   */
  public Meeting getMeeting(String id) throws Exception;

  /**
   * Get total meeting relate to current user
   * @param username
   * @return
   */
  public long getMeetingTotal(String username);

  /**
   * Save a new schedule meeting
   * @param meeting
   * @return Object saved to JCR, null: error while saving
   */
  public Meeting save(Meeting meeting) throws Exception;

  /**
   *
   * @param meeting
   * @return
   */
  public boolean delete(Meeting meeting) throws Exception;

  /**
   *
   * @param userVoteds
   * @return
   */
  public Meeting updateVote(Meeting meeting, String username, Map<String, String> userVoteds) throws Exception;

  public Meeting finalMeeting(Meeting meeting, List<String> timeOptionIds) throws Exception;

  /**
   * Add new option for meeting
   * @param meeting
   * @param username
   * @return Meeting object with new participant list
   */
  public Meeting addParticipant(Meeting meeting, String username) throws Exception;

  /**
   * Remove username from Meeting's participants
   * @param meeting
   * @param username
   * @return Meeting object with new list of paricipants
   * @throws Exception
   */
  public Meeting removeParticipant(Meeting meeting, String username) throws Exception;

  /**
   * Allow comment when Meeting is voting
   * Return last comment of Meeting
   * @param meeting
   * @param meetingComment
   * @throws Exception
   */
  public MeetingComment postComment(Meeting meeting, MeetingComment meetingComment) throws Exception;

}
