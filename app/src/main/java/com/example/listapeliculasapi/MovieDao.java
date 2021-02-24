package com.example.listapeliculasapi;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * from movie_table LIMIT 1")
    Movie[] getAnyMovie();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movie);

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Query("SELECT * from movie_table")
    LiveData<List<Movie>> getAllMovies();

    @Delete
    void deleteWord(Movie movie);

}
