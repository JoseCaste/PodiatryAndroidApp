package com.podiatry.podiatrylogin.services;

import com.podiatry.podiatrylogin.data.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("/allProducts")
    Call<List<Product>> getAllProduct();
}
