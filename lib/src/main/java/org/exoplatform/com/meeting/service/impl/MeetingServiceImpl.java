package org.exoplatform.com.meeting.service.impl;

import org.exoplatform.com.meeting.service.MeetingService;
import org.exoplatform.com.meeting.service.entity.Meeting;
import org.exoplatform.com.meeting.service.entity.Page;
import org.exoplatform.com.meeting.service.entity.TimeOption;
import org.exoplatform.portal.config.UserACL;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.List;


/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * Code-fest Team-A 2015
 * Implement of Meeting Service
 */
public class MeetingServiceImpl implements MeetingService{

  private Log log = ExoLogger.getExoLogger(MeetingService.class);
  private String repo = "repository";
  private String ws = "collaboration";

  private RepositoryService repoService;
  private SessionProviderService sessionProviderService;
  private UserACL userACL;

  public MeetingServiceImpl(RepositoryService repoService,
                            SessionProviderService sessionProviderService,
                            UserACL userACL){
    this.repoService = repoService;
    this.sessionProviderService = sessionProviderService;
    this.userACL = userACL;
  }



  @Override
  public List<Meeting> getMeetings(String username, Page page) {
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
    try {
      Session session = getSession();
      Node rootNode = session.getRootNode();
      System.out.println("Root Node: " +rootNode);
    }catch (Exception ex){
      if(log.isErrorEnabled()) log.error(ex.getMessage());
    }
    return null;
  }

  @Override
  public boolean delete(Meeting meeting) {
    return false;
  }

  @Override
  public List<Meeting> getMeetings(String username, int status, Page page) {
    return null;
  }

  @Override
  public void updateVote(Meeting meeting, String username, String[] timeOptionId) {

  }

  @Override
  public void finalMeeting(Meeting meeting, String[] timeOptionId) {

  }

  /**
   * Get system session, only for init data.
   * Please NOT use for navigate JCR data
   *
   * @return
   * @throws Exception
   */
  private Session getSession() throws Exception {
    ManageableRepository repository = repoService.getRepository(this.repo);
    SessionProvider sessionProvider = sessionProviderService.getSystemSessionProvider(null);
    Session session = sessionProvider.getSession(this.ws, repository);
    return session;
  }


}
