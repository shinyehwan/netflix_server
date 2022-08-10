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
        String checkNameParams = name; // 해당(확인할) 이름 값
        return this.jdbcTemplate.queryForObject(checkNameQuery,
                int.class,
                checkNameParams); // checkNameQuery, checkNameParams 통해 가져온 값(int형)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }

    // 찜하기 추가
    public int createBasket(PostProfileBasketReq postProfileBasketReq) {
        String createBasketQuery = "insert into Basket (profileId, movieId, seriesId) VALUES (?, ?, ?)"; // 실행될 동적 쿼리문
        Object[] createBasketParams = new Object[]{postProfileBasketReq.getProfileId(), postProfileBasketReq.getMovieId(), postProfileBasketReq.getSeriesId()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createBasketQuery, createBasketParams);
        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽입된 찜하기의 Idx번호를 반환한다.

    }

    // 해당유저의 프로필의 찜하기 조회
    public List<GetProfileBasketRes> getBasket(int userIdx, int profileIdx){
        String query = "select User.userIdx, Profile.profileIdx, Movie.posterUrl, Series.posterUrl\n" +
                "from User, Profile, Basket, Movie, Series\n" +
                "where User.userIdx = Profile.userId and Basket.profileId = Profile.profileIdx and Basket.movieId = Movie.id and Basket.seriesId = Series.id\n" +
                "and User.userIdx = ? and Profile.profileIdx = ?";
        int Param1 = userIdx;
        int Param2 = profileIdx;
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new GetProfileBasketRes(
                        rs.getInt("userIdx"),
                        rs.getInt("profileIdx"),
                        rs.getString("posterUrl"),
                        rs.getString("posterUrl")), // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
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
