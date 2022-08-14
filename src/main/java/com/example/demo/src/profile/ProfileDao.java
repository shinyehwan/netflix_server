package com.example.demo.src.profile;

import com.example.demo.src.profile.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class ProfileDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 프로필 추가
    public int createProfile(int userIdx, ProfileAddReq profileAddReq) {

        String createProfileQuery = "insert into Profile (userId, profileImageId, name) VALUES (?, ?, ?)"; // 실행될 동적 쿼리문
        Object[] createProfileParams = new Object[]{userIdx, profileAddReq.getProfileImageId(), profileAddReq.getName()};
        this.jdbcTemplate.update(createProfileQuery, createProfileParams); // 동적 쿼리의 ?부분에 주입될 값
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값을 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 profileIdx번호를 반환한다.
    }

    // 프로필 확인
    public int checkProfile(String name) {
        String checkNameQuery = "select exists (select name from Profile where name = ?)";
        String checkNameParams2 = name; // 해당(확인할) 이름 값
        return this.jdbcTemplate.queryForObject(checkNameQuery,
                int.class,
                checkNameParams2); // checkNameQuery, checkNameParams 통해 가져온 값(int형)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 찜하기 확인 영화
    public int checkBasketMovie(int profileIdx, int movieId) {
        String checkNameQuery = "select exists (select movieId from BasketMovie where profileId = ? and movieId = ?)";
        int param1 = profileIdx;
        int param2 = movieId;
        return this.jdbcTemplate.queryForObject(checkNameQuery,
                int.class,
                param1, param2); // checkNameQuery, checkNameParams 통해 가져온 값(int형)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 찜하기 확인 시리즈
    public int checkBasketSeries(int profileIdx, int seriesId) {
            String checkNameQuery = "select exists (select seriesId from BasketSeries where profileId =? and seriesId = ?)";
            int param1 = profileIdx;
            int param2 = seriesId;
            return this.jdbcTemplate.queryForObject(checkNameQuery,
                    int.class,
                    param1, param2); // checkNameQuery, checkNameParams 통해 가져온 값(int형)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
        }



    // 평가 확인
    public int checkAssessMovie(int profileIdx, int movieId) {
        String checkNameQuery = "select exists (select movieId from Assessment where profileId = ? and movieId = ?)";
        int param1 = profileIdx;
        int param2 = movieId;
        return this.jdbcTemplate.queryForObject(checkNameQuery,
                int.class,
                param1, param2); // checkNameQuery, checkNameParams 통해 가져온 값(int형)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    public int checkAssessSeries(int profileIdx, int seriesId) {
            String checkNameQuery = "select exists (select seriesId from Assessment where profileId =? and seriesId = ?)";
            int param1 = profileIdx;
            int param2 = seriesId;
            return this.jdbcTemplate.queryForObject(checkNameQuery,
                    int.class,
                    param1, param2); // checkNameQuery, checkNameParams 통해 가져온 값(int형)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
        }

    // 찜하기 추가 영화
    public int createBasket(int userIdx, int profileIdx, PostProfileBasketMovieReq postProfileBasketMovieReq) {
        String createBasketQuery = "insert into BasketMovie (profileId, movieId, basket) VALUES (?, ?, ?)"; // 실행될 동적 쿼리문
        Object[] createBasketParams = new Object[]{profileIdx, postProfileBasketMovieReq.getMovieId(), postProfileBasketMovieReq.getBasket()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createBasketQuery, createBasketParams);
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽입된 찜하기의 Idx번호를 반환한다.

    }

    // 찜하기 추가 시리즈
    public int createBasket2(int userIdx, int profileIdx, PostProfileBasketSeriesReq postProfileBasketSeriesReq) {
        String createBasketQuery = "insert into BasketSeries (profileId, seriesId, basket) VALUES (?, ?, ?)"; // 실행될 동적 쿼리문
        Object[] createBasketParams = new Object[]{profileIdx, postProfileBasketSeriesReq.getSeriesId(), postProfileBasketSeriesReq.getBasket()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createBasketQuery, createBasketParams);
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽입된 찜하기의 Idx번호를 반환한다.

    }

    // 좋아요 추가
    public int createAssess(int userIdx, int profileIdx, PostProfileAssessReq postProfileAssessReq) {
        String query = "insert into Assessment(profileId, movieId, seriesId, assessment) VALUES (?,?,?,?)";
        Object[] Params = new Object[]{profileIdx, postProfileAssessReq.getMovieId(), postProfileAssessReq.getSeriesId(), postProfileAssessReq.getAssessment()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(query, Params);
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }


    // 해당유저의 프로필의 찜하기 조회
    public List<GetProfileBasketRes> getBasket(int userIdx, int profileIdx){
        String query = "select Profile.profileIdx, BasketMovie.movieId, BasketSeries.seriesId\n" +
                "    from Profile, BasketMovie, BasketSeries\n" +
                "        where BasketMovie.profileId = Profile.profileIdx and BasketSeries.profileId = Profile.profileIdx\n" +
                "        and BasketMovie.basket = 1 and BasketSeries.basket = 1\n" +
                "        and Profile.userId = ? and Profile.profileIdx = ?";
        int Param1 = userIdx;
        int Param2 = profileIdx;
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetProfileBasketRes(
                        rs.getInt("profileIdx"),
                        rs.getString("movieId"),
                        rs.getString("seriesId")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                Param1, Param2
                ); //
    }
    // 해당유저의 프로필의 찜하기 조회
        public List<GetProfileAlarmRes> getAlarm(int userIdx, int profileIdx){
            String query = "SELECT\n" +
                    "    Profile.userId,\n" +
                    "    profileId,\n" +
                    "    title,\n" +
                    "    content,\n" +
                    "    alarmUrl,\n" +
                    "       case\n" +
                    "           when timestampdiff(hour, Alarm.createdAt, CURRENT_TIMESTAMP) < 24\n" +
                    "               then concat(timestampdiff(hour, Alarm.createdAt, CURRENT_TIMESTAMP), '시간 전')\n" +
                    "           else date_format(Alarm.createdAt, '%m월 %d일')\n" +
                    "           end  AS \"알람 날짜\"\n" +
                    "FROM Alarm, Profile\n" +
                    "WHERE Alarm.profileId = Profile.profileIdx and Profile.userId = ? and  Alarm.profileId = ?\n" +
                    "ORDER BY Alarm.createdAt DESC;\n";
            int Param1 = userIdx;
            int Param2 = profileIdx;
            return this.jdbcTemplate.query(query,
                    (rs, rowNum) -> new GetProfileAlarmRes(
                            rs.getInt("userId"),
                            rs.getInt("profileId"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getString("alarmUrl")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
                    Param1, Param2
                    ); //
        }


}
