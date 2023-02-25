package com.example.myhandlocksstore.ui.brands;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.adapters.BrandsAdapter;
import com.example.myhandlocksstore.adapters.NavCategoryAdapter;
import com.example.myhandlocksstore.models.BrandsModel;
import com.example.myhandlocksstore.models.NavCategoryModel;
import com.example.myhandlocksstore.models.UserModel;
import com.example.myhandlocksstore.ui.aboutUs.AboutUsActivity;
import com.example.myhandlocksstore.ui.category.CategoryActivity;
import com.example.myhandlocksstore.ui.home.HomeActivity;
import com.example.myhandlocksstore.ui.myCarts.MyCartsActivity;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BrandsActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    List<BrandsModel> brandsModelList;
    BrandsAdapter brandsAdapter;

    ImageView imageMenu;
    TextView textTitle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.brand_rec);
        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false));
        brandsModelList = new ArrayList<>();
        brandsAdapter = new BrandsAdapter(getApplicationContext(),brandsModelList);
        recyclerView.setAdapter(brandsAdapter);

        db.collection("Brands")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BrandsModel brandsModel = document.toObject(BrandsModel.class);
                                brandsModelList.add(brandsModel);
                                brandsAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                        }
                    }
                });

        imageMenu = findViewById(R.id.imageMenu);
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

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case
                            R.id.menuHome:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(BrandsActivity.this, HomeActivity.class));

                        break;
                    case
                            R.id.menuAboutUs:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(BrandsActivity.this, AboutUsActivity.class));
                        break;

                    case
                            R.id.menuProfile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(BrandsActivity.this, ProfileActivity.class));

                        break;
                    case
                            R.id.menuCategory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(BrandsActivity.this, CategoryActivity.class));

                        break;
                    case
                            R.id.menuBrands:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case
                            R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(BrandsActivity.this, OffersActivity.class));

                        break;
                    case
                            R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(BrandsActivity.this, NewProductsActivity.class));

                        break;
                    case
                            R.id.menuMyOrders:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(BrandsActivity.this, MyOrdersActivity.class));

                        break;
                    case
                            R.id.menuMyCarts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(BrandsActivity.this, MyCartsActivity.class));

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
                Glide.with(BrandsActivity.this).load(userModel.getProfileImg()).into(headerImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}