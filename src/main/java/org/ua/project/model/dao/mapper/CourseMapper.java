package org.ua.project.model.dao.mapper;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseMapper implements EntityMapper<Course> {

    @Override
    public Course extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String courseName = resultSet.getString("name");
        LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
        String description = resultSet.getString("description");
        int studentsCount = resultSet.getInt("students_count");
        Theme theme = new Theme.Builder()
                .setName(resultSet.getString("theme_name"))
                .setId(resultSet.getInt("theme_id"))
                .build();

        Optional<String> tutorLogin = Optional.ofNullable(resultSet.getString("login"));

        User tutor = null;
        if (tutorLogin.isPresent()) {
            tutor = new User.Builder()
                    .setId(resultSet.getInt("user_id"))
                    .setLogin(tutorLogin.get())
                    .setFirstName(resultSet.getString("first_name"))
                    .setLastName(resultSet.getString("last_name"))
                    .build();
        }

        List<User> studentsList = new ArrayList<>();

        for (int i = 0; i < studentsCount; i++) {
            studentsList.add(new User());
        }

        return new Course.Builder()
                .setId(id)
                .setName(courseName)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setDescription(description)
                .setTutor(tutor)
                .setTheme(theme)
                .setStudents(studentsList)
                .build();
    }
}
