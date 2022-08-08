package com.example.demo.src.series;

import com.example.demo.config.BaseException;
import com.example.demo.src.series.model.*;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class SeriesProvider {

    private final SeriesDao seriesDao;
    private final JwtService jwtService;

    @Autowired
    public SeriesProvider(SeriesDao seriesDao, JwtService jwtService) {
        this.seriesDao = seriesDao;
        this.jwtService = jwtService;
    }

    // 영화 상세 정보 조회
    public List<GetSeriesInfoRes> getSeries(String posterUrl) throws BaseException {
        try {
            List<GetSeriesInfoRes> getSeriesInfoRes = seriesDao.getSeries(posterUrl);
            return getSeriesInfoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 영화 포스터 조회
    public List<GetSeriesPosterUrlRes> getSeriesPoster() throws BaseException {
        try {
            List<GetSeriesPosterUrlRes> getSeriesPosterUrlRes = seriesDao.getSeriesPoster();
            return getSeriesPosterUrlRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 영화 이름으로 조회
    public List<GetSeriesInfoRes> getSeriesInfoTotal() throws BaseException {
        try {
            List<GetSeriesInfoRes> getSeriesInfoRes = seriesDao.getSeriesInfoTotal();
            return getSeriesInfoRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 영화 포스터 제목으로 조회
    public List<GetSeriesTitlePosterRes> getSeriesPosterByTitle(String title) throws BaseException {
                try {
                    List<GetSeriesTitlePosterRes> getSeriesTitlePosterRes = seriesDao.getSeriesPosterByTitle(title);
                    return getSeriesTitlePosterRes;
                } catch (Exception exception) {
                    throw new BaseException(DATABASE_ERROR);
                }
    }

    // 크리에이터 포스터 감독으로 조회
    public List<GetSeriesCreatorPosterRes> getSeriesPosterByCreator(String creator) throws BaseException {
        try {
            List<GetSeriesCreatorPosterRes> getSeriesCreatorPosterRes = seriesDao.getSeriesPosterByCreator(creator);
            return getSeriesCreatorPosterRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 영화 포스터 배우로 조회
    public List<GetSeriesActorPosterRes> getSeriesPosterByActor(String actor) throws BaseException {
        try {
            List<GetSeriesActorPosterRes> getSeriesActorPosterRes = seriesDao.getSeriesPosterByActor(actor);
            return getSeriesActorPosterRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 영화 포스터 장르로 조회
    public List<GetSeriesGenrePosterRes> getSeriesPosterByGenre(String genre) throws BaseException {
        try {
            List<GetSeriesGenrePosterRes> getSeriesGenrePosterRes = seriesDao.getSeriesPosterByGenre(genre);
            return getSeriesGenrePosterRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSeriesSeasonRes> getSeason(int seriesId) throws BaseException {
        try {
            List<GetSeriesSeasonRes> getSeriesSeasonRes = seriesDao.getSeason(seriesId);
            return getSeriesSeasonRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetSeriesEpisodeRes> getEpisode(int seriesId, int season) throws BaseException {
        try {
            List<GetSeriesEpisodeRes> getSeriesEpisodeRes = seriesDao.getEpisode(seriesId, season);
            return getSeriesEpisodeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
