package com.komic.androiddesignpatterns.model;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("title")
    public String title;
}
