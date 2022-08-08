package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProfileBasketRes {
    private int userIdx;
    private int profileIdx;
    private String moviePosterUrl;
    private String seriesPosterUrl;
}
