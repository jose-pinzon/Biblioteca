package com.example.biblioteca.Inteface;
import com.example.biblioteca.Modelos.book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface retrofitInterface {
    @GET
    Call<List<book>> getBooks(@Url String url);
}
