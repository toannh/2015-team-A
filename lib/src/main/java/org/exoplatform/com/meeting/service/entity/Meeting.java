package org.exoplatform.com.meeting.service.entity;

import java.util.List;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * Declare a Meeting entity
 */
public class Meeting {

  private String id; // node uuid
  private String jcrPath;
  private String title;
  private String location;
  private String description;
  private List<TimeOption> timeOptions;
  private long meetingValidation; // Expiration vote time

  private String owner;
  private List<String> participant;

  private List<UserVoted> userVotes;

  private int status; // Event status, values: 1: booked, 0: voting, -1: closed

  private String documentPath;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getJcrPath() {
    return jcrPath;
  }

  public void setJcrPath(String jcrPath) {
    this.jcrPath = jcrPath;
  }

  public String getDocumentPath() {
    return documentPath;
  }

  public void setDocumentPath(String documentPath) {
    this.documentPath = documentPath;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<TimeOption> getTimeOptions() {
    return timeOptions;
  }

  public void setTimeOptions(List<TimeOption> timeOptions) {
    this.timeOptions = timeOptions;
  }

  public long getMeetingValidation() {
    return meetingValidation;
  }

  public void setMeetingValidation(long meetingValidation) {
    this.meetingValidation = meetingValidation;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public List<String> getParticipant() {
    return participant;
  }

  public void setParticipant(List<String> participant) {
    this.participant = participant;
  }

  public List<UserVoted> getUserVotes() {
    return userVotes;
  }

  public void setUserVotes(List<UserVoted> userVotes) {
    this.userVotes = userVotes;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
