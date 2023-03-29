package com.example.myhandlocksstore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.models.MyCartModel;
import com.example.myhandlocksstore.models.MyOrdersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    Context context;
    List<MyOrdersModel> myOrdersModelList;
    int totalPrice = 0;
    FirebaseAuth auth;
    FirebaseFirestore firestore;


    public MyOrdersAdapter(Context context, List<MyOrdersModel> myOrdersModelList) {
        this.context = context;
        this.myOrdersModelList = myOrdersModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int a = position;
        holder.name.setText(myOrdersModelList.get(position).getProductName());
        holder.price.setText(myOrdersModelList.get(position).getProductPrice());
        holder.date.setText(myOrdersModelList.get(position).getCurrentDate());
        holder.time.setText(myOrdersModelList.get(position).getCurrentTime());
        holder.quantity.setText(myOrdersModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(myOrdersModelList.get(position).getTotalPrice()));
        holder.deliveryType.setText(myOrdersModelList.get(position).getDeliveryType());


    }

    @Override
    public int getItemCount() {
        return myOrdersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, quantity, totalPrice, deliveryType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            deliveryType = itemView.findViewById(R.id.delivery_type);



        }
    }
}
