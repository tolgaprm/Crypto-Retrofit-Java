package com.tolgapirim.cryptoretrofit.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolgapirim.cryptoretrofit.databinding.RecyclerRowBinding;
import com.tolgapirim.cryptoretrofit.model.CryptoModel;
import com.tolgapirim.cryptoretrofit.view.DetailActivity;

import java.util.List;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder> {

    List<CryptoModel> cryptoModelList;

    String [] colors = {"#7D70BA","#5FBB97","#B76D68","#798086","#CC3363","#CC3363"};

    public CryptoAdapter(List<CryptoModel> cryptoModelList) {
        this.cryptoModelList = cryptoModelList;
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CryptoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {

        holder.bind(cryptoModelList,position,colors);
    }


    @Override
    public int getItemCount() {
        return cryptoModelList.size();
    }

    public static class CryptoViewHolder extends RecyclerView.ViewHolder{

        RecyclerRowBinding binding;
        public CryptoViewHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(List<CryptoModel> cryptoModelList, Integer position, String[] colors ){
            binding.currency.setText(cryptoModelList.get(position).currency);
            binding.price.setText(cryptoModelList.get(position).price);

            itemView.setBackgroundColor(Color.parseColor(colors[position % 6] ));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra("currency",cryptoModelList.get(position).currency);
                    intent.putExtra("price",cryptoModelList.get(position).price);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
