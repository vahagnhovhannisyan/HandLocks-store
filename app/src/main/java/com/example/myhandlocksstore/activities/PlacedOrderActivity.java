package com.example.myhandlocksstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.models.UserModel;
import com.example.myhandlocksstore.ui.home.HomeActivity;
import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.models.MyCartModel;
import com.example.myhandlocksstore.ui.myCarts.MyCartsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacedOrderActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseDatabase database;

    ImageView home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacedOrderActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        List<MyCartModel> list = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");
        String deliveryType = getIntent().getStringExtra("deliveryType");

        if(list != null && list.size() > 0){
            for(MyCartModel model : list){
                final HashMap<String,Object> cartMap = new HashMap<>();

                cartMap.put("productName", model.getProductName());
                cartMap.put("productPrice", model.getProductPrice());
                cartMap.put("currentDate", model.getCurrentDate());
                cartMap.put("currentTime", model.getCurrentTime());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalPrice", model.getTotalPrice());
                cartMap.put("deliveryType", deliveryType);

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getEmail()).collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(PlacedOrderActivity.this, "Ձեր Գնումը Կատարված Է", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }
    }
}