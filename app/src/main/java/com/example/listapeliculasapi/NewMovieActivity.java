package com.example.listapeliculasapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class NewMovieActivity extends AppCompatActivity {

    private MovieViewModel mMovieViewModel;

    private static final String MOVIE_BASE_URL =
            "https://imdb-api.com/API/Search/k_u5036s4z/";

    private EditText mEditWordView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_movie);
        mEditWordView = findViewById(R.id.edit_movie);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                submit(); // ejecuta la query, pinta en segunda pantalla
            }
        });



    }

    private void pintar(ArrayList<Movie> movies) {
        RecyclerView recyclerView2 = findViewById(R.id.recyclerview2);

        final MovieListAdapterSecond adapter = new MovieListAdapterSecond(this);
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        adapter.setMovies(movies);


        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Movie myMovie = adapter.getMovieAtPosition(position);
                        Toast.makeText(NewMovieActivity.this, "Inserting " +
                                myMovie.getMovieTitle(), Toast.LENGTH_LONG).show();

                        // insert movie on swipe
                        enviar(myMovie.getMovieTitle(), myMovie.getMovieGenre(), myMovie.getMovieUrl());
                    }
                });

        helper.attachToRecyclerView(recyclerView2);
        }



    private void enviar(String title, String desc, String urlMovie){

        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            replyIntent.putExtra("EXTRA_REPLY",         title);
            replyIntent.putExtra("EXTRA_REPLY2",         desc);
            replyIntent.putExtra("EXTRA_REPLY3",         urlMovie);


            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }




    private void displayToast(String string) {
        Toast.makeText(this,string, Toast.LENGTH_LONG).show();
    }

    private void submit(){
        ArrayList<String> array2 = new ArrayList<>();

        Intent replyIntent = new Intent();
        if (TextUtils.isEmpty(mEditWordView.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            String queryString = mEditWordView.getText().toString();


            RequestQueue queue = Volley.newRequestQueue(this);

            Uri builtURI = Uri.parse(MOVIE_BASE_URL+queryString).buildUpon()
                    .build();
            String url = builtURI.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, (String) null, new
                            Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    String title = null;
                                    String desc = null;
                                    String img = null;

                                   ArrayList<Movie> moviesList = new ArrayList<>();



                                    try {
                                        JSONArray itemsArray = response.getJSONArray("results");
                                        int i = 0;



                                        while (i < itemsArray.length() ) {
                                            // Get the current item information.
                                            JSONObject movie = itemsArray.getJSONObject(i);
                                            // Try to get the author and title from the current item,
                                            // catch if either field is empty and move on.

                                            try {
                                                title = movie.getString("title");
                                                img = movie.getString("image");
                                                desc = movie.getString("description");

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            // Move to the next item.

                                            Movie peli = new Movie(title, desc, img);
//                                            System.out.println("FIRST" +peli.getMovieTitle() + peli.getMovieGenre());
                                            moviesList.add(peli);

                                           System.out.println("SECOND "+moviesList.get(i).getMovieTitle());
                                            i++;

                                        }

                                        if (title != null && desc != null && img != null) {
                                              pintar(moviesList);
//                                            enviar(title, desc, img);
                                        } else {
                                            displayToast("error2");
                                        }


                                    } catch (Exception e) {

                                        displayToast("error3");
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            displayToast("error1");
                        }
                    });
            queue.add(jsonObjectRequest);

        }



    }





}