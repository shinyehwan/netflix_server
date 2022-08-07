package com.example.demo.src.series.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Series {
    private int seriesIdx;
    private String title;
    private String summary;
    private String releaseYear;
    private int runtime;
    private String filmUrl;
    private String previewUrl;
    private int filmRating;
    private String resolution;
    private String posterUrl;
    private int isOriginal;


}
