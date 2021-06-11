package org.ua.project.controller.constants;

public class ControllerConstants {

    //suppress default constructor
   private ControllerConstants() {
       throw new AssertionError();
   }

    public static final String REDIRECT_PREFIX = "redirect:";

    public static final String USER_ATTR = "user";
    public static final String USER_ROLE_ATTR = "userRole";
    public static final String LOGGED_USERS_ATTR = "loggedUsers";

    public static final String ERROR_ATR = "error";

    public static final String REDIRECT_TO_REGISTER_TUTOR_PAGE = REDIRECT_PREFIX + "/admin/register_tutor";
    public static final String REDIRECT_TO_MANAGE_COURSES_PAGE = REDIRECT_PREFIX + "/admin/manage_courses";
    public static final String REDIRECT_TO_EDIT_COURSE_PAGE = REDIRECT_PREFIX + "/admin/edit_course";
    public static final String REDIRECT_TO_MAIN_PAGE = REDIRECT_PREFIX + "/main_page";
    public static final String REDIRECT_TO_MANAGE_USERS_PAGE = REDIRECT_PREFIX + "/admin/manage_students";
    public static final String REDIRECT_TO_PERSONAL_CABINET = REDIRECT_PREFIX + "/user/personal_cabinet";
    public static final String REDIRECT_TO_JOURNAL = REDIRECT_PREFIX + "/user/personal_cabinet/journal";
    public static final String REDIRECT_TO_ENROLL_PAGE = REDIRECT_PREFIX + "/user/enroll";
    public static final String REDIRECT_TO_REGISTRATION_PAGE = REDIRECT_PREFIX + "/registration_page";
    public static final String REDIRECT_TO_SIGN_IN_PAGE = REDIRECT_PREFIX + "/sign_in_page";

    public static final String FORWARD_TO_REGISTER_TUTOR_PAGE = "/WEB-INF/jsp/admin/registerTutor.jsp";
    public static final String FORWARD_TO_LEAVE_COURSE = "/WEB-INF/jsp/user/leave_course.jsp";
    public static final String FORWARD_TO_MANAGE_STUDENTS_PAGE = "/WEB-INF/jsp/admin/manage_students.jsp";
    public static final String FORWARD_TO_PERSONAL_CABINET = "/WEB-INF/jsp/user/personal_cabinet.jsp";
    public static final String FORWARD_TO_ENROLL_PAGE = "/WEB-INF/jsp/user/enroll.jsp";
    public static final String FORWARD_TO_MAIN_PAGE = "/WEB-INF/jsp/main.jsp";
    public static final String FORWARD_TO_EDIT_COURSE_PAGE = "/WEB-INF/jsp/admin/edit_course.jsp";
    public static final String FORWARD_TO_DELETE_COURSE_PAGE = "/WEB-INF/jsp/admin/delete_course.jsp";
    public static final String FORWARD_TO_JOURNAL = "/WEB-INF/jsp/user/journal.jsp";
    public static final String FORWARD_TO_USER_REGISTRATION = "/WEB-INF/jsp/registration.jsp";
    public static final String FORWARD_TO_SIGN_IN = "/WEB-INF/jsp/signIn.jsp";

    public static final String ERROR_500_PAGE = "/WEB-INF/error500";
    public static final String FORWARD_TO_ERROR_PAGE = "/error.jsp";

    public static final int ITEMS_PER_PAGE = 3;
}
