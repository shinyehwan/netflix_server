package com.example.demo.src.series.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetSeriesEpisodeRes {

    private int seriesId;
    private int season;
    private String name;
    private String episodeImgUrl;
    private int episodeRuntime;
    private String episodeSummary;
}
