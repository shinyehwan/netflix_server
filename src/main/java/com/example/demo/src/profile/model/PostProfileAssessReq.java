package com.example.demo.src.profile.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PostProfileAssessReq {

    private Integer movieId;
    private Integer seriesId;
    private int assessment;

}
