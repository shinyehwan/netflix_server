package com.example.demo.src.series;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.series.model.*;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/netflix/users/{userIdx}/profile/{profileIdx}/series")
@Slf4j
public class SeriesController {

    private final SeriesProvider seriesProvider;
    private final SeriesService seriesService;
    private final JwtService jwtService;

    @Autowired
    public SeriesController(SeriesProvider seriesProvider, SeriesService seriesService, JwtService jwtService)
    {
        this.seriesProvider = seriesProvider;
        this.seriesService = seriesService;
        this.jwtService = jwtService;
    }

    /**
     * 8. 메인 화면 단순 영화 포스터 조회
     * /series
     */

    @ResponseBody
    @GetMapping
    public BaseResponse<List<GetSeriesPosterUrlRes>> getSeriesPoster(@PathVariable int userIdx,
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
            List<GetSeriesPosterUrlRes> getSeriesPosterUrlRes = seriesProvider.getSeriesPoster();
            return new BaseResponse<>(getSeriesPosterUrlRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     *
     * 해당 posterUrl을 갖는 영화 정보 조회 API
     * [GET] /series/info?posterUrl=
     */

    @ResponseBody
    @GetMapping("/info")
    public BaseResponse<List<GetSeriesInfoRes>> getSeries(@PathVariable int userIdx,
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
            // query string인 posterUrl 있을 경우, 조건을 만족하는 유저정보들을 불러온다.
            List<GetSeriesInfoRes> getSeriesInfoRes = seriesProvider.getSeries(posterUrl);
            return new BaseResponse<>(getSeriesInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    @ResponseBody
    @GetMapping("/info/total")
    public BaseResponse<List<GetSeriesInfoTotalRes>> getSeriesTotal(@PathVariable int userIdx,
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

            List<GetSeriesInfoTotalRes> getSeriesInfoTotalRes = seriesProvider.getSeriesInfoTotal();
            return new BaseResponse<>(getSeriesInfoTotalRes);


        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 16. 시리즈 더보기 누를시 디테일한 정보
     * /series/info/detail
     */
    @ResponseBody
    @GetMapping("/{seriesId}/detail")
    public BaseResponse<List<GetSeriesDetailAll>> getSeriesDetail(@PathVariable int userIdx,
                                                                  @PathVariable int profileIdx,
                                                                  @PathVariable int seriesId) {
        try {
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
            List<GetSeriesDetailAll> getSeriesDetailAll = seriesProvider.getDetail(seriesId);
            return new BaseResponse<>(getSeriesDetailAll);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 10. 제목 검색
     * 해당 타이틀을 갖는 영화 포스터 조회
     * /Series-title?title=
     */

    @ResponseBody
    @GetMapping("/series-title")
    public BaseResponse<List<GetSeriesTitlePosterRes>> getSeriesTitle(@PathVariable int userIdx,
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
            List<GetSeriesTitlePosterRes> getSeriesTitlePosterRes = seriesProvider.getSeriesPosterByTitle(title);
            return new BaseResponse<>(getSeriesTitlePosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 11. 배우 검색
     * @param actor
     * /Series-actor?actor=
     */
    @ResponseBody
    @GetMapping("/series-actor")
    public BaseResponse<List<GetSeriesActorPosterRes>> getSeriesActor(@PathVariable int userIdx,
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
            List<GetSeriesActorPosterRes> getSeriesActorPosterRes = seriesProvider.getSeriesPosterByActor(actor);
            return new BaseResponse<>(getSeriesActorPosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    /**
     * 12. 크리에이터 검색
     * @param creator
     * /Series-creator?creator=
     */
    @ResponseBody
    @GetMapping("/series-creator")
    public BaseResponse<List<GetSeriesCreatorPosterRes>> getSeriesCreator(@PathVariable int userIdx,
                                                                          @PathVariable int profileIdx,
                                                                          @RequestParam String creator){

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
            List<GetSeriesCreatorPosterRes> getSeriesCreatorPosterRes = seriesProvider.getSeriesPosterByCreator(creator);
            return new BaseResponse<>(getSeriesCreatorPosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 13. 장르 검색
     * @param genre
     * /Series-genre?genre=
     */
    @ResponseBody
    @GetMapping("/series-genre")
    public BaseResponse<List<GetSeriesGenrePosterRes>> getSeriesGenre(@PathVariable int userIdx,
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
            List<GetSeriesGenrePosterRes> getSeriesGenrePosterRes = seriesProvider.getSeriesPosterByGenre(genre);
            return new BaseResponse<>(getSeriesGenrePosterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 14. 시리즈 관련 시즌 리스트 정렬
     *
     */

    @ResponseBody
    @GetMapping("/{seriesId}/season")
    public BaseResponse<List<GetSeriesSeasonRes>> getSeriesSeason(@PathVariable int userIdx,
                                                                  @PathVariable int profileIdx,
                                                                  @PathVariable int seriesId) {
        try {
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
            List<GetSeriesSeasonRes> getSeriesSeason = seriesProvider.getSeason(seriesId);
            return new BaseResponse<>(getSeriesSeason);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 15. 시리즈 관련 시즌에 따른 에피소드 리스트 정렬
     *
     */

    @ResponseBody
    @GetMapping("/{seriesId}/season/{season}/episode")
    public BaseResponse<List<GetSeriesEpisodeRes>> getSeriesEpisode(@PathVariable int userIdx,
                                                                    @PathVariable int profileIdx,
                                                                    @PathVariable int seriesId,
                                                                    @PathVariable int season) {
        try {
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
            List<GetSeriesEpisodeRes> getSeriesEpisode = seriesProvider.getEpisode(seriesId, season);
            return new BaseResponse<>(getSeriesEpisode);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
