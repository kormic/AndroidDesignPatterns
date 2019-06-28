package com.komic.androiddesignpatterns.model;

import com.komic.androiddesignpatterns.BuildConfig;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import io.reactivex.Single;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesService {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    private MoviesApi api;

    public MoviesService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new AuthInterceptor()).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        api = retrofit.create(MoviesApi.class);
    }

    public Single<MoviesResponse> getMoviesResponse() {
        return api.getMoviesResponse();
    }

    private static class AuthInterceptor implements Interceptor {

        private String moviesApiKey;

        public AuthInterceptor() {
            try {
                moviesApiKey = URLEncoder.encode(BuildConfig.MOVIES_API_KEY, StandardCharsets.UTF_8.toString());
            } catch (Exception e) {
                moviesApiKey = "dscsd";
            }
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl url = chain.request().url()
                    .newBuilder()
                    .addQueryParameter("api_key", moviesApiKey)
                    .build();
            Request request = chain.request().newBuilder().url(url).build();
            return chain.proceed(request);
        }
    }
}