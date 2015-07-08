package org.exoplatform.codefest.utils;

import java.text.SimpleDateFormat;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/8/15
 * Utils
 */
public class Utils {
  private static final SimpleDateFormat formatDateTime = new SimpleDateFormat();
  static {
    formatDateTime.applyPattern("EEE dd/MM HH:mm a");
  }

  /**
   * Convert friendly date time format
   * @param time
   * @return String datetime format
   */
  public static String toDate(long time){
    return formatDateTime.format(time);
  }

}
