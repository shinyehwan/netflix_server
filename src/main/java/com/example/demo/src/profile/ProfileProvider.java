package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
