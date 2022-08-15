package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Slf4j
@RestController
@RequestMapping("/netflix/users/{userIdx}/profile")
public class ProfileController {

    @Autowired
    private final ProfileProvider profileProvider;
    @Autowired
    private final ProfileService profileService;
    @Autowired
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    public ProfileController(ProfileProvider profileProvider, ProfileService profileService, JwtService jwtService) {
        this.profileProvider = profileProvider;
        this.profileService = profileService;
        this.jwtService = jwtService;
    }

    /**
     *  2-1 프로필 아이디 생성 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("/new")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<ProfileAddRes> createProfile(@PathVariable int userIdx, @RequestBody ProfileAddReq profileAddReq) {
        // Validation 관련질문 -> .isEmpty()는 가능한데 왜 == NULL 은 불가한가?

        if (profileAddReq.getName().isEmpty()) {
            return new BaseResponse<>(POST_PROFILE_EMPTY_NAME);
        }

        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            ProfileAddRes profileAddRes = profileService.createProfile(userIdx, profileAddReq);
            return new BaseResponse<>(profileAddRes);
            }
         catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 2-2 해당 프로필에서 찜하기 영화 추가 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("{profileIdx}/movie/basket")
    public BaseResponse<PostProfileBasketMovieRes> createBasket(@PathVariable int userIdx,
                                                                @PathVariable int profileIdx,
                                                                @RequestBody PostProfileBasketMovieReq postProfileBasketMovieReq) {
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

            PostProfileBasketMovieRes postProfileBasketMovieRes = profileService.createBasket(userIdx, profileIdx, postProfileBasketMovieReq);
            return new BaseResponse<>(postProfileBasketMovieRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 2-3 해당 프로필에서 찜하기 시리즈 추가 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("{profileIdx}/series/basket")
    public BaseResponse<PostProfileBasketSeriesRes> createBasket(@PathVariable int userIdx,
                                                                @PathVariable int profileIdx,
                                                                @RequestBody PostProfileBasketSeriesReq postProfileBasketSeriesReq) {
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

            PostProfileBasketSeriesRes postProfileBasketSeriesRes = profileService.createBasket2(userIdx, profileIdx, postProfileBasketSeriesReq);
            return new BaseResponse<>(postProfileBasketSeriesRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 2-4. 내가 찜한 목록 조회
     * [GET]
     *
     */
    @GetMapping("{profileIdx}/basket")
    public BaseResponse<List<GetProfileBasketRes>> getProfileBasket(@PathVariable int userIdx,
                                                                    @PathVariable int profileIdx){
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

            List<GetProfileBasketRes> getProfileBasketRes = profileProvider.getBasket(userIdx ,profileIdx);
            return new BaseResponse<>(getProfileBasketRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }

    }

    /**
     * 2-5 영화 찜하기 취소(변경) API
     * [PATCH] /movie/basket
     */
    @ResponseBody
    @PatchMapping("/{profileIdx}/movie/basket")
    public BaseResponse<String> modifyBasketMovie(@PathVariable int userIdx,
                                               @PathVariable int profileIdx,
                                               @RequestBody PatchBasketMovieReq patchBasketMovieReq) {
        try {
             //jwt에서 idx 추출.
             int userIdxByJwt = jwtService.getUserIdx();
             //userIdx와 접근한 유저가 같은지 확인
             if(userIdx != userIdxByJwt){
             return new BaseResponse<>(INVALID_USER_JWT);
             }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            profileService.modifyBasketMovie(profileIdx, patchBasketMovieReq);

            String result = "영화 찜하기가 취소 되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 2-6 시리즈 찜하기 취소(변경) API
     * [PATCH] /series/basket
     */
    @ResponseBody
    @PatchMapping("/{profileIdx}/series/basket")
    public BaseResponse<String> modifyBasketSeries(@PathVariable int userIdx,
                                               @PathVariable int profileIdx,
                                               @RequestBody PatchBasketSeriesReq patchBasketSeriesReq) {
        try {
             //jwt에서 idx 추출.
             int userIdxByJwt = jwtService.getUserIdx();
             //userIdx와 접근한 유저가 같은지 확인
             if(userIdx != userIdxByJwt){
             return new BaseResponse<>(INVALID_USER_JWT);
             }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
                 //같다면 유저네임 변경
            profileService.modifyBasketSeries(profileIdx, patchBasketSeriesReq);

            String result = "시리즈 찜하기가 취소되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 해당 컨텐츠 좋아요 영화 추가하기 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("{profileIdx}/movie/assess")
    public BaseResponse<PostMovieAssessRes> createBasket(@PathVariable int userIdx,
                                                         @PathVariable int profileIdx,
                                                         @RequestBody PostMovieAssessReq postMovieAssessReq) {
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

            PostMovieAssessRes postMovieAssessRes = profileService.createAssess(userIdx, profileIdx, postMovieAssessReq);
            return new BaseResponse<>(postMovieAssessRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 2 영화 좋아요 변경 API
     * [PATCH] /movie/assess
     */
    @ResponseBody
    @PatchMapping("/{profileIdx}/movie/assess")
    public BaseResponse<String> modifyAccessMovie(@PathVariable int userIdx,
                                                   @PathVariable int profileIdx,
                                                   @RequestBody PatchAssessMovieReq patchAssessMovieReq) {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int profileIdxByJwt = jwtService.getProfileIdx();
            //profileIdx와 접근한 유저가 같은지 확인
            if(profileIdx != profileIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            profileService.modifyAssessMovie(profileIdx, patchAssessMovieReq);

            String result = "시리즈 좋아요가 변경되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }






    /**
     * 알람설정
     * 2-11 [GET]
     * /netflix/users/:userIdx/profile/:profileIdx/alarm
     */

    @GetMapping("/{profileIdx}/alarm")
    public BaseResponse<List<GetProfileAlarmRes>> getProfileAlarm(@PathVariable int userIdx,
                                                                    @PathVariable int profileIdx){
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
            List<GetProfileAlarmRes> getProfileAlarmRes = profileProvider.getAlarm(userIdx ,profileIdx);
            return new BaseResponse<>(getProfileAlarmRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }

    }





}
