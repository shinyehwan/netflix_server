package com.example.demo.src.movie;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.movie.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/netflix/movie")
@Slf4j
public class MovieController {

    private final MovieProvider movieProvider;
    private final MovieService service;

    @Autowired
    public MovieController(MovieProvider movieProvider, MovieService service)
    {
        this.movieProvider = movieProvider;
        this.service = service;
    }

    /**
     * 메인 화면 단순 영화 포스터 조회
     * /movie
     */

    @ResponseBody
    @GetMapping
    public BaseResponse<List<GetMoviePosterUrlRes>> getMoviePoster(){
        try{
            List<GetMoviePosterUrlRes> getMoviePosterUrlRes = movieProvider.getMoviePoster();
            return new BaseResponse<>(getMoviePosterUrlRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 모든 영화 정보 조회 API
     * [GET] /movie/info
     *
     * 해당 posterUrl을 갖는 영화 정보 조회 API
     * [GET] /movie/info?posterUrl=
     */

    @ResponseBody
    @GetMapping("/info")
    public BaseResponse<List<GetMovieInfoRes>> getMovie(@RequestParam(required = false) String posterUrl){

        try{
            if (posterUrl == null) {
                List<GetMovieInfoRes> getMovieInfoRes = movieProvider.getMovieInfoTotal();
                return new BaseResponse<>(getMovieInfoRes);
            }
            // query string인 posterUrl 있을 경우, 조건을 만족하는 유저정보들을 불러온다.
            List<GetMovieInfoRes> getMovieInfoRes = movieProvider.getMovie(posterUrl);
            return new BaseResponse<>(getMovieInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 제목 검색
     * 해당 타이틀을 갖는 영화 포스터 조회
     * /movie-title?title=
     */

    @ResponseBody
    @GetMapping("/movie-title")
    public BaseResponse<List<GetMovieTitlePosterRes>> getMovieTitle(@RequestParam String title){

        try{
            List<GetMovieTitlePosterRes> getMovieTitlePosterRes = movieProvider.getMoviePosterByTitle(title);
            return new BaseResponse<>(getMovieTitlePosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 감독 검색
     * /movie-director?director=
     */
    @ResponseBody
    @GetMapping("/movie-director")
    public BaseResponse<List<GetMovieDirectorPosterRes>> getMovieDirector(@RequestParam String director){

        try{
            List<GetMovieDirectorPosterRes> getMovieDirectorPosterRes = movieProvider.getMoviePosterByDirector(director);
            return new BaseResponse<>(getMovieDirectorPosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 배우 검색
     * @param actor
     * /movie-actor?actor=
     */
    @ResponseBody
    @GetMapping("/movie-actor")
    public BaseResponse<List<GetMovieActorPosterRes>> getMovieActor(@RequestParam String actor){
        try{
            List<GetMovieActorPosterRes> getMovieActorPosterRes = movieProvider.getMoviePosterByActor(actor);
            return new BaseResponse<>(getMovieActorPosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    /**
     * 각본가 검색
     * @param writer
     * /movie-writer?writer=
     */
    @ResponseBody
    @GetMapping("/movie-writer")
    public BaseResponse<List<GetMovieWriterPosterRes>> getMovieWriter(@RequestParam String writer){

        try{
            List<GetMovieWriterPosterRes> getMovieWriterPosterRes = movieProvider.getMoviePosterByWriter(writer);
            return new BaseResponse<>(getMovieWriterPosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    /**
     * 장르 검색
     * @param genre
     * /movie-genre?genre=
     */
    @ResponseBody
    @GetMapping("/movie-genre")
    public BaseResponse<List<GetMovieGenrePosterRes>> getMovieGenre(@RequestParam String genre){

        try{
            List<GetMovieGenrePosterRes> getMovieGenrePosterRes = movieProvider.getMoviePosterByGenre(genre);
            return new BaseResponse<>(getMovieGenrePosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }






}
