package net.bashayer.popularmovies.network.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private Integer id;
    private Double voteAverage;
    private String posterPath;
    private String title;
    private String releaseDate;
    private String overView;

    public Movie(Integer id, Double voteAverage, String posterPath, String title, String releaseDate, String goverView) {
        this.id = id;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overView = goverView;
    }

    public String getOverView() {
        return overView;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}