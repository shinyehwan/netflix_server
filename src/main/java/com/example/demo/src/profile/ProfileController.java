package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.model.ProfileAddReq;
import com.example.demo.src.profile.model.ProfileAddRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/netflix/users")
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
     * 3. 프로필 아이디 생성 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("/{userIdx}/new")    // POST 방식의 요청을 매핑하기 위한 어노테이션
    public BaseResponse<ProfileAddRes> createProfile(@PathVariable("userIdx") int userIdx,
                                                  @RequestBody ProfileAddReq profileAddReq) {
        //  @RequestBody란, 클라이언트가 전송하는 HTTP Request Body(우리는 JSON으로 통신하니, 이 경우 body는 JSON)를 자바 객체로 매핑시켜주는 어노테이션
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if (profileAddReq.getName() == null) {
            return new BaseResponse<>(POST_PROFILE_EMPTY_EMAIL);
        }
        try {
            ProfileAddRes profileAddRes = profileService.createProfile(profileAddReq);
            return new BaseResponse<>(profileAddRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
