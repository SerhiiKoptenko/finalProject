package org.ua.project.model.dao.impl.mapper;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CourseMapper implements ObjectMapper {

    @Override
    public Course extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("course_id");
        String courseName = resultSet.getString("name");
        LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
        String description = resultSet.getString("description");

        Theme theme = new Theme.Builder()
                .setName(resultSet.getString("theme_name"))
                .build();

        User tutor = new User.Builder()
                .setLogin(resultSet.getString("login"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .build();

        return  new Course.Builder()
                .setId(id)
                .setName(courseName)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setDescription(description)
                .setTutor(tutor)
                .setTheme(theme)
                .build();
    }

    public List<Course> extractCoursesAsList(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            courses.add(extractFromResultSet(resultSet));
        }
        return courses;
    }
}
