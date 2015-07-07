package org.exoplatform.codefest.service;

import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.app.ThreadLocalSessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.IdentityConstants;

import javax.jcr.Session;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * #comments here
 */
public class DumpThreadLocalSessionProviderService extends ThreadLocalSessionProviderService {

  private DumpSessionProvider userSessionProvider;

  public DumpThreadLocalSessionProviderService(){
    super();
  }

  public SessionProvider getSessionProvider(Object key) {
    return userSessionProvider;
  }

  /**
   * Apply an user session
   * @param userSession
   */
  public void applyUserSession(Session userSession) {
    // create user session provider
    if (userSession != null) {
      userSessionProvider = new DumpSessionProvider(new ConversationState(new Identity(userSession.getUserID())));
    } else {
      userSessionProvider = new DumpSessionProvider(new ConversationState(new Identity(IdentityConstants.ANONIM)));
    }
    userSessionProvider.setSession(userSession);
  }

  public static class DumpSessionProvider extends SessionProvider {

    private Session session = null;

    public DumpSessionProvider(ConversationState userState) {
      super(userState);
    }

    public void setSession(Session value) {
      session = value;
    }

    public Session getSession(String workspace, ManageableRepository repo) {
      return session;
    }

  }
}