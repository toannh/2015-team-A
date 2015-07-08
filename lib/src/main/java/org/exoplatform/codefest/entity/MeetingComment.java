package org.exoplatform.codefest.entity;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/8/15
 * Declare properties of Meeting's comments
 */
public class MeetingComment {
  private String username;
  private String message;
  private String email;
  private String site;
  private String language;
  private long commentDate;

  public MeetingComment(String username, String message, String email, String site, String language) {
    this.username = username;
    this.message = message;
    this.email = email;
    this.site = site;
    this.language = language;
  }

  public MeetingComment() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public long getCommentDate() {
    return commentDate;
  }

  public void setCommentDate(long commentDate) {
    this.commentDate = commentDate;
  }
}
