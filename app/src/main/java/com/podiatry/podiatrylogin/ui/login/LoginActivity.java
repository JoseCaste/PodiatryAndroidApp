package com.podiatry.podiatrylogin.ui.login;

import android.app.Activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.podiatry.podiatrylogin.R;
import com.podiatry.podiatrylogin.controlPanel.ControlPanelActivity;
import com.podiatry.podiatrylogin.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_login);

        binding.setLifecycleOwner(this);
        loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);
        //observe if progress bar will be showing
        Observer<Boolean> progress_observer= aBoolean -> {
            System.out.println("Observer progress--> "+aBoolean.booleanValue());

            if(aBoolean.booleanValue())
                binding.progressBar.setVisibility(View.VISIBLE);
            else
                binding.progressBar.setVisibility(View.INVISIBLE);
        };
        loginViewModel.getShow_progress().observe(this,progress_observer);

        Observer<Boolean> controlPanel= aBoolean -> {
            System.out.println("Observer controlPanel--> "+aBoolean.booleanValue());
            if(aBoolean){
                binding.progressBar.setVisibility(View.INVISIBLE);
                Intent controlPanelActivity= new Intent(this, ControlPanelActivity.class);
                startActivity(controlPanelActivity);
            }
        };
        loginViewModel.getStartControlPanel().observe(this, controlPanel);
    }
}