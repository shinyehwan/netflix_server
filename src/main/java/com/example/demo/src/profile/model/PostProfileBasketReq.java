package com.example.demo.src.profile.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostProfileBasketReq {

    private int profileId;
    private int movieId;
    private int seriesId;

}
