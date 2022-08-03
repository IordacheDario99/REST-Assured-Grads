package com.endava.petclinic.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Boolean enabled;
    private String username;
    private String password;
    private List<Role> roles;

    public User() {
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "enabled=" + enabled +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    public User(String username, String password, String... roles) { //String... / unde ... este parametru optional care returneaza un array de tipurile de date cerute (String)
        this.enabled = true;
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();

        for (String str : roles) {
            Role role = new Role(str);
            this.roles.add(role);
        }
    }

}
