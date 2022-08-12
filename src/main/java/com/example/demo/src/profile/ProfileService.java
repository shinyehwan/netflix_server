package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.PostProfileBasketReq;
import com.example.demo.src.profile.model.PostProfileBasketRes;
import com.example.demo.src.profile.model.ProfileAddReq;
import com.example.demo.src.profile.model.ProfileAddRes;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Slf4j
@Service
public class ProfileService {

    private final ProfileDao profileDao;
    private final ProfileProvider profileProvider;
    private final JwtService jwtService;

    @Autowired
    public ProfileService(ProfileDao profileDao, ProfileProvider profileProvider, JwtService jwtService) {
        this.profileDao = profileDao;
        this.profileProvider = profileProvider;
        this.jwtService = jwtService;
    }


    // 프로필 추가(POST)
    public ProfileAddRes createProfile(ProfileAddReq profileAddReq) throws BaseException {
        // 중복 확인: 해당 이름 가진 유저가 있는지 확인합니다. 중복될 경우, 에러 메시지를 보냅니다.
        // 해당 유저 아이디에서만 중복이 불가능하고, 유저아이디가 다르면 중복을 허용하였다.
        if (profileProvider.checkProfile(profileAddReq.getUserIdx(), profileAddReq.getName()) == 1) {
            throw new BaseException(POST_PROFILE_EXISTS_NAME);
        }
        try {
            int profileIdx= profileDao.createProfile(profileAddReq);
            return new ProfileAddRes(profileIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 찜하기 추가
    public PostProfileBasketRes createBasket(PostProfileBasketReq postProfileBasketReq) throws BaseException {
        // 중복 확인: 해당 이름 가진 유저가 있는지 확인합니다. 중복될 경우, 에러 메시지를 보냅니다.
//        if (profileProvider.checkProfile(postProfileBasketReq.getName()) == 1) {
//            throw new BaseException(POST_PROFILE_EXISTS_NAME);
//        }

        try {
            int basketIdx = profileDao.createBasket(postProfileBasketReq);
            return new PostProfileBasketRes(basketIdx);

//  *********** 해당 부분은 7주차 수업 후 주석해제하서 대체해서 사용해주세요! ***********
//            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostUserRes(jwt,userIdx);
//  *********************************************************************
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
