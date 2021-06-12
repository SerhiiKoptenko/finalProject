package org.ua.project.controller.command.impl.guest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.*;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(UserSignInCommand.class)
public class UserSignInCommandTest {


    @Test
    public void testCreateDirectoryStructure_usingCreateMockAndExpectNew() throws Exception {
        String userLogin = "test";
        String userPassword = "4444";

        UserService userServiceMock = mock(UserService.class);
        whenNew(UserService.class).withNoArguments().thenReturn(userServiceMock);

        when(userServiceMock.signInUser(userLogin, userPassword)).thenReturn(new User.Builder()
        .setLogin(userLogin)
        .setPassword(userPassword)
        .build());

        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);
        when(httpServletRequestMock.getParameter(Parameter.LOGIN.getValue())).thenReturn(userLogin);
        when(httpServletRequestMock.getParameter(Parameter.PASSWORD.getValue())).thenReturn(userPassword);

        HttpSession httpSessionMock = mock(HttpSession.class);
        HttpServletResponse httpServletResponseMock = mock(HttpServletResponse.class);
        ServletContext servletContextMock = mock(ServletContext.class);

        Set<String> loggedUsers = new HashSet<>();

        when(servletContextMock.getAttribute(ControllerConstants.LOGGED_USERS_ATTR)).thenReturn(loggedUsers);
        when(httpSessionMock.getServletContext()).thenReturn(servletContextMock);
        when(httpServletRequestMock.getSession()).thenReturn(httpSessionMock);

        String url = new UserSignInCommand().execute(httpServletRequestMock, httpServletResponseMock);
        assertEquals("redirect:/main_page", url);
        assertTrue(loggedUsers.contains(userLogin));
    }
}
