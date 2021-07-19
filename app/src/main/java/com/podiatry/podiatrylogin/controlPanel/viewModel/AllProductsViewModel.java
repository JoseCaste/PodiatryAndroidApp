package com.podiatry.podiatrylogin.controlPanel.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.podiatry.podiatrylogin.data.model.Product;
import com.podiatry.podiatrylogin.services.ProductService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllProductsViewModel extends ViewModel {
    private MutableLiveData<List<Product>> allProducts= new MutableLiveData<>();
    private Retrofit retrofit;

    public LiveData<List<Product>> getAllProducts(){
        return allProducts;
    }
    public void getFromAPI(){

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.3.7:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> allProducts_= productService.getAllProduct();
        allProducts_.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                allProducts.setValue(response.body());
                response.body().stream().forEach(System.out::println);
                //allProducts.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                System.out.println(t.getMessage());
                System.out.println(t.toString());
            }
        });
    }
    
}
