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
       allUrls.add("/admin/admin_basis");
       allUrls.add("/admin/manage_students");
       allUrls.add("/admin/manage_courses");
       allUrls.add("/admin/manage_courses?command=addCourse");
       allUrls.add("/admin/manage_courses?command=addTheme");
       allUrls.add("/admin/manage_courses?command=removeTheme");
       allUrls.add("/admin/edit_course");
       allUrls.add("/admin/edit_course?command=updateCourse");
       allUrls.add("/admin/delete_course");
       allUrls.add("/admin/manage_courses?command=deleteCourse");
       allUrls.add("/admin/manage_students?command=updateUserBlockedStatus");
       allUrls.add("/admin/register_tutor");
       allUrls.add("/admin/register_tutor?command=register");
       allUrls.add("/registration_page");
       allUrls.add("/sign_in_page");
       allUrls.add("/registration_page?command=register");
       allUrls.add("/signIn_page?command=signIn");
       allUrls.add("/main_page");
       allUrls.add("/main_page?command=enroll");
       allUrls.add("/user/enroll");
       allUrls.add("/user/personal_cabinet");
       allUrls.add("/user/personal_cabinet?command=displayTutorsCourses");
       allUrls.add("/user/personal_cabinet?command=displayStudentsCourses");
       allUrls.add("/user/personal_cabinet/journal");
       allUrls.add("/user/personal_cabinet?command=updateMark");
       allUrls.add("/user/personal_cabinet/leave_course");
       allUrls.add("/user/personal_cabinet/leave_course?command=leaveCourse");

        List<String> guestUrls = new ArrayList<>();
        guestUrls.add("/main_page");
        guestUrls.add("/sign_in_page");
        guestUrls.add("/registration_page");
        accessMap.put(User.Role.GUEST, guestUrls);


        List<String> studentUrls = new ArrayList<>();
        studentUrls.add("/user/personal_cabinet");
        studentUrls.add("/main_page");
        studentUrls.add("/user/personal_cabinet/leave_course");
        studentUrls.add("/main_page?command=enroll");
        studentUrls.add("/user/enroll");
        accessMap.put(User.Role.STUDENT, studentUrls);

        List<String> adminUrls = new ArrayList<>();
        adminUrls.add("/admin/manage_courses");
        adminUrls.add("/admin/admin_basis");
        adminUrls.add("/admin/edit_course");
        adminUrls.add("/admin/register_tutor");
        adminUrls.add("/admin/delete_course");
        adminUrls.add("/admin/manage_students");
        adminUrls.add("/main_page");
        accessMap.put(User.Role.ADMIN, adminUrls);

        List<String> tutorUrls = new ArrayList<>();
        tutorUrls.add("/user/personal_cabinet/journal");
        tutorUrls.add("/user/personal_cabinet");
        accessMap.put(User.Role.TUTOR, tutorUrls);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        String url = request.getRequestURI() + request.getParameter("command");
        Optional<User> userOpt = Optional.ofNullable((User) request.getSession().getAttribute(ControllerConstants.USER_ATTR));
        if (userOpt.isPresent() && allUrls.contains(url)
                && !accessMap.get(userOpt.get().getRole())
                .contains(url)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            chain.doFilter(req, res);
        }
    }
}
