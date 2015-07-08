package org.exoplatform.codefest.entity;

import com.google.gson.Gson;

import java.util.List;
import java.util.UUID;

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
  private boolean isMultiChoice;

  private String owner;
  private String type;

  private List<String> participant;

  private List<UserVoted> userVotes;

  private int status; // Event status, values: 1: booked, 0: voting, -1: closed

  private String documentPath;
  private long dateCreated;
  private long dateModified;

  public Meeting(String jcrPath, String title, String location, String description,
                 List<TimeOption> timeOptions, long meetingValidation, boolean isMultiChoice, String owner,
                 String type, List<String> participant, List<UserVoted> userVotes, int status,
                 String documentPath, long dateCreated, long dateModified) {
    this(UUID.randomUUID().toString());
    this.jcrPath = jcrPath;
    this.title = title;
    this.location = location;
    this.description = description;
    this.timeOptions = timeOptions;
    this.meetingValidation = meetingValidation;
    this.isMultiChoice = isMultiChoice;
    this.owner = owner;
    this.type = type;
    this.participant = participant;
    this.userVotes = userVotes;
    this.status = status;
    this.documentPath = documentPath;
    this.dateCreated = dateCreated;
    this.dateModified = dateModified;
  }

  public Meeting(){
    this(UUID.randomUUID().toString());
  }

  public Meeting(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
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

  public void setId(String id) {
    this.id = id;
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

  public long getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(long dateCreated) {
    this.dateCreated = dateCreated;
  }

  public long getDateModified() {
    return dateModified;
  }

  public void setDateModified(long dateModified) {
    this.dateModified = dateModified;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isMultiChoice() {
    return isMultiChoice;
  }

  public void setMultiChoice(boolean isMultiChoice) {
    this.isMultiChoice = isMultiChoice;
  }

  @Override
  public String toString(){
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public boolean isVoted(String username) {
    if (this.userVotes != null && !this.userVotes.isEmpty()) {
      for (UserVoted userVoted : this.userVotes) {
        if (userVoted.getUsername().equals(username)) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isVotedOption(String username, String optionId) {
    if (this.userVotes != null && !this.userVotes.isEmpty()) {
      for (UserVoted userVoted : this.userVotes) {
        if (userVoted.getUsername().equals(username) && userVoted.getOptionId().equals(optionId)) {
          return true;
        }
      }
    }
    return false;
  }
}
