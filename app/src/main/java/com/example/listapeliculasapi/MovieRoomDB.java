package com.example.listapeliculasapi;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class MovieRoomDB extends RoomDatabase {

    public abstract MovieDao movieDao();
    private static MovieRoomDB INSTANCE;

    static MovieRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDB.class, "movie_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                }
            };

    /**
     * Populate the database in the background.
     */
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final WordDao mDao;
//        String[] words = {"dolphin", "crocodile", "cobra"};
//
//        PopulateDbAsync(WordRoomDB db) {
//            mDao = db.wordDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            // If we have no words, then create the initial list of words
//            if (mDao.getAnyWord().length < 1) {
//                for (int i = 0; i <= words.length - 1; i++) {
//                    Word word = new Word(words[i]);
//                    mDao.insert(word);
//                }
//            }
//            return null;
//        }
//    }
}
