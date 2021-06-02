package org.ua.project.controller.constants;

public class ControllerConstants {
    public static final String REDIRECT_PREFIX = "redirect:";


    public static final String USER_LOGIN_ATTR = "userLogin";
    public static final String USER_ROLE_ATTR = "userRole";
    public static final String LOGGED_USERS_ATTR = "loggedUsers";

    public static final String REDIRECT_TO_MANAGE_COURSES_PAGE = REDIRECT_PREFIX + "/admin/manage_courses";
    public static final String REDIRECT_TO_EDIT_COURSE_PAGE = REDIRECT_PREFIX + "/admin/edit_course";
    public static final String REDIRECT_TO_MAIN_PAGE = REDIRECT_PREFIX + "/main_page";

    public static final String FORWARD_TO_MAIN_PAGE = "/WEB-INF/jsp/main.jsp";
    public static final String FORWARD_TO_EDIT_COURSE_PAGE = "/WEB-INF/jsp/admin/edit_course.jsp";
    public static final String FORWARD_TO_DELETE_COURSE_PAGE = "/WEB-INF/jsp/admin/delete_course.jsp";
    public static final String FORWARD_TO_COURSES_BY_THEME = "/WEB-INF/jsp/courses_theme.jsp";

    public static final int ITEMS_PER_PAGE = 5;
    private ControllerConstants() {}
}
