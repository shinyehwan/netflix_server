package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileImage {

    private int profileImageIdx;
    private String profileImageUrl;
    private String profileCategory;
}
