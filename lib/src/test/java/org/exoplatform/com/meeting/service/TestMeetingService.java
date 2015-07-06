package org.exoplatform.com.meeting.service;

import junit.framework.TestCase;
import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.container.component.RequestLifeCycle;
import org.exoplatform.services.cms.comments.CommentsService;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.CredentialsImpl;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.IdentityConstants;
import org.exoplatform.services.security.Identity;

import javax.jcr.Session;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * #comments here
 */
public class TestMeetingService extends TestCase {

  protected final String REPO_NAME = "repository";
  protected final String COLLABORATION_WS = "collaboration";


  private static StandaloneContainer container;
  private static CommentsService commentsService;
  private RepositoryService      repositoryService;
  protected ManageableRepository repository;
  protected SessionProvider sessionProvider;
  protected CredentialsImpl credentials;
  protected Session session;
  protected SessionProviderService sessionProviderService_;

  static {
    initContainer();
  }

  private static void initContainer() {
    try {
      String containerConf = Thread.currentThread()
              .getContextClassLoader()
              .getResource("conf/standalone/configuration.xml")
              .toString();
      StandaloneContainer.addConfigurationURL(containerConf);
      String loginConf = Thread.currentThread().getContextClassLoader().getResource("conf/standalone/login.conf").toString();
      System.setProperty("java.security.auth.login.config", loginConf);
      container = StandaloneContainer.getInstance();

    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize standalone container: " + e.getMessage(), e);
    }
  }

  /**
   * Set current container
   */
  private void begin() {
    RequestLifeCycle.begin(container);
  }

  /**
   * Clear current container
   */
  protected void tearDown() throws Exception {
    RequestLifeCycle.end();
  }

  @Override
  protected void setUp() throws Exception {
    begin();
    Identity systemIdentity = new Identity(IdentityConstants.SYSTEM);
    ConversationState.setCurrent(new ConversationState(systemIdentity));
    repositoryService = (RepositoryService) container.getComponentInstanceOfType(RepositoryService.class);
    sessionProviderService_ = (SessionProviderService) container.getComponentInstanceOfType(SessionProviderService.class);

    applySystemSession();
    reset();
    init();
    commentsService = (CommentsService) container.getComponentInstanceOfType(CommentsService.class);
  }

  private void reset() throws Exception { }

  public void applySystemSession() throws Exception{
    System.setProperty("gatein.tenant.repository.name", REPO_NAME);
    container = StandaloneContainer.getInstance();

    repositoryService = (RepositoryService) container.getComponentInstanceOfType(RepositoryService.class);
    sessionProviderService_ = (SessionProviderService) container.getComponentInstanceOfType(SessionProviderService.class);
    repositoryService.setCurrentRepositoryName(REPO_NAME);
    repository = repositoryService.getCurrentRepository();

    closeOldSession();
    sessionProvider = sessionProviderService_.getSystemSessionProvider(null);
    session = sessionProvider.getSession(COLLABORATION_WS, repository);
    sessionProvider.setCurrentRepository(repository);
    sessionProvider.setCurrentWorkspace(COLLABORATION_WS);
  }

  public void testA() throws Exception{
    System.out.println("Test A");
    assertEquals("Error", 1, 1);
  }



  /**
   * Close current session
   */
  private void closeOldSession() {
    if (session != null && session.isLive()) {
      session.logout();
    }
  }

  private void init() throws Exception {}

}
