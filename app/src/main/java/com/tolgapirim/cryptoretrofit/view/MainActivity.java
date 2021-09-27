package com.tolgapirim.cryptoretrofit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tolgapirim.cryptoretrofit.adapter.CryptoAdapter;
import com.tolgapirim.cryptoretrofit.databinding.ActivityMainBinding;
import com.tolgapirim.cryptoretrofit.model.CryptoModel;
import com.tolgapirim.cryptoretrofit.service.CryptoAPI;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    Retrofit retrofit;
    CompositeDisposable compositeDisposable;
   String  BASE_URL ="https://api.nomics.com/v1/";
    CryptoAdapter adapter;
    List<CryptoModel> cryptoModelList;

    //
    //https://api.nomics.com/v1/currencies/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
    }

    public void getData(){


        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData("d36e6480ea93d8a7dfd580e5b5ffdc1d54002a7d")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)

        );


    }

    private void handleResponse(List<CryptoModel> cryptoModels) {

        cryptoModelList =cryptoModels;
       binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
       adapter= new CryptoAdapter(cryptoModels);
       binding.recyclerView.setAdapter(adapter);

       binding.progressBar.setVisibility(View.GONE);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();



        compositeDisposable.clear();
    }
}