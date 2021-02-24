package com.example.listapeliculasapi;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "movie_table")
public class Movie {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String movieTitle;
    @NonNull
    @ColumnInfo(name = "genre")
    private String movieGenre;
    @NonNull
    @ColumnInfo(name = "url")
    private String movieUrl;

    public Movie(String movieTitle, String movieGenre, String movieUrl) {
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.movieUrl = movieUrl;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }


    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }
}
