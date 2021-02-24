package com.example.listapeliculasapi;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepo mRepository;
    private LiveData<List<Movie>> mAllWords;

    public MovieViewModel (Application application) {
        super(application);
        mRepository = new MovieRepo(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Movie>> getAllWords() { return mAllWords; }
    public void insert(Movie movie) { mRepository.insert(movie); }
    public void deleteAll() {mRepository.deleteAll();}
    public void deleteMovie(Movie movie) {mRepository.deleteMovie(movie);}


}
