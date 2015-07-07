package org.exoplatform.codefest.service;

import junit.framework.TestCase;
import org.exoplatform.codefest.entity.Meeting;
import org.exoplatform.codefest.entity.Page;
import org.exoplatform.codefest.entity.TimeOption;
import org.exoplatform.codefest.entity.UserVoted;
import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.container.component.RequestLifeCycle;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.ext.app.SessionProviderService;
import org.exoplatform.services.jcr.ext.common.SessionProvider;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.services.security.IdentityConstants;

import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by The eXo Platform SEA
 * Author : eXoPlatform
 * toannh@exoplatform.com
 * On 7/6/15
 * Test Meeting Service
 */
public class TestMeetingService extends TestCase {

  protected final String REPO_NAME = "repository";
  protected final String COLLABORATION_WS = "collaboration";


  private static StandaloneContainer container;
  private static MeetingService meetingService;
  private RepositoryService repositoryService;
  protected ManageableRepository repository;
  protected SessionProvider sessionProvider;
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

    meetingService = (MeetingService) container.getComponentInstanceOfType(MeetingService.class);
    applySystemSession();
    reset();
    init();
  }

  private void reset() throws Exception {
  }

  public void applySystemSession() throws Exception {
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

  public void testSave() throws Exception {
    Meeting meeting = new Meeting();
    meeting.setTitle("Code-fest Team A");
    List<TimeOption> timeOptionList = new ArrayList<TimeOption>();
    Calendar cal11 = new GregorianCalendar(2015, 7, 7);
    Calendar cal12 = new GregorianCalendar(2015, 7, 8);

    Calendar cal21 = new GregorianCalendar(2015, 7, 8);
    Calendar cal22 = new GregorianCalendar(2015, 7, 9);

    Calendar cal31 = new GregorianCalendar(2015, 7, 9);
    Calendar cal32 = new GregorianCalendar(2015, 7, 10);


    timeOptionList.add(new TimeOption(true, cal11.getTime().getTime(), cal12.getTime().getTime()));
    timeOptionList.add(new TimeOption(true, cal21.getTime().getTime(), cal22.getTime().getTime()));
    timeOptionList.add(new TimeOption(true, cal31.getTime().getTime(), cal32.getTime().getTime()));

    meeting.setTimeOptions(timeOptionList);
    meeting.setDocumentPath("/rest/jcr/repository/collaboration/documnet.docx");
    meeting.setLocation("Ha Noi");
    meeting.setOwner("toannh");
    meeting.setType("unknown");
    meeting.setDateCreated(Calendar.getInstance().getTime().getTime());
    meeting.setDateModified(Calendar.getInstance().getTime().getTime());
    meeting.setJcrPath("/jcrPath");
    meeting.setMeetingValidation(Calendar.getInstance().getTime().getTime());
    meeting.setStatus(1);
    meeting.setMultiChoice(true);

    Meeting m = meetingService.save(meeting);

    long totalMeetingToannh = meetingService.getMeetingTotal("toannh");
    long totalMeetingGiangnt = meetingService.getMeetingTotal("giangnt");
    long totalMeetingTrangvh = meetingService.getMeetingTotal("trangvh");
    long totalMeetingTuyennt = meetingService.getMeetingTotal("tuyennt");

    assertNotNull("Error while save a new meeting", m);
    assertEquals("Count by toannh", 3, totalMeetingToannh);
    assertEquals("Count by giangnt", 2, totalMeetingGiangnt);
    assertEquals("Count by tuyennt", 2, totalMeetingTuyennt);
    assertEquals("Count by trangvh", 2, totalMeetingTrangvh);
  }

  public void testGetMeetingTotal() throws Exception {
    long totalMeetingToannh = meetingService.getMeetingTotal("toannh");
    long totalMeetingGiangnt = meetingService.getMeetingTotal("giangnt");
    long totalMeetingTrangvh = meetingService.getMeetingTotal("trangvh");
    long totalMeetingTuyennt = meetingService.getMeetingTotal("tuyennt");

    assertEquals("Error when count meeting of toannh", 5, totalMeetingToannh);
    assertEquals("Error when count meeting of giangnt", 4, totalMeetingGiangnt);
    assertEquals("Error when count meeting of trangvh", 4, totalMeetingTrangvh);
    assertEquals("Error when count meeting of tuyennt", 4, totalMeetingTuyennt);
  }

  public void testGetMeetings() throws Exception {
    Page page = new Page(100, 0, "DESC");
    String username = "toannh";
    int status = 1;
    List<Meeting> meetings = meetingService.getMeetings(username, status, page);
    assertEquals("GetMeeting is not correct", meetings.size(), meetingService.getMeetingTotal(username));
  }

  public void testRemoveParticipant() throws Exception {
    Meeting newMeeting = addSimpleMeeting();
    String username = "giangnt";
    List<String> beforeParticipants = newMeeting.getParticipant();
    Meeting meeting = meetingService.removeParticipant(newMeeting, username);
    List<String> afterParicipants = meeting.getParticipant();

    assertTrue(beforeParticipants.contains(username));
    assertTrue(!afterParicipants.contains(username));
  }

  public void testAddParticipant() throws Exception{
    Meeting newMeeting = addSimpleMeeting();
    String username = "teamA";
    List<String> beforeParticipants = newMeeting.getParticipant();
    Meeting meeting = meetingService.addParticipant(newMeeting, username);
    List<String> afterParicipants = meeting.getParticipant();

    assertTrue(!beforeParticipants.contains(username));
    assertTrue(afterParicipants.contains(username));
  }

  /**
   * Close current session
   */
  private void closeOldSession() {
    if (session != null && session.isLive()) {
      session.logout();
    }
  }

  private void init() throws Exception {
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    List<String> participants1 = new ArrayList<String>();
    List<String> participants2 = new ArrayList<String>();
    List<String> participants3 = new ArrayList<String>();
    List<String> participants4 = new ArrayList<String>();
    List<UserVoted> userVotes = new ArrayList<UserVoted>();
    List<TimeOption> timeOptionLst_001 = new ArrayList<TimeOption>();
    List<TimeOption> timeOptionLst_002 = new ArrayList<TimeOption>();
    List<TimeOption> timeOptionLst_003 = new ArrayList<TimeOption>();

    TimeOption timeOption1 = new TimeOption(true, cal1.getTimeInMillis(), cal2.getTimeInMillis());
    TimeOption timeOption2 = new TimeOption(true, cal1.getTimeInMillis(), cal2.getTimeInMillis());

    timeOptionLst_001.add(timeOption1);
    timeOptionLst_001.add(timeOption2);

    timeOptionLst_002.add(timeOption1);
    timeOptionLst_002.add(timeOption2);

    timeOptionLst_003.add(timeOption1);
    timeOptionLst_003.add(timeOption2);

    participants1.add("toannh");
    participants2.add("giangnt");
    participants3.add("tuyennt");
    participants4.add("trangvh");

    userVotes.add(new UserVoted("toannh", timeOption1.getId(), 1));
    userVotes.add(new UserVoted("giangnt", timeOption1.getId(), 1));

    Meeting m1 = new Meeting("/Meeting/m1", "Meeting 001", "HANOI", "Meeting 001 descriptions", timeOptionLst_001,
            cal1.getTime().getTime(), true, "toannh", "type", participants4, userVotes, 1,
            "/rest/jcr/repository/collaboration/document1.docx", cal1.getTimeInMillis(), cal1.getTimeInMillis());
    Meeting m2 = new Meeting("/Meeting/m1", "Meeting 001", "HANOI", "Meeting 001 descriptions", timeOptionLst_001,
            cal1.getTime().getTime(), true, "giangnt", "type", participants3, userVotes, 1,
            "/rest/jcr/repository/collaboration/document2.docx", cal1.getTimeInMillis(), cal1.getTimeInMillis());
    Meeting m3 = new Meeting("/Meeting/m1", "Meeting 001", "HANOI", "Meeting 001 descriptions", timeOptionLst_001,
            cal1.getTime().getTime(), true, "tuyennt", "type", participants2, userVotes, 1,
            "/rest/jcr/repository/collaboration/document3.docx", cal1.getTimeInMillis(), cal1.getTimeInMillis());
    Meeting m4 = new Meeting("/Meeting/m1", "Meeting 001", "HANOI", "Meeting 001 descriptions", timeOptionLst_001,
            cal1.getTime().getTime(), true, "trangvh", "type", participants1, userVotes, 1,
            "/rest/jcr/repository/collaboration/document4.docx", cal1.getTimeInMillis(), cal1.getTimeInMillis());
    meetingService.save(m1);
    meetingService.save(m2);
    meetingService.save(m3);
    meetingService.save(m4);
  }

  private Meeting addSimpleMeeting() throws Exception {
    Calendar cal1 = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    List<String> participants1 = new ArrayList<String>();
    List<String> participants2 = new ArrayList<String>();
    List<String> participants3 = new ArrayList<String>();
    List<String> participants4 = new ArrayList<String>();
    List<UserVoted> userVotes = new ArrayList<UserVoted>();
    List<TimeOption> timeOptionLst_001 = new ArrayList<TimeOption>();
    List<TimeOption> timeOptionLst_002 = new ArrayList<TimeOption>();
    List<TimeOption> timeOptionLst_003 = new ArrayList<TimeOption>();

    TimeOption timeOption1 = new TimeOption(true, cal1.getTimeInMillis(), cal2.getTimeInMillis());
    TimeOption timeOption2 = new TimeOption(true, cal1.getTimeInMillis(), cal2.getTimeInMillis());

    timeOptionLst_001.add(timeOption1);
    timeOptionLst_001.add(timeOption2);

    timeOptionLst_002.add(timeOption1);
    timeOptionLst_002.add(timeOption2);

    timeOptionLst_003.add(timeOption1);
    timeOptionLst_003.add(timeOption2);

    participants4.add("toannh");
    participants4.add("giangnt");
    participants4.add("tuyennt");
    participants4.add("trangvh");

    userVotes.add(new UserVoted("trangvh", timeOption1.getId(), 1));
    userVotes.add(new UserVoted("giangnt", timeOption1.getId(), 1));
    Meeting m1 = new Meeting("/Meeting/m" + cal1.getTimeInMillis(), "Meeting 001", "HANOI", "Meeting 001 descriptions", timeOptionLst_001,
            cal1.getTime().getTime(), true, "team-A", "type", participants4, userVotes, 1,
            "/rest/jcr/repository/collaboration/document1.docx", cal1.getTimeInMillis(), cal1.getTimeInMillis());
    meetingService.save(m1);
    return m1;
  }

}
