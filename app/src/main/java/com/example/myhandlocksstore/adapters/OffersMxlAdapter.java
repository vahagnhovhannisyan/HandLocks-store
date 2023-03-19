package com.example.myhandlocksstore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.activities.DetailedForOffersActivity;
import com.example.myhandlocksstore.models.OffersModel;

import java.util.List;

public class OffersMxlAdapter extends RecyclerView.Adapter<OffersMxlAdapter.ViewHolder> {
    Context context;
    List<OffersModel> list;

    public OffersMxlAdapter(Context context, List<OffersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int a = position;
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.oldPrice.setText(list.get(position).getOldPrice() + "֏");
        holder.newPrice.setText(list.get(position).getNewPrice() + "֏");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedForOffersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("detail", list.get(a));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name, description, oldPrice, newPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.offer_img);
            name = itemView.findViewById(R.id.offer_name);
            description = itemView.findViewById(R.id.offer_des);
            oldPrice = itemView.findViewById(R.id.offer_old_price);
            newPrice = itemView.findViewById(R.id.offer_new_price);
        }
    }
}

