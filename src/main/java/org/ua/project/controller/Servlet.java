package org.ua.project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.command.impl.GoToMainPageCommand;
import org.ua.project.controller.command.impl.admin.*;
import org.ua.project.controller.command.impl.guest.GoToRegistrationPageCommand;
import org.ua.project.controller.command.impl.guest.GoToSignInPageCommand;
import org.ua.project.controller.command.impl.guest.UserRegistrationCommand;
import org.ua.project.controller.command.impl.guest.UserSignInCommand;
import org.ua.project.controller.command.impl.user.GoToPersonalCabinetCommand;
import org.ua.project.controller.command.impl.user.student.*;
import org.ua.project.controller.command.impl.user.tutor.DisplayStudentsByCourseCommand;
import org.ua.project.controller.command.impl.user.UserSignOutCommand;
import org.ua.project.controller.command.impl.user.tutor.DisplayTutorsCoursesCommand;
import org.ua.project.controller.command.impl.user.tutor.UpdateStudentsMarkCommand;
import org.ua.project.controller.constants.ControllerConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

public class Servlet extends HttpServlet {
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(Servlet.class);

    public void init(ServletConfig servletConfig) {
        servletConfig.getServletContext()
                .setAttribute(ControllerConstants.LOGGED_USERS_ATTR, new HashSet<String>());

        commands.put("/admin/manage_students", new GoToManageStudentsCommand());
        commands.put("/admin/manage_courses", new GoToManageCoursesPageCommand());
        commands.put("/admin/manage_courses?command=addCourse", new AddCourseCommand());
        commands.put("/admin/manage_courses?command=addTheme", new AddThemeCommand());
        commands.put("/admin/manage_courses?command=removeTheme", new RemoveThemeCommand());
        commands.put("/admin/edit_course", new GoToEditCourseCommand());
        commands.put("/admin/edit_course?command=updateCourse", new UpdateCourseCommand());
        commands.put("/admin/delete_course", new GoToDeleteCourseCommand());
        commands.put("/admin/manage_courses?command=deleteCourse", new DeleteCourseCommand());
        commands.put("/admin/manage_students?command=updateUserBlockedStatus", new UpdateUserBlockedStatusCommand());
        commands.put("/admin/register_tutor", new GoToRegisterTutorCommand());
        commands.put("/admin/register_tutor?command=register", new RegisterTutorCommand());

        commands.put("/registration_page", new GoToRegistrationPageCommand());
        commands.put("/sign_in_page", new GoToSignInPageCommand());
        commands.put("/registration_page?command=register", new UserRegistrationCommand());
        commands.put("/signIn_page?command=signIn", new UserSignInCommand());
        commands.put("/signOut", new UserSignOutCommand());


        commands.put("/main_page", new GoToMainPageCommand());
        commands.put("/main_page?command=enroll", new EnrollCommand());
        commands.put("/user/enroll", new GoToEnrollCommand());
        commands.put("/user/personal_cabinet", new GoToPersonalCabinetCommand());
        commands.put("/user/personal_cabinet?command=displayTutorsCourses", new DisplayTutorsCoursesCommand());

        commands.put("/user/personal_cabinet?command=displayStudentsCourses", new DisplayCoursesByStudentCommand());
        commands.put("/user/personal_cabinet/journal", new DisplayStudentsByCourseCommand());
        commands.put("/user/personal_cabinet?journal?command=updateMark", new UpdateStudentsMarkCommand());
        commands.put("/user/personal_cabinet/leave_course", new GoToLeaveCourseCommand());
        commands.put("/user/personal_cabinet/leave_course?command=leaveCourse", new LeaveCourseCommand());



    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        Optional<String> commandParameter = Optional.ofNullable(req.getParameter("command"));
        if (commandParameter.isPresent()) {
            path += "?command=" + commandParameter.get();
        }
        String lastRequest = req.getRequestURI();
        Optional<String> queryString = Optional.ofNullable(req.getQueryString());
        if (queryString.isPresent()) {
            lastRequest += "?" + queryString.get();
        }
        req.getSession().setAttribute("lastRequest", lastRequest);
        logger.info("received path {}", path);


        Optional<Command> commandOpt = Optional.ofNullable(commands.get(path));
        if (commandOpt.isPresent()) {
            String page = commandOpt.get().execute(req, resp);
            if (page.startsWith(ControllerConstants.REDIRECT_PREFIX)) {
                logger.info("Redirecting to: {}", page);
                resp.sendRedirect(page.replaceFirst(ControllerConstants.REDIRECT_PREFIX, ""));
                return;
            }
            logger.info("Forwarding to: {}", page);
            req.getRequestDispatcher(page).forward(req, resp);
        } else {
            logger.error("Unknown url: {}", path);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
