package com.example.listapeliculasapi;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MovieListAdapterSecond extends RecyclerView.Adapter<MovieListAdapterSecond.WordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Movie> mMovies; // Cached copy of words

    MovieListAdapterSecond(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WordViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mMovies != null) {
            Movie current = mMovies.get(position);
            holder.movieTitleItemView.setText(current.getMovieTitle());
            holder.movieGenreItemView.setText(current.getMovieGenre());
            String url = mMovies.get(position).getMovieUrl();

            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .apply(new RequestOptions().override(450, 400))
                    .into(holder.movieUrlItemView);

        } else {
            // Covers the case of data not being ready yet.
            holder.movieTitleItemView.setText("No title");
            holder.movieGenreItemView.setText("No Genre");

        }
    }

    void setMovies(List<Movie> movies){
        mMovies = movies;
        notifyDataSetChanged();
    }

    public Movie getMovieAtPosition (int position) {
        return mMovies.get(position);
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMovies != null)
            return mMovies.size();
        else return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final ImageView movieUrlItemView;
        private final TextView movieTitleItemView;
        private final TextView movieGenreItemView;


        private WordViewHolder(View itemView) {
            super(itemView);
            movieTitleItemView = itemView.findViewById(R.id.textViewNombrePelicula);
            movieGenreItemView= itemView.findViewById(R.id.textViewGeneroPelicula);
            movieUrlItemView = itemView.findViewById(R.id.imageView);

        }
    }
}
