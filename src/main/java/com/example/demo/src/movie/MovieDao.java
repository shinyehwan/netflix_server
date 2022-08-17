package com.example.demo.src.movie;

import com.example.demo.src.movie.model.*;
import com.example.demo.src.series.model.GetSeriesDetailAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MovieDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 모든 영화 정보 조회
    public List<GetMovieInfoRes> getMovieInfoTotal() {
        String getMovieByTitleQuery = "select * from Movie";
        return this.jdbcTemplate.query(getMovieByTitleQuery,
                (rs, rowNum) -> new GetMovieInfoRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("summary"),
                        rs.getString("releaseYear"),
                        rs.getInt("runtime"),
                        rs.getString("movieUrl"),
                        rs.getString("previewUrl"),
                        rs.getInt("filmRating"),
                        rs.getString("resolution"),
                        rs.getString("posterUrl"),
                        rs.getInt("isOriginal")
                )
        ); // 해당 posterUrl을 갖는 모든 Movie 정보를 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보, Params)의 결과 반환
    }

    // 해당 posterUrl 갖는 영화들의 정보 조회
    public List<GetMovieInfoRes> getMovie(String posterUrl){
        String getMovieQuery = "select * from Movie where posterUrl = ?";
        String getMovieByTitleParams = posterUrl;
        return this.jdbcTemplate.query(getMovieQuery,
                (rs, rowNum) -> new GetMovieInfoRes(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("summary"),
                        rs.getString("releaseYear"),
                        rs.getInt("runtime"),
                        rs.getString("movieUrl"),
                        rs.getString("previewUrl"),
                        rs.getInt("filmRating"),
                        rs.getString("resolution"),
                        rs.getString("posterUrl"),
                        rs.getInt("isOriginal")
                        ), getMovieByTitleParams  // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)

    }
    // 모든 영화 포스터 조회
    public List<GetMoviePosterUrlRes> getMoviePoster(){
        String getMoviePosterQuery = "select id, posterUrl from Movie";
        return this.jdbcTemplate.query(getMoviePosterQuery,
                (rs, rowNum) -> new GetMoviePosterUrlRes(
                        rs.getInt("id"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        ); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환(동적쿼리가 아니므로 Parmas부분이 없음)

    }

    // 해당 title을 갖는 영화들의 포스터 조회
    public List<GetMovieTitlePosterRes> getMoviePosterByTitle(String title){
        String getMoviePosterByTitleQuery = "select title, posterUrl from Movie where title = ?";
        String getMoviePosterByTitleParams = title;
        return this.jdbcTemplate.query(getMoviePosterByTitleQuery,
                (rs, rowNum) -> new GetMovieTitlePosterRes(
                        rs.getString("title"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        , getMoviePosterByTitleParams); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환

    }

    // 해당 감독을 갖는 영화들의 포스터 조회
    public List<GetMovieDirectorPosterRes> getMoviePosterByDirector(String director){
        String getMoviePosterByDirectorQuery = "select Director.name, Movie.posterUrl from Movie, ParticipateDirector,Director\n" +
                "where ParticipateDirector.movieId = Movie.id and  ParticipateDirector.directorId = Director.id and Director.name = ?";
        String getMoviePosterByDirectorParams = director;
        return this.jdbcTemplate.query(getMoviePosterByDirectorQuery,
                (rs, rowNum) -> new GetMovieDirectorPosterRes(
                        rs.getString("name"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        , getMoviePosterByDirectorParams); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환

    }

     // 해당 배우가 참여한 영화들의 포스터 조회
    public List<GetMovieActorPosterRes> getMoviePosterByActor(String actor){
        String getMoviePosterByActorQuery = "select Actor.name, Movie.posterUrl from Movie, ParticipateActor, Actor\n" +
                "where ParticipateActor.movieId = Movie.id and ParticipateActor.actorId = Actor.id and Actor.name = ?";
        String getMoviePosterByActorDirectorParams = actor;
        return this.jdbcTemplate.query(getMoviePosterByActorQuery,
                (rs, rowNum) -> new GetMovieActorPosterRes(
                        rs.getString("name"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        , getMoviePosterByActorDirectorParams); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환

    }

    // 해당 각본가가 참여한 영화들의 포스터 조회
    public List<GetMovieWriterPosterRes> getMoviePosterByWriter(String writer){
        String getMoviePosterByWriterQuery = "select Writer.name, Movie.posterUrl from Movie, ParticipateWriter, Writer " +
                "where ParticipateWriter.movieId = Movie.id and ParticipateWriter.writerId = Writer.id and Writer.name = ?";
        String getMoviePosterByWriterParams = writer;
        return this.jdbcTemplate.query(getMoviePosterByWriterQuery,
                (rs, rowNum) -> new GetMovieWriterPosterRes(
                        rs.getString("name"),
                        rs.getString("posterUrl")
                ), getMoviePosterByWriterParams);
    }



    // 해당 장르를 갖는 영화들의 포스터 조회
    public List<GetMovieGenrePosterRes> getMoviePosterByGenre(String genre){
        String getMoviePosterByGenreQuery = "select Genre.genre, Movie.posterUrl from Movie, ContactGenre, Genre " +
                "where ContactGenre.movieId = Movie.id and ContactGenre.genreId = Genre.id and Genre.genre = ?";
        String getMoviePosterByGenreParams = genre;
        return this.jdbcTemplate.query(getMoviePosterByGenreQuery,
                (rs, rowNum) -> new GetMovieGenrePosterRes(
                        rs.getString("genre"),
                        rs.getString("posterUrl")
                ) // RowMapper(위의 링크 참조): 원하는 결과값 형태로 받기
        , getMoviePosterByGenreParams); // 복수개의 회원정보들을 얻기 위해 jdbcTemplate 함수(Query, 객체 매핑 정보)의 결과 반환

    }

    public List<GetMovieDetailAll> getDetail(int movieId) {
        String sql = "select Actor.name as actorName, Director.name as directorName, Writer.name as writerName, Movie.filmRating, Genre.genre, FeatureMovie.movieFeature\n" +
                "                from Actor, Movie, ParticipateActor, Director, ParticipateDirector, Writer, ParticipateWriter, Genre, ContactGenre, ContactFeatureMovie, FeatureMovie\n" +
                "                where Movie.id = ParticipateActor.movieId\n" +
                "                  and ParticipateActor.actorId = Actor.id\n" +
                "                  and Movie.id = ParticipateDirector.movieId\n" +
                "                  and ParticipateDirector.directorId = Director.id\n" +
                "                  and Movie.id = ParticipateWriter.movieId\n" +
                "                  and ParticipateWriter.writerId = Writer.id\n" +
                "                  and Movie.id = ContactGenre.movieId\n" +
                "                  and ContactGenre.genreId = Genre.id\n" +
                "                  and Movie.id = ContactFeatureMovie.movieId\n" +
                "                  and ContactFeatureMovie.featureMovieId = FeatureMovie.id\n" +
                "                   and Movie.id = ?";
        int param = movieId;

        return this.jdbcTemplate.query(sql,
                (rs, rowNum) -> new GetMovieDetailAll(
                        rs.getString("actorName"),
                        rs.getString("directorName"),
                        rs.getString("writerName"),
                        rs.getInt("filmRating"),
                        rs.getString("genre"),
                        rs.getString("seriesFeature")
                ), param
        );
    }





}
