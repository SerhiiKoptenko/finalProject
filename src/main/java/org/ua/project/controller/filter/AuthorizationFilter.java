package org.ua.project.controller.filter;

import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AuthorizationFilter extends HttpFilter {
    private static final Map<User.Role, List<String>> accessMap = new HashMap<>();
    private static final List<String> allUrls = new ArrayList<>();

    @Override
    public void init() throws ServletException {

        List<String> guestUrls = new ArrayList<>();
        guestUrls.add("/registration_page");
        guestUrls.add("/sign_in_page");
        guestUrls.add("/registration_page?command=register");
        guestUrls.add("/signIn_page?command=signIn");
        guestUrls.add("/main_page");
        accessMap.put(User.Role.GUEST, guestUrls);

        List<String> studentUrls = new ArrayList<>();
        studentUrls.add("/main_page");
        studentUrls.add("/main_page?command=enroll");
        studentUrls.add("/user/enroll");
        studentUrls.add("/user/personal_cabinet");
        studentUrls.add("/user/personal_cabinet?command=displayStudentsCourses");
        studentUrls.add("/user/personal_cabinet/leave_course");
        studentUrls.add("/user/personal_cabinet/leave_course?command=leaveCourse");
        accessMap.put(User.Role.STUDENT, studentUrls);

        List<String> adminUrls = new ArrayList<>();
        adminUrls.add("/admin/manage_students");
        adminUrls.add("/admin/manage_courses");
        adminUrls.add("/admin/manage_courses?command=addCourse");
        adminUrls.add("/admin/manage_courses?command=addTheme");
        adminUrls.add("/admin/manage_courses?command=removeTheme");
        adminUrls.add("/admin/edit_course");
        adminUrls.add("/admin/edit_course?command=updateCourse");
        adminUrls.add("/admin/delete_course");
        adminUrls.add("/admin/manage_courses?command=deleteCourse");
        adminUrls.add("/admin/manage_students?command=updateUserBlockedStatus");
        adminUrls.add("/admin/register_tutor");
        adminUrls.add("/admin/register_tutor?command=register");

        accessMap.put(User.Role.ADMIN, adminUrls);

        List<String> tutorUrls = new ArrayList<>();
        tutorUrls.add("/user/personal_cabinet?command=displayTutorsCourses");
        tutorUrls.add("/user/personal_cabinet/journal");
        tutorUrls.add("/user/personal_cabinet?command=updateMark");
        accessMap.put(User.Role.TUTOR, tutorUrls);

        allUrls.addAll(guestUrls);
        allUrls.addAll(studentUrls);
        allUrls.addAll(tutorUrls);
        allUrls.addAll(adminUrls);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String url = request.getRequestURI();
        Optional<String> command = Optional.ofNullable(request.getParameter("command"));
        if (command.isPresent()) {
            url += "?command=" + command.get();
        }
        Optional<User> userOpt = Optional.ofNullable((User) request.getSession().getAttribute(ControllerConstants.USER_ATTR));
        User user = userOpt.orElse(new User.Builder()
                .setRole(User.Role.GUEST)
                .build());

        if (allUrls.contains(url)) {
            if (!User.Role.GUEST.equals(user.getRole())) {
                Set<String> loggedUsers = (Set<String>) request.getSession().getServletContext().getAttribute(ControllerConstants.LOGGED_USERS_ATTR);
                if (!loggedUsers.contains(user.getLogin())) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    request.getSession().invalidate();
                    return;
                }
            }
            if (!accessMap.get(user.getRole()).contains(url)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
