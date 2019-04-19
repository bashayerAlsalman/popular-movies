package net.bashayer.popularmovies.data.model;

import java.io.Serializable;
import java.util.Objects;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class MovieModel implements Serializable {

    @PrimaryKey
    private Integer id;
    private String title;
    private String releaseDate;
    private String overView;
    private Double voteAverage;
    private String posterPath;


    public MovieModel() {
    }

    public MovieModel(Integer id, Double voteAverage, String posterPath, String title, String releaseDate, String goverView) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieModel that = (MovieModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}