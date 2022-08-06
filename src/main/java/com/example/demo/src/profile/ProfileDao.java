package com.example.demo.src.profile;

import com.example.demo.src.profile.model.ProfileAddReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ProfileDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 프로필 추가
    public int createProfile(ProfileAddReq profileAddReq) {
        String createProfileQuery = "insert into Profile (profileImageIdx, userIdx, name) VALUES (?,?,?)"; // 실행될 동적 쿼리문
        Object[] createProfileParams = new Object[]{profileAddReq.getProfileImageIdx(), profileAddReq.getUserIdx(), profileAddReq.getName()}; // 동적 쿼리의 ?부분에 주입될 값
        this.jdbcTemplate.update(createProfileQuery, createProfileParams);

        String lastInserIdQuery = "select last_insert_id()"; // 가장 마지막에 삽입된(생성된) id값은 가져온다.
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class); // 해당 쿼리문의 결과 마지막으로 삽인된 유저의 userIdx번호를 반환한다.
    }

    // 프로필 확인
    public int checkProfile(String name) {
        String checkNameQuery = "select exists(select name from Profile where name = ?)";
        String checkNameParams = name; // 해당(확인할) 이메일 값
        return this.jdbcTemplate.queryForObject(checkNameQuery,
                int.class,
                checkNameParams); // checkNameQuery, checkNameParams 통해 가져온 값(int형)을 반환한다. -> 쿼리문의 결과(존재하지 않음(False,0),존재함(True, 1))를 int형(0,1)으로 반환됩니다.
    }
}
