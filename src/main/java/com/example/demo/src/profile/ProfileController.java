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
@RequestMapping("/netflix/users/profile")
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
        //  @RequestBody란, 클라이언트가 전송하는 HTTP Request Body(우리는 JSON으로 통신하니, 이 경우 body는 JSON)를 자바 객체로 매핑시켜주는 어노테이션
        // TODO: name 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        // Validation 관련질문 -> .isEmpty()는 가능한데 왜 == NULL 은 불가한가?

        if (profileAddReq.getName().isEmpty()) {
            return new BaseResponse<>(POST_PROFILE_EMPTY_NAME);
        }
        try {
            ProfileAddRes profileAddRes = profileService.createProfile(userIdx, profileAddReq);
            return new BaseResponse<>(profileAddRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 해당 프로필에서 찜하기 추가 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("/basket/new")    // POST 방식의 요청을 매핑하기 위한 어노테 이션
    public BaseResponse<PostProfileBasketRes> createBasket(@RequestBody PostProfileBasketReq postProfileBasketReq) {
        //  @RequestBody란, 클라이언트가 전송하는 HTTP Request Body(우리는 JSON으로 통신하니, 이 경우 body는 JSON)를 자바 객체로 매핑시켜주는 어노테이션
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
     * /netflix/user/profile/basket
     */

    @GetMapping("/basket")
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
