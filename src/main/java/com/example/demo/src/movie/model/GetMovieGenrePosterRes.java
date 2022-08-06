package com.example.demo.src.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMovieGenrePosterRes {
    private String genre;
    private String posterUrl;
}
