package org.exoplatform.com.meeting.service.entity;

import java.util.UUID;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * #comments here
 */
public class TimeOption {
  private long fromDate;
  private long toDate;
  private boolean isSelected;
  private final String id;

  public TimeOption(){
    this(UUID.randomUUID().toString());
  }

  public TimeOption(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
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

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }
}
