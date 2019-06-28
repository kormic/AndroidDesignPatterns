package com.komic.androiddesignpatterns.mvc;

import android.util.Log;

import com.komic.androiddesignpatterns.model.Movie;
import com.komic.androiddesignpatterns.model.MoviesResponse;
import com.komic.androiddesignpatterns.model.MoviesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviesController {
    private MVCActivity view;
    private MoviesService moviesService;

    public MoviesController(MVCActivity view) {
        this.view =  view;
        this.moviesService = new MoviesService();
        fetchTopRatedMovies();
    }

    private void fetchTopRatedMovies() {
        moviesService.getMoviesResponse()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<MoviesResponse>() {
                    @Override
                    public void onSuccess(MoviesResponse value) {
                        List<String> movieNames = new ArrayList<>();
                        for(Movie movie: value.movies) {
                            movieNames.add(movie.title);
                        }
                        view.setValues(movieNames);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError();
                    }
                });
    }

    public void onRefresh() {
        fetchTopRatedMovies();
    }
}
