package com.example.demo.src.series;

import com.example.demo.src.series.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SeriesDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 모든 시리즈 정보 조회
    public List<GetSeriesInfoRes> getSeriesInfoTotal() {
        String getSeriesByTitleQuery = "select Series.id, title, summary, releaseYear, runtime, filmUrl, previewUrl, filmRating, resolution, posterUrl, isOriginal, Season.id, Season.episodeId from Series, Season";
        return this.jdbcTemplate.query(getSeriesByTitleQuery,
                (rs, rowNum) -> new GetSeriesInfoRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("summary"),
                        rs.getString("releaseYear"),
                        rs.getInt("runtime"),
                        rs.getString("filmUrl"),
                        rs.getString("previewUrl"),
                        rs.getInt("filmRating"),
                        rs.getString("resolution"),
                        rs.getString("posterUrl"),
                        rs.getInt("isOriginal"),
                        rs.getInt("id"),
                        rs.getInt("episodeId")
                )
        ); // 해당 posterUrl을 갖는 모든 Series 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // 해당 posterUrl 갖는 시리즈들의 정보 조회
    public List<GetSeriesInfoRes> getSeries(String posterUrl){
        String getSeriesQuery = "select Series.id, title, summary, releaseYear, runtime, filmUrl, previewUrl, filmRating, resolution, posterUrl, isOriginal, Season.id, Season.episodeId\n" +
                "from Series, Season\n" +
                "where Series.id = Season.seriesId and posterUrl = ?";
        String getSeriesByTitleParams = posterUrl;
        return this.jdbcTemplate.query(getSeriesQuery,
                (rs, rowNum) -> new GetSeriesInfoRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("summary"),
                        rs.getString("releaseYear"),
                        rs.getInt("runtime"),
                        rs.getString("filmUrl"),
                        rs.getString("previewUrl"),
                        rs.getInt("filmRating"),
                        rs.getString("resolution"),
                        rs.getString("posterUrl"),
                        rs.getInt("isOriginal"),
                        rs.getInt("id"),
                        rs.getInt("episodeId")
                        ), getSeriesByTitleParams  // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)

    }
    // 모든 영화 포스터 조회
    public List<GetSeriesPosterUrlRes> getSeriesPoster(){
        String getSeriesPosterQuery = "select id, posterUrl from Series";
        return this.jdbcTemplate.query(getSeriesPosterQuery,
                (rs, rowNum) -> new GetSeriesPosterUrlRes(
                        rs.getInt("id"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)

    }

    // 해당 title을 갖는 시리즈들의 포스터 조회
    public List<GetSeriesTitlePosterRes> getSeriesPosterByTitle(String title){
        String getSeriesPosterByTitleQuery = "select title, posterUrl from Series where title = ?";
        String getSeriesPosterByTitleParams = title;
        return this.jdbcTemplate.query(getSeriesPosterByTitleQuery,
                (rs, rowNum) -> new GetSeriesTitlePosterRes(
                        rs.getString("title"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        , getSeriesPosterByTitleParams); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환

    }


     // 해당 배우가 참여한 시리즈들의 포스터 조회
    public List<GetSeriesActorPosterRes> getSeriesPosterByActor(String actor){
        String getSeriesPosterByActorQuery = "select Actor.name, Series.posterUrl from Series, ParticipateActor,Actor\n" +
                "where ParticipateActor.seriesId = Series.id and ParticipateActor.actorId = Actor.id and Actor.name = ?";
        String getSeriesPosterByActorDirectorParams = actor;
        return this.jdbcTemplate.query(getSeriesPosterByActorQuery,
                (rs, rowNum) -> new GetSeriesActorPosterRes(
                        rs.getString("name"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        , getSeriesPosterByActorDirectorParams); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환

    }

    // 해당 크리에이터가 참여한 영화들의 포스터 조회
    public List<GetSeriesCreatorPosterRes> getSeriesPosterByCreator(String creator){
        String getSeriesPosterByCreatorQuery = "select Creator.name, Series.posterUrl from Series, ParticipateCreator, Creator where ParticipateCreator.seriesId = Series.id and ParticipateCreator.creatorId = Creator.id and Creator.name = ?";
        String getSeriesPosterByCreatorParams = creator;
        return this.jdbcTemplate.query(getSeriesPosterByCreatorQuery,
                (rs, rowNum) -> new GetSeriesCreatorPosterRes(
                        rs.getString("name"),
                        rs.getString("posterUrl")
                ), getSeriesPosterByCreatorParams);
    }



    // 해당 장르를 갖는 시리즈들의 포스터 조회
    public List<GetSeriesGenrePosterRes> getSeriesPosterByGenre(String genre){
        String getSeriesPosterByGenreQuery = "select Genre.genre, Series.posterUrl from Series, ContactGenre, Genre " +
                "where ContactGenre.seriesId = Series.id and ContactGenre.genreId = Genre.id and Genre.genre = ?";
        String getSeriesPosterByGenreParams = genre;
        return this.jdbcTemplate.query(getSeriesPosterByGenreQuery,
                (rs, rowNum) -> new GetSeriesGenrePosterRes(
                        rs.getString("genre"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        , getSeriesPosterByGenreParams); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환

    }

    // 해당 시리즈의 모든 시즌을 조회
    public List<GetSeriesSeasonRes> getSeason(int seriesId) {
        String sql = "select seriesId, Season.season\n" +
                "from Season\n" +
                "where seriesId = ?";
        int param = seriesId;
        return this.jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetSeriesSeasonRes(
                        rs.getInt("seriesId"),
                        rs.getInt("season")),
                param);
    }

    public List<GetSeriesEpisodeRes> getEpisode(int seriesId, int season) {
            String sql = "select Season.seriesId, Season.season, Episode.name, Episode.episodeImgUrl,Episode.episodeRuntime,Episode.episodeSummary\n" +
                    "from Season, Episode\n" +
                    "where Season.episodeId = Episode.id and Season.seriesId = ? and Season.season = ?";
            int param1 = seriesId;
            int param2 = season;
            return this.jdbcTemplate.query(sql,
                    (rs, rowNum) -> new GetSeriesEpisodeRes(
                            rs.getInt("seriesId"),
                            rs.getInt("season"),
                            rs.getString("name"),
                            rs.getString("episodeImgUrl"),
                            rs.getInt("episodeRuntime"),
                            rs.getString("episodeSummary")),
                    param1, param2);
        }
    public List<GetSeriesDetailAll> getDetail(int seriesId) {
                String sql = "select Actor.name as actorName, Creator.name as creatorName, Series.filmRating, Genre.genre, FeatureSeries.seriesFeature\n" +
                        "from Actor, Series, ParticipateActor, Creator, ParticipateCreator, Genre, ContactGenre, ContactFeatureSeries, FeatureSeries\n" +
                        "where Series.id = ParticipateActor.seriesId\n" +
                        "  and ParticipateActor.actorId = Actor.id\n" +
                        "  and Series.id = ParticipateCreator.seriesId\n" +
                        "  and ParticipateCreator.creatorId = Creator.id\n" +
                        "  and Series.id = ContactGenre.seriesId\n" +
                        "  and ContactGenre.genreId = Genre.id\n" +
                        "  and Series.id = ContactFeatureSeries.seriesId\n" +
                        "  and ContactFeatureSeries.featureSeriesId = FeatureSeries.id\n" +
                        "   and Series.id = ?";
                int param = seriesId;

                return this.jdbcTemplate.query(sql,
                        (rs, rowNum) -> new GetSeriesDetailAll(
                                rs.getString("actorName"),
                                rs.getString("creatorName"),
                                rs.getInt("filmRating"),
                                rs.getString("genre"),
                                rs.getString("seriesFeature")
                                ), param
                        );
            }






}
