package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProfileAlarmRes {
    private int userId;
    private int profileId;
    private String title;
    private String content;
    private String alarmUrl;
}
