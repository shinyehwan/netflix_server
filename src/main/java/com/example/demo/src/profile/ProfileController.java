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
     *  프로필 아이디 생성 API
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
     * 해당 프로필에서 찜하기 추가 API
     * [POST] // 아직 validation 을 구현하지 못했음
     */
    @ResponseBody
    @PostMapping("/basket/new")
    public BaseResponse<PostProfileBasketRes> createBasket(@PathVariable int userIdx, @RequestBody PostProfileBasketReq postProfileBasketReq) {
        try {
            PostProfileBasketRes postProfileBasketRes = profileService.createBasket(postProfileBasketReq);
            return new BaseResponse<>(postProfileBasketRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 내가 찜한 목록 조회
     * [GET]
     *
     */

    @GetMapping("{profileIdx}/basket")
    public BaseResponse<List<GetProfileBasketRes>> getProfileBasket(@PathVariable int userIdx,
                                                                    @PathVariable int profileIdx){
        try {
            List<GetProfileBasketRes> getProfileBasketRes = profileProvider.getBasket(userIdx ,profileIdx);
            return new BaseResponse<>(getProfileBasketRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }

    }

    /**
     * 알람설정
     * [GET]
     * /netflix/users/:userIdx/profile/:profileIdx/alarm
     */

    @GetMapping("/{profileIdx}/alarm")
    public BaseResponse<List<GetProfileAlarmRes>> getProfileAlarm(@PathVariable int userIdx,
                                                                    @PathVariable int profileIdx){
        try {
            List<GetProfileAlarmRes> getProfileAlarmRes = profileProvider.getAlarm(userIdx ,profileIdx);
            return new BaseResponse<>(getProfileAlarmRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }

    }

}
