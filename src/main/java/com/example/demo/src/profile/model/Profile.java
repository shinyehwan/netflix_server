package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Profile {

    private int profileIdx;
    private int userIdx;
    private int profileImageIdx;
    private String name;
    private int language;
    private int ageGrade;
    private int autoPrePlay;
    private int autoNextPlay;

}
