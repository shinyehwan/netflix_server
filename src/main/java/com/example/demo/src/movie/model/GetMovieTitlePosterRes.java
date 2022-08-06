package com.example.demo.src.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMovieTitlePosterRes {
    private String title;
    private String posterUrl;
}
