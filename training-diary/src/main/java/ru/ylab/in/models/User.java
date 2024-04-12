package ru.ylab.in.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class User {

    public List<TrainingTypeInterface> trainingList = new ArrayList<>();

    private Long id;
    private String login;
    private String password;
    private String role;
    private String name;
    private int trainingIndex = 0;


    public User(Long id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this. password = password;
        this.role = role;
    }

}
