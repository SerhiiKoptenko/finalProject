package org.ua.project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.controller.command.Command;
import org.ua.project.controller.command.impl.*;
import org.ua.project.controller.command.impl.gotocommands.*;
import org.ua.project.controller.constants.ControllerConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@WebServlet("/")
public class Servlet extends HttpServlet {
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(Servlet.class);

    public void init(ServletConfig servletConfig) {
        servletConfig.getServletContext()
                .setAttribute(ControllerConstants.LOGGED_USERS_ATTR, new HashSet<String>());

        commands.put("/admin/admin_basis", new GoToAdminBasisCommand());
        commands.put("/admin/manage_students", new GoToManageUsersCommand());
        commands.put("/admin/manage_courses", new GoToManageCoursesPageCommand());
        commands.put("/admin/manage_courses?command=addCourse", new AddCourseCommand());
        commands.put("/admin/manage_courses?command=addTheme", new AddThemeCommand());
        commands.put("/admin/manage_courses?command=removeTheme", new RemoveThemeCommand());
        commands.put("/admin/edit_course", new GoToEditCourseCommand());
        commands.put("/admin/edit_course?command=updateCourse", new UpdateCourseCommand());
        commands.put("/admin/delete_course", new GoToDeleteCourseCommand());
        commands.put("/admin/manage_courses?command=deleteCourse", new DeleteCourseCommand());
        commands.put("/admin/manage_students?command=updateUserBlockedStatus", new UpdateUserBlockedStatusCommand());

        commands.put("/registration_page", new GoToRegistrationPageCommand());
        commands.put("/sign_in_page", new GoToSignInPageCommand());
        commands.put("/registration_page?command=register", new UserRegistrationCommand());
        commands.put("/signIn_page?command=signIn", new UserSignInCommand());
        commands.put("/signOut", new UserSignOutCommand());



        commands.put("/main_page", new GoToMainPageCommand());
        commands.put("/users/enroll?command=enroll", new EnrollCommand());
        commands.put("/user/personal_cabinet", new GoToPersonalCabinetCommand());
        commands.put("/user/personal_cabinet?command=displayTutorsCourses", new DisplayTutorsCoursesCommand());

        commands.put("/user/personal_cabinet?command=displayStudentsCourses", new DisplayCoursesByStudentCommand());
        commands.put("/user/personal_cabinet/journal", new DisplayStudentsByCourseCommand());
        commands.put("/user/personal_cabinet?command=updateMark", new UpdateStudentsMarkCommand());
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
        String commandName = req.getParameter("command");
        if (commandName != null) {
            path += "?command=" + commandName;
        }
        logger.trace("received path {}", path);
        Optional<Command> commandOpt = Optional.ofNullable(commands.get(path));
        if (commandOpt.isPresent()) {
            String page = commandOpt.get().execute(req, resp);
            if (page.startsWith(ControllerConstants.REDIRECT_PREFIX)) {
                logger.trace("Redirecting to: {}", page);
                resp.sendRedirect(page.replaceFirst(ControllerConstants.REDIRECT_PREFIX, ""));
            } else {
                logger.trace("Forwarding to: {}",  page);
                req.getRequestDispatcher(page).forward(req, resp);
            }
        } else {
            logger.error("unknown command: {}", path);
        }
    }
}
