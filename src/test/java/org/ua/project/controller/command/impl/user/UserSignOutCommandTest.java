package org.ua.project.controller.command.impl.user;

import org.junit.Test;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserSignOutCommandTest {

    @Test
    public void testExecute() throws Exception {
        String userLogin = "test";

        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);
        HttpSession httpSessionMock = mock(HttpSession.class);
        when(httpSessionMock.getAttribute(ControllerConstants.USER_ATTR)).thenReturn(new User.Builder()
                .setLogin(userLogin)
                .setRole(User.Role.STUDENT)
                .build());

        Set<String> loggedUsers = new HashSet<>();
        loggedUsers.add(userLogin);
        ServletContext servletContextMock = mock(ServletContext.class);
        when(servletContextMock.getAttribute(ControllerConstants.LOGGED_USERS_ATTR)).thenReturn(loggedUsers);

        when(httpSessionMock.getServletContext()).thenReturn(servletContextMock);
        when(httpServletRequestMock.getSession()).thenReturn(httpSessionMock);

        HttpServletResponse httpServletResponseMock = mock(HttpServletResponse.class);

        assertEquals(ControllerConstants.REDIRECT_TO_MAIN_PAGE, new UserSignOutCommand().execute(httpServletRequestMock, httpServletResponseMock));
        assertEquals(0, loggedUsers.size());
    }
}
