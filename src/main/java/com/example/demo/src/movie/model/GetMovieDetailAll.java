package com.example.demo.src.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMovieDetailAll {
    private String actorName;
    private String directorName;
    private String writer;
    private int filmRating;
    private String genre;
    private String movieFeature;
}
