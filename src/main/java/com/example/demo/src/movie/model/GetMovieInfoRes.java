package com.example.demo.src.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

// 모든 영화 조회
public class GetMovieInfoRes {

    private int movieIdx;
    private String title;
    private String summary;
    private String releaseYear;
    private int runtime;
    private String movieUrl;
    private String previewUrl;
    private int filmRating;
    private String resolution;
    private String posterUrl;
    private int isOriginal;

}
