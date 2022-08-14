package com.example.demo.src.movie;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.movie.model.*;
import com.example.demo.src.series.model.GetSeriesEpisodeRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/netflix/users/{userIdx}/profile/{profileIdx}/movie")
@Slf4j
public class MovieController {

    private final MovieProvider movieProvider;
    private final MovieService service;
    private final JwtService jwtService;

    @Autowired
    public MovieController(MovieProvider movieProvider, MovieService service, JwtService jwtService)
    {
        this.movieProvider = movieProvider;
        this.service = service;
        this.jwtService = jwtService;

    }

    /**
     * 1. 메인 화면 단순 영화 포스터 조회
     * /movie
     */

    @ResponseBody
    @GetMapping
    public BaseResponse<List<GetMoviePosterUrlRes>> getMoviePoster(@PathVariable int userIdx,
                                                                   @PathVariable int profileIdx){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMoviePosterUrlRes> getMoviePosterUrlRes = movieProvider.getMoviePoster();
            return new BaseResponse<>(getMoviePosterUrlRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 2. 모든 영화 정보 조회 API
     * [GET] /movie/info
     *
     * 해당 posterUrl을 갖는 영화 정보 조회 API
     * [GET] /movie/info?posterUrl=
     */

    @ResponseBody
    @GetMapping("/info")
    public BaseResponse<List<GetMovieInfoRes>> getMovie(@PathVariable int userIdx,
                                                        @PathVariable int profileIdx,
                                                        @RequestParam(required = false) String posterUrl){

        try{

            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
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
     * 3. 제목 검색
     * 해당 타이틀을 갖는 영화 포스터 조회
     * /movie-title?title=
     */

    @ResponseBody
    @GetMapping("/movie-title")
    public BaseResponse<List<GetMovieTitlePosterRes>> getMovieTitle(@PathVariable int userIdx,
                                                                    @PathVariable int profileIdx,
                                                                    @RequestParam String title){

        try{

            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMovieTitlePosterRes> getMovieTitlePosterRes = movieProvider.getMoviePosterByTitle(title);
            return new BaseResponse<>(getMovieTitlePosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 4. 감독 검색
     * /movie-director?director=
     */
    @ResponseBody
    @GetMapping("/movie-director")
    public BaseResponse<List<GetMovieDirectorPosterRes>> getMovieDirector(@PathVariable int userIdx,
                                                                          @PathVariable int profileIdx,
                                                                          @RequestParam String director){

        try{

            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMovieDirectorPosterRes> getMovieDirectorPosterRes = movieProvider.getMoviePosterByDirector(director);
            return new BaseResponse<>(getMovieDirectorPosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 5. 배우 검색
     * @param actor
     * /movie-actor?actor=
     */
    @ResponseBody
    @GetMapping("/movie-actor")
    public BaseResponse<List<GetMovieActorPosterRes>> getMovieActor(@PathVariable int userIdx,
                                                                    @PathVariable int profileIdx,
                                                                    @RequestParam String actor){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMovieActorPosterRes> getMovieActorPosterRes = movieProvider.getMoviePosterByActor(actor);
            return new BaseResponse<>(getMovieActorPosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    /**
     * 6. 각본가 검색
     * @param writer
     * /movie-writer?writer=
     */
    @ResponseBody
    @GetMapping("/movie-writer")
    public BaseResponse<List<GetMovieWriterPosterRes>> getMovieWriter(@PathVariable int userIdx,
                                                                      @PathVariable int profileIdx,
                                                                      @RequestParam String writer){

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMovieWriterPosterRes> getMovieWriterPosterRes = movieProvider.getMoviePosterByWriter(writer);
            return new BaseResponse<>(getMovieWriterPosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    /**
     * 7. 장르 검색
     * @param genre
     * /movie-genre?genre=
     */
    @ResponseBody
    @GetMapping("/movie-genre")
    public BaseResponse<List<GetMovieGenrePosterRes>> getMovieGenre(@PathVariable int userIdx,
                                                                    @PathVariable int profileIdx,
                                                                    @RequestParam String genre){

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetMovieGenrePosterRes> getMovieGenrePosterRes = movieProvider.getMoviePosterByGenre(genre);
            return new BaseResponse<>(getMovieGenrePosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }








}
