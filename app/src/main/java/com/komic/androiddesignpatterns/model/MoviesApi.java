package com.komic.androiddesignpatterns.model;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MoviesApi {
    @GET("movie/top_rated?language=en-US&page=1")
    Single<MoviesResponse> getMoviesResponse();
}
