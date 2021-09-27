package com.tolgapirim.cryptoretrofit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tolgapirim.cryptoretrofit.databinding.ActivityDetailBinding;
import com.tolgapirim.cryptoretrofit.model.CryptoDetailModel;
import com.tolgapirim.cryptoretrofit.model.CryptoModel;
import com.tolgapirim.cryptoretrofit.service.CryptoAPI;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    Retrofit retrofit;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String  BASE_URL ="https://api.nomics.com/v1/";
    String ids;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent= getIntent();
        ids = intent.getStringExtra("currency");
        price = intent.getStringExtra("price");

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


        compositeDisposable.add(cryptoAPI.getDetail("d36e6480ea93d8a7dfd580e5b5ffdc1d54002a7d",ids,"id,name,logo_url")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleResponse));

    }

    private void handleResponse(List<CryptoDetailModel> cryptoDetailModel) {


        if (cryptoDetailModel.get(0).logo_url.equals("")){
            binding.cardView.setVisibility(View.INVISIBLE);
            binding.textDes.setVisibility(View.VISIBLE);
        }else{
            binding.currencyId.setText(cryptoDetailModel.get(0).id);
        }


        binding.name.setText(cryptoDetailModel.get(0).name);
        binding.price.setText(price);

        Glide.with(this).load(cryptoDetailModel.get(0).logo_url).into(binding.imageView);

    }




}