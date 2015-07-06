package org.exoplatform.com.meeting.service.entity;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * #comments here
 */
public class TimeOption {
  private String desc;
  private long fromDate;
  private long toDate;
  private String vote; //total vote of option
  private String userVote; // user vote option

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public long getFromDate() {
    return fromDate;
  }

  public void setFromDate(long fromDate) {
    this.fromDate = fromDate;
  }

  public long getToDate() {
    return toDate;
  }

  public void setToDate(long toDate) {
    this.toDate = toDate;
  }

  public String getVote() {
    return vote;
  }

  public void setVote(String vote) {
    this.vote = vote;
  }

  public String getUserVote() {
    return userVote;
  }

  public void setUserVote(String userVote) {
    this.userVote = userVote;
  }
}
