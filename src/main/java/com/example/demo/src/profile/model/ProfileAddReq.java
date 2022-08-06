package com.example.demo.src.profile.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ProfileAddReq {

    private int profileImageIdx;
    private int userIdx;
    private String name;
}
