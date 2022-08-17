package com.example.demo.src.profile.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSeriesAssessReq {
    private int seriesId;
    private int assessment;
}
