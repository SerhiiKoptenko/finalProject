package org.ua.project.controller.command.impl.guest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.ua.project.controller.constants.ControllerConstants;
import org.ua.project.controller.constants.Parameter;
import org.ua.project.controller.util.authorization.RegistrationUtility;
import org.ua.project.model.entity.User;
import org.ua.project.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({StudentRegistrationCommand.class, RegistrationUtility.class})
public class StudentRegistrationCommandTest {

    @Test
    public void testExecute() throws Exception {
        String firstName = "First";
        String lastName = "Last";
        String login = "login";
        String password = "1111";

        User user = new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setLogin(login)
                .setPassword(password)
                .setRole(User.Role.STUDENT)
                .build();

        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);
        when(httpServletRequestMock.getParameter(Parameter.FIRST_NAME.getValue())).thenReturn(firstName);
        when(httpServletRequestMock.getParameter(Parameter.LAST_NAME.getValue())).thenReturn(lastName);
        when(httpServletRequestMock.getParameter(Parameter.LOGIN.getValue())).thenReturn(login);
        when(httpServletRequestMock.getParameter(Parameter.PASSWORD.getValue())).thenReturn(password);

        HttpServletResponse httpServletResponseMock = mock(HttpServletResponse.class);

        UserService userServiceMock = mock(UserService.class);
        whenNew(UserService.class).withNoArguments().thenReturn(userServiceMock);

        doNothing().when(userServiceMock).registerUser(user);

        String expected = ControllerConstants.REDIRECT_TO_REGISTRATION_PAGE + "?registrationResult=success";
        assertEquals(expected, new StudentRegistrationCommand().execute(httpServletRequestMock, httpServletResponseMock));
    }
}
