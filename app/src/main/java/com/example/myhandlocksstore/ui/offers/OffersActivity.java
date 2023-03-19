package com.example.myhandlocksstore.ui.offers;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.adapters.OffersBrnAdapter;
import com.example.myhandlocksstore.adapters.OffersCxnAdapter;
import com.example.myhandlocksstore.adapters.OffersKoxAdapter;
import com.example.myhandlocksstore.adapters.OffersMijAdapter;
import com.example.myhandlocksstore.adapters.OffersMxlAdapter;
import com.example.myhandlocksstore.adapters.OffersPakAdapter;
import com.example.myhandlocksstore.adapters.OffersPakMexAdapter;
import com.example.myhandlocksstore.adapters.PopularAdapters;
import com.example.myhandlocksstore.models.OffersModel;
import com.example.myhandlocksstore.models.PopularModel;
import com.example.myhandlocksstore.models.UserModel;
import com.example.myhandlocksstore.ui.aboutUs.AboutUsActivity;
import com.example.myhandlocksstore.ui.brands.BrandsActivity;
import com.example.myhandlocksstore.ui.category.CategoryActivity;
import com.example.myhandlocksstore.ui.home.HomeActivity;
import com.example.myhandlocksstore.ui.myCarts.MyCartsActivity;
import com.example.myhandlocksstore.ui.myOrders.MyOrdersActivity;
import com.example.myhandlocksstore.ui.newProducts.NewProductsActivity;
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

public class OffersActivity extends AppCompatActivity {

    ScrollView scrollView;
    ProgressBar progressBar;

    ImageView imageMenu;
    ImageView imageCart;
    TextView textTitle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseDatabase database;

    RecyclerView brnRec, pakRec, koxRec, mxlRec, cxnRec, mijRec, pakMexRec;
    FirebaseFirestore db;

    List<OffersModel> offersModelList;
    OffersBrnAdapter offersBrnAdapter;

    List<OffersModel> offersPakModelList;
    OffersPakAdapter offersPakAdapter;

    List<OffersModel> offersKoxModelList;
    OffersKoxAdapter offersKoxAdapter;

    List<OffersModel> offersMxlModelList;
    OffersMxlAdapter offersMxlAdapter;

    List<OffersModel> offersCxnModelList;
    OffersCxnAdapter offersCxnAdapter;

    List<OffersModel> offersMijModelList;
    OffersMijAdapter offersMijAdapter;

    List<OffersModel> offersPakMexModelList;
    OffersPakMexAdapter offersPakMexAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        imageCart = findViewById(R.id.imageCart);
        imageMenu = findViewById(R.id.imageMenu);
        textTitle = findViewById(R.id.textTitle);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        database = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();

        scrollView = findViewById(R.id.scroll_view);
        progressBar = findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        brnRec = findViewById(R.id.brnRec);
        pakRec = findViewById(R.id.pakRec);
        koxRec = findViewById(R.id.koxRec);
        mxlRec = findViewById(R.id.mxlRec);
        cxnRec = findViewById(R.id.cxnRec);
        mijRec = findViewById(R.id.mijRec);
        pakMexRec = findViewById(R.id.pakMexRec);



        brnRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersModelList = new ArrayList<>();
        offersBrnAdapter = new OffersBrnAdapter(getApplicationContext(),offersModelList);
        brnRec.setAdapter(offersBrnAdapter);

        db.collection("OffersBrn")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OffersModel offersModel = document.toObject(OffersModel.class);
                                offersModelList.add(offersModel);
                                offersBrnAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        pakRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersPakModelList = new ArrayList<>();
        offersPakAdapter = new OffersPakAdapter(getApplicationContext(),offersPakModelList);
        pakRec.setAdapter(offersPakAdapter);

        db.collection("OffersPak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OffersModel offersModel = document.toObject(OffersModel.class);
                                offersPakModelList.add(offersModel);
                                offersPakAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        koxRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersKoxModelList = new ArrayList<>();
        offersKoxAdapter = new OffersKoxAdapter(getApplicationContext(),offersKoxModelList);
        koxRec.setAdapter(offersKoxAdapter);

        db.collection("OffersKox")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OffersModel offersModel = document.toObject(OffersModel.class);
                                offersKoxModelList.add(offersModel);
                                offersKoxAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        mxlRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersMxlModelList = new ArrayList<>();
        offersMxlAdapter = new OffersMxlAdapter(getApplicationContext(),offersMxlModelList);
        mxlRec.setAdapter(offersMxlAdapter);

        db.collection("OffersMxl")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OffersModel offersModel = document.toObject(OffersModel.class);
                                offersMxlModelList.add(offersModel);
                                offersMxlAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        cxnRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersCxnModelList = new ArrayList<>();
        offersCxnAdapter = new OffersCxnAdapter(getApplicationContext(),offersCxnModelList);
        cxnRec.setAdapter(offersCxnAdapter);

        db.collection("OffersCxn")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OffersModel offersModel = document.toObject(OffersModel.class);
                                offersCxnModelList.add(offersModel);
                                offersCxnAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        mijRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersMijModelList = new ArrayList<>();
        offersMijAdapter = new OffersMijAdapter(getApplicationContext(),offersMijModelList);
        mijRec.setAdapter(offersMijAdapter);

        db.collection("OffersMij")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OffersModel offersModel = document.toObject(OffersModel.class);
                                offersMijModelList.add(offersModel);
                                offersMijAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        pakMexRec.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL,false));
        offersPakMexModelList = new ArrayList<>();
        offersPakMexAdapter = new OffersPakMexAdapter(getApplicationContext(),offersPakMexModelList);
        pakMexRec.setAdapter(offersPakMexAdapter);

        db.collection("OffersPak")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task .getResult()) {
                                OffersModel offersModel = document.toObject(OffersModel.class);
                                offersPakMexModelList.add(offersModel);
                                offersPakMexAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Սխալ։" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        imageCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(OffersActivity.this, MyCartsActivity.class));
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
                        startActivity(new Intent(OffersActivity.this, HomeActivity.class));

                        break;
                    case
                            R.id.menuAboutUs:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, AboutUsActivity.class));
                        break;

                    case
                            R.id.menuProfile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, ProfileActivity.class));

                        break;
                    case
                            R.id.menuCategory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, CategoryActivity.class));

                        break;
                    case
                            R.id.menuBrands:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, BrandsActivity.class));

                        break;
                    case
                            R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case
                            R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, NewProductsActivity.class));

                        break;
                    case
                            R.id.menuMyOrders:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, MyOrdersActivity.class));

                        break;
                    case
                            R.id.menuMyCarts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(OffersActivity.this, MyCartsActivity.class));

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
                Glide.with(OffersActivity.this).load(userModel.getProfileImg()).into(headerImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
