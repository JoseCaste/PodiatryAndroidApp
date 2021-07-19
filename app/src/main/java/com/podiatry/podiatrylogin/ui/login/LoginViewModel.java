package com.podiatry.podiatrylogin.ui.login;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.database.Observable;
import android.media.MediaDataSource;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.podiatry.podiatrylogin.data.model.User;
import com.podiatry.podiatrylogin.services.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginViewModel extends ViewModel {
    public String username="jose";
    public String password="12345";
    public String alertUsername;
    private UserService userService;
    private Retrofit retrofit;
    private MutableLiveData<Boolean> show_progress= new MutableLiveData<>();
    private MutableLiveData<Boolean> startControlPanel= new MutableLiveData<>();



    public void login() {
        if(username!=null){
            User user = new User(username, md5(password));
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.3.7:8080/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            sigin(user);
        }else{
            System.out.println("No es valido");
            alertUsername="El nombre de usuario no puede estar vacío";
        }

    }
    public LiveData<Boolean> getShow_progress(){
        return show_progress;
    }
    public LiveData<Boolean> getStartControlPanel(){
        return  startControlPanel;
    }

    private void sigin(User user) {
            userService=retrofit.create(UserService.class);
            show_progress.setValue(true);
            Call<User> user_signin= userService.signin(user);
            user_signin.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.body()!=null){
                        System.out.println("Rest-->"+ response.body().toString());
                        startControlPanel.setValue(true);
                        show_progress.setValue(false);
                    }else {
                        System.out.println("INVISIBLE");
                        show_progress.setValue(false);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println(t.getMessage());
                    System.out.println(t.getStackTrace());
                    show_progress.setValue(false);
                    startControlPanel.setValue(true);

                }
            });



    }
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}