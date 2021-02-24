package com.example.listapeliculasapi;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepo {

    private MovieDao movieDao;
    private LiveData<List<Movie>> mAllMovies;

    MovieRepo(Application application) {
        MovieRoomDB db = MovieRoomDB.getDatabase(application);
        movieDao = db.movieDao();
        mAllMovies = movieDao.getAllMovies();
    }

    LiveData<List<Movie>> getAllWords() {
        return mAllMovies;
    }

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(movieDao).execute();
    }

    public void deleteMovie(Movie movie)  {
        new deleteWordAsyncTask(movieDao).execute(movie);
    }

    public void insert (Movie movie) {
        new insertAsyncTask(movieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MovieDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao mAsyncTaskDao;

        deleteWordAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
}

