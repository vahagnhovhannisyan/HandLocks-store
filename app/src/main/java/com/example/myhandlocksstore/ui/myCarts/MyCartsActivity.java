package com.example.myhandlocksstore.ui.myCarts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.activities.DetailedActivity;
import com.example.myhandlocksstore.models.UserModel;
import com.example.myhandlocksstore.ui.aboutUs.AboutUsActivity;
import com.example.myhandlocksstore.ui.brands.BrandsActivity;
import com.example.myhandlocksstore.ui.category.CategoryActivity;
import com.example.myhandlocksstore.ui.home.HomeActivity;
import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.activities.PlacedOrderActivity;
import com.example.myhandlocksstore.adapters.MyCartAdapter;
import com.example.myhandlocksstore.models.MyCartModel;
import com.example.myhandlocksstore.ui.myOrders.MyOrdersActivity;
import com.example.myhandlocksstore.ui.newProducts.NewProductsActivity;
import com.example.myhandlocksstore.ui.offers.OffersActivity;
import com.example.myhandlocksstore.ui.profile.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyCartsActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageView imageMenu;
    ImageView imageMenu0;
    TextView textTitle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseDatabase database;

    Button buyNow;
    ConstraintLayout constraint1, constraint2;

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount;
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_carts);
        radioGroup = findViewById(R.id.radioGroup);

        buyNow = findViewById(R.id.buy_now);

        constraint1 = findViewById(R.id.constraint1);
        constraint2 = findViewById(R.id.constraint2);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        overTotalAmount = findViewById(R.id.textViewTotalAmount);

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getApplicationContext(), cartModelList);
        recyclerView.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getEmail()).collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                        String documentId = documentSnapshot.getId();
                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                        cartModel.setDocumentId(documentId);
                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    calculateTotalAmount(cartModelList);
                }



            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectId);
                Intent intent = new Intent(getApplicationContext(), PlacedOrderActivity.class);
                intent.putExtra("deliveryType", radioButton.getText().toString());
                intent.putExtra("itemList", (Serializable) cartModelList);
                startActivity(intent);
                calculateTotalAmount(cartModelList);
                for(int a = 0; a < cartModelList.size(); a++){

                    db.collection("CurrentUser").document(auth.getCurrentUser().getEmail()).collection("AddToCart").document(cartModelList.get(a).getDocumentId())
                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        for(int a = 0; a < cartModelList.size(); a++){
                                        cartModelList.remove(cartModelList.get(a));
                                        }

                                         cartAdapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Սխալ" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }
        });

        imageMenu = findViewById(R.id.imageMenu);
        imageMenu0 = findViewById(R.id.imageMenu0);
        textTitle = findViewById(R.id.textTitle);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        database = FirebaseDatabase.getInstance();

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        imageMenu0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuHome:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyCartsActivity.this, HomeActivity.class));

                        break;
                    case R.id.menuAboutUs:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyCartsActivity.this, AboutUsActivity.class));
                        break;

                    case R.id.menuProfile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyCartsActivity.this, ProfileActivity.class));

                        break;
                    case R.id.menuCategory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyCartsActivity.this, CategoryActivity.class));

                        break;
                    case R.id.menuBrands:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyCartsActivity.this, BrandsActivity.class));

                        break;
                    case R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyCartsActivity.this, OffersActivity.class));

                        break;
                    case R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyCartsActivity.this, NewProductsActivity.class));

                        break;
                    case R.id.menuMyOrders:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyCartsActivity.this, MyOrdersActivity.class));

                        break;
                    case R.id.menuMyCarts:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                }

                return true;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.nav_header_name);
        TextView headerEmail = headerView.findViewById(R.id.nav_header_email);
        CircleImageView headerImage = headerView.findViewById(R.id.nav_header_img);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);

                headerName.setText(userModel.getName());
                headerEmail.setText(userModel.getEmail());
                if (userModel.getProfileImg() != null)
                    Glide.with(MyCartsActivity.this).load(userModel.getProfileImg()).into(headerImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Thread thread = new Thread(){
            @Override
            public void run(){
                while (!isInterrupted()){
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                calculateTotalAmount(cartModelList);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    private void calculateTotalAmount(List<MyCartModel> cartModelList) {

        double totalAmount = 0.0;
        for (MyCartModel myCartModel : cartModelList) {
            totalAmount += myCartModel.getTotalPrice();
        }
        overTotalAmount.setText("Ընդհանուր Գումար։ " + totalAmount + "֏");
        if (totalAmount == 0) {


            constraint2.setVisibility(View.GONE);
            constraint1.setVisibility(View.VISIBLE);

        } else {
            constraint1.setVisibility(View.GONE);
            constraint2.setVisibility(View.VISIBLE);
        }

    }
}