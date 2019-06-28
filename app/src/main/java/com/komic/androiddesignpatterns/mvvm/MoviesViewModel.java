package com.komic.androiddesignpatterns.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.komic.androiddesignpatterns.model.Movie;
import com.komic.androiddesignpatterns.model.MoviesResponse;
import com.komic.androiddesignpatterns.model.MoviesService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends ViewModel {

    private final MutableLiveData<List<String>> movies = new MutableLiveData<>();
    private final MutableLiveData<Boolean> movieError = new MutableLiveData<>();
    private MoviesService moviesService;

    public MoviesViewModel() {
        moviesService = new MoviesService();
        fetchTopRatedMovies();
    }

    public LiveData<List<String>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getMovieError() {
        return movieError;
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
                        movies.setValue(movieNames);
                        movieError.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        movieError.setValue(true);
                    }
                });
    }

    public void onRefresh() {
        fetchTopRatedMovies();
    }
}
