package com.example.demo.src.movie;

import com.example.demo.config.BaseException;
import com.example.demo.src.movie.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MovieProvider {

    private final MovieDao movieDao;
    private final JwtService jwtService;

    @Autowired
    public MovieProvider(MovieDao movieDao, JwtService jwtService) {
        this.movieDao = movieDao;
        this.jwtService = jwtService;
    }

    // 영화 상세 정보 조회
    public List<GetMovieInfoRes> getMovie(String posterUrl) throws BaseException {
        try {
            List<GetMovieInfoRes> getMovieInfoRes = movieDao.getMovie(posterUrl);
            return getMovieInfoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 영화 포스터 조회
    public List<GetMoviePosterUrlRes> getMoviePoster() throws BaseException {
        try {
            List<GetMoviePosterUrlRes> getMoviePosterUrlRes = movieDao.getMoviePoster();
            return getMoviePosterUrlRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 영화 이름으로 조회
    public List<GetMovieInfoRes> getMovieInfoTotal() throws BaseException {
        try {
            List<GetMovieInfoRes> getMovieInfoRes = movieDao.getMovieInfoTotal();
            return getMovieInfoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 영화 포스터 제목으로 조회
    public List<GetMovieTitlePosterRes> getMoviePosterByTitle(String title) throws BaseException {
                try {
                    List<GetMovieTitlePosterRes> getMovieTitlePosterRes = movieDao.getMoviePosterByTitle(title);
                    return getMovieTitlePosterRes;
                } catch (Exception exception) {
                    throw new BaseException(DATABASE_ERROR);
                }
    }

    // 영화 포스터 감독으로 조회
    public List<GetMovieDirectorPosterRes> getMoviePosterByDirector(String director) throws BaseException {
        try {
            List<GetMovieDirectorPosterRes> getMovieDirectorPosterRes = movieDao.getMoviePosterByDirector(director);
            return getMovieDirectorPosterRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 영화 포스터 배우로 조회
    public List<GetMovieActorPosterRes> getMoviePosterByActor(String actor) throws BaseException {
        try {
            List<GetMovieActorPosterRes> getMovieActorPosterRes = movieDao.getMoviePosterByActor(actor);
            return getMovieActorPosterRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 영화 포스터 각본가로 조회
    public List<GetMovieWriterPosterRes> getMoviePosterByWriter(String writer) throws BaseException {
        try {
            List<GetMovieWriterPosterRes> getMovieWriterPosterRes = movieDao.getMoviePosterByWriter(writer);
            return getMovieWriterPosterRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 영화 포스터 장르로 조회
    public List<GetMovieGenrePosterRes> getMoviePosterByGenre(String genre) throws BaseException {
        try {
            List<GetMovieGenrePosterRes> getMovieGenrePosterRes = movieDao.getMoviePosterByGenre(genre);
            return getMovieGenrePosterRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
