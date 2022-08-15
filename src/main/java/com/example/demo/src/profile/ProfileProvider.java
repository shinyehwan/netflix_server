package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.GetProfileAlarmRes;
import com.example.demo.src.profile.model.GetProfileBasketRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ProfileProvider {

    private final ProfileDao profileDao;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProfileProvider(ProfileDao profileDao, JwtService jwtService) {
        this.profileDao = profileDao;
        this.jwtService = jwtService;
    }


    // 해당 프로필이 이미 Profile Table에 존재하는지 확인
    public int checkProfile(String name) throws BaseException {
        try {
            return profileDao.checkProfile(name);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 프로필이 이미 Basket Table에 존재하는지 확인
    public int checkBasketMovie(int profileIdx, int movieId) throws BaseException {
        try {
            return profileDao.checkBasketMovie(profileIdx, movieId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkBasketSeries(int profileIdx, int seriesId) throws BaseException {
            try {
                return profileDao.checkBasketSeries(profileIdx, seriesId);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        }

        // 해당 프로필이 이미 Basket Table에 basket = 0 인지 존재하는지 확지
    public int checkBasketMovie2(int profileIdx, int movieId) throws BaseException {
        try {
            return profileDao.checkBasketMovie2(profileIdx, movieId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkBasketSeries2(int profileIdx, int seriesId) throws BaseException {
            try {
                return profileDao.checkBasketSeries2(profileIdx, seriesId);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        }


        // 해당 프로필이 이미 Basket Table에 존재하는지 확인
    public int checkAssessMovie(int profileIdx, int movieId) throws BaseException {
        try {
            return profileDao.checkAssessMovie(profileIdx, movieId);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkAssessSeries(int profileIdx, int seriesId) throws BaseException {
            try {
                return profileDao.checkAssessSeries(profileIdx, seriesId);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        }



    // 해당 profileIdx를 갖는 찜하기 목록 조회
    public List<GetProfileBasketRes> getBasket(int userIdx, int profileIdx) throws BaseException {
        try {
            List<GetProfileBasketRes> getProfileBasketRes = profileDao.getBasket(userIdx, profileIdx);
            return getProfileBasketRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 알람
    public List<GetProfileAlarmRes> getAlarm(int userIdx, int profileIdx) throws BaseException {
        try {
            List<GetProfileAlarmRes> getProfileAlarmRes = profileDao.getAlarm(userIdx, profileIdx);
            return getProfileAlarmRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
