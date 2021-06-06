package org.ua.project;

import java.util.Locale;
import java.util.ResourceBundle;

public class TestResourceBundle {

    public static void main(String[] args) {
        Locale locale = new Locale("en");
        Locale locale1 = new Locale("ru");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/app", locale);
        ResourceBundle resourceBundle1 = ResourceBundle.getBundle("i18n/app", locale1);

        System.out.println(resourceBundle.getString("apple"));
        System.out.println(resourceBundle1.getString("apple"));
    }
}
