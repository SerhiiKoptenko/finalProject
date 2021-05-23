package org.ua.project.service;

public class ServiceContainer {
    private static final UserService userService = new UserService();

    public static UserService getUserService() {
        return userService;
    }
}
