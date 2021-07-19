package com.podiatry.podiatrylogin.services;

import com.podiatry.podiatrylogin.data.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/signin")
    Call<User> signin(@Body User user);
}
