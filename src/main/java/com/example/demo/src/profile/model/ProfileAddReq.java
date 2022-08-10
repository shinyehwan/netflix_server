package com.example.demo.src.profile.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ProfileAddReq {
    private int userIdx;
    private int profileImageId;
    private String name;
}
