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
  private String id;

  public TimeOption(){
    id = UUID.randomUUID().toString();
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

}
