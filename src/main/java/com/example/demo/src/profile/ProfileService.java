package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.*;
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
    public ProfileAddRes createProfile(int userIdx, ProfileAddReq profileAddReq) throws BaseException {
        // 중복 확인: 해당 이름 가진 유저가 있는지 확인합니다. 중복될 경우, 에러 메시지를 보냅니다.
        // 해당 유저 아이디에서만 중복이 불가능하고, 유저아이디가 다르면 중복을 허용하였다.
        if (profileProvider.checkProfile(profileAddReq.getName()) == 1) {
            throw new BaseException(POST_PROFILE_EXISTS_NAME);
        }
        try {
            int profileIdx= profileDao.createProfile(userIdx, profileAddReq);
//            return new ProfileAddRes(profileIdx);

            String jwt = jwtService.createJwt2(profileIdx);
            return new ProfileAddRes(profileIdx, jwt);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 찜하기 추가
    public PostProfileBasketRes createBasket(int userIdx, int profileIdx, PostProfileBasketReq postProfileBasketReq) throws BaseException {
//         중복 확인: 해당 영화 고유 아이디나, 시리즈 고유아이디를 가진 프로필이 있는지 확인합니다. 중복될 경우, 에러 메시지를 보냅니다.
        if (profileProvider.checkBasketMovie(profileIdx, postProfileBasketReq.getMovieId()) == 1) {
            throw new BaseException(POST_BASKET_EXISTS_MOVIE);
        }

        if (profileProvider.checkBasketSeries(profileIdx, postProfileBasketReq.getSeriesId()) == 1) {
            throw new BaseException(POST_BASKET_EXISTS_SERIES);
        }

        try {
            int basketIdx = profileDao.createBasket(userIdx, profileIdx, postProfileBasketReq);
            return new PostProfileBasketRes(basketIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 좋아요 추가
    public PostProfileAssessRes createAssess(int userIdx, int profileIdx, PostProfileAssessReq postProfileAssessReq) throws BaseException {
//         중복 확인: 해당 영화 고유 아이디나, 시리즈 고유아이디를 가진 평가가 있는지 확인합니다. 중복될 경우, 에러 메시지를 보냅니다.
        if (profileProvider.checkAssessMovie(profileIdx, postProfileAssessReq.getMovieId()) == 1) {
            throw new BaseException(POST_ACCESS_EXISTS_MOVIE);
        }

        if (profileProvider.checkAssessSeries(profileIdx, postProfileAssessReq.getSeriesId()) == 1) {
            throw new BaseException(POST_ACCESS_EXISTS_SERIES);
        }

        try {
            int assessmentIdx = profileDao.createAssess(userIdx, profileIdx, postProfileAssessReq);
            return new PostProfileAssessRes(assessmentIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
