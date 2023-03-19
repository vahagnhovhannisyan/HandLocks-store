package com.example.myhandlocksstore.ui.myOrders;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.adapters.MyCartAdapter;
import com.example.myhandlocksstore.adapters.MyOrdersAdapter;
import com.example.myhandlocksstore.models.MyCartModel;
import com.example.myhandlocksstore.models.MyOrdersModel;
import com.example.myhandlocksstore.models.UserModel;
import com.example.myhandlocksstore.ui.aboutUs.AboutUsActivity;
import com.example.myhandlocksstore.ui.brands.BrandsActivity;
import com.example.myhandlocksstore.ui.category.CategoryActivity;
import com.example.myhandlocksstore.ui.home.HomeActivity;
import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.ui.myCarts.MyCartsActivity;
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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyOrdersActivity extends AppCompatActivity {
    ImageView imageCart;
    ImageView imageMenu;
    TextView textTitle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseDatabase database;

    ConstraintLayout constraint1, constraint2;

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalMoney;
    RecyclerView recyclerView;
    MyOrdersAdapter myOrdersAdapter;
    List<MyOrdersModel> myOrdersModelList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        constraint1 = findViewById(R.id.constraint1);
        constraint2 = findViewById(R.id.constraint2);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        overTotalMoney = findViewById(R.id.textViewTotalMoney);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        myOrdersModelList = new ArrayList<>();
        myOrdersAdapter = new MyOrdersAdapter(getApplicationContext(), myOrdersModelList);
        recyclerView.setAdapter(myOrdersAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getEmail()).collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        String documentId = documentSnapshot.getId();
                        MyOrdersModel myOrdersModel = documentSnapshot.toObject(MyOrdersModel.class);
                        myOrdersModel.setDocumentId(documentId);
                        myOrdersModelList.add(myOrdersModel);
                        myOrdersAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    calculateTotalMoney(myOrdersModelList);
                }


            }
        });
    }

        private void calculateTotalMoney(List<MyOrdersModel> myOrdersModelList) {

            double totalMoney = 0.0;
            for(MyOrdersModel myOrdersModel : myOrdersModelList){
                totalMoney += myOrdersModel.getTotalPrice();
            }
            overTotalMoney.setText("Ընդհանուր Առևտուր։ "+ totalMoney + "֏");
            if(totalMoney == 0){


                constraint2.setVisibility(View.GONE);
                constraint1.setVisibility(View.VISIBLE);

            }
            else{
                constraint1.setVisibility(View.GONE);
                constraint2.setVisibility(View.VISIBLE);
            }


        imageCart = findViewById(R.id.imageCart);
        imageMenu = findViewById(R.id.imageMenu);
        textTitle = findViewById(R.id.textTitle);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        database = FirebaseDatabase.getInstance();

            imageCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(new Intent(MyOrdersActivity.this, MyCartsActivity.class));
                }
            });

        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case
                            R.id.menuHome:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyOrdersActivity.this, HomeActivity.class));

                        break;
                    case
                            R.id.menuAboutUs:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyOrdersActivity.this, AboutUsActivity.class));
                        break;

                    case
                            R.id.menuProfile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyOrdersActivity.this, ProfileActivity.class));

                        break;
                    case
                            R.id.menuCategory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyOrdersActivity.this, CategoryActivity.class));

                        break;
                    case
                            R.id.menuBrands:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyOrdersActivity.this, BrandsActivity.class));

                        break;
                    case
                            R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyOrdersActivity.this, OffersActivity.class));

                        break;
                    case
                            R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyOrdersActivity.this, NewProductsActivity.class));

                        break;
                    case
                            R.id.menuMyOrders:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case
                            R.id.menuMyCarts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(MyOrdersActivity.this, MyCartsActivity.class));

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
                if(userModel.getProfileImg() != null)
                Glide.with(MyOrdersActivity.this).load(userModel.getProfileImg()).into(headerImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}