package org.ua.project.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Entity, Serializable {
    private static final long serialVersionUID = 1394355768393479048L;

    private int id;
    private String firstName;
    private String lastName;
    private String login;
    private LocalDate birthDate;
    private UserRole role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(login, user.login) && Objects.equals(birthDate, user.birthDate) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, login, birthDate, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", birthDate=" + birthDate +
                ", role=" + role +
                '}';
    }
}
