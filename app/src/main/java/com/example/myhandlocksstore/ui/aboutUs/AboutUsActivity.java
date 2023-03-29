package com.example.myhandlocksstore.ui.aboutUs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.activities.LoginActivity;
import com.example.myhandlocksstore.activities.MainActivity;
import com.example.myhandlocksstore.models.UserModel;
import com.example.myhandlocksstore.ui.brands.BrandsActivity;
import com.example.myhandlocksstore.ui.category.CategoryActivity;
import com.example.myhandlocksstore.ui.home.HomeActivity;
import com.example.myhandlocksstore.ui.myCarts.MyCartsActivity;
import com.example.myhandlocksstore.ui.myOrders.MyOrdersActivity;
import com.example.myhandlocksstore.ui.newProducts.NewProductsActivity;
import com.example.myhandlocksstore.ui.offers.OffersActivity;
import com.example.myhandlocksstore.ui.profile.ProfileActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUsActivity extends AppCompatActivity {
    ImageView imageMenu;
    ImageView imageCart;
    TextView textTitle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
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
                startActivity(new Intent(AboutUsActivity .this, MyCartsActivity.class));
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
                        startActivity(new Intent(AboutUsActivity.this, HomeActivity.class));

                        break;
                    case
                            R.id.menuAboutUs:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case
                            R.id.menuProfile:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(AboutUsActivity.this, ProfileActivity.class));

                        break;
                    case
                            R.id.menuCategory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(AboutUsActivity.this, CategoryActivity.class));

                        break;
                    case
                        R.id.menuBrands:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(AboutUsActivity.this, BrandsActivity.class));

                        break;
                    case
                            R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(AboutUsActivity.this, OffersActivity.class));

                        break;
                    case
                            R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(AboutUsActivity.this, NewProductsActivity.class));

                        break;
                    case
                            R.id.menuMyOrders:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(AboutUsActivity.this, MyOrdersActivity.class));

                        break;
                    case
                            R.id.menuMyCarts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(AboutUsActivity.this, MyCartsActivity.class));

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
                Glide.with(AboutUsActivity.this).load(userModel.getProfileImg()).into(headerImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void instagram(View view) {
        Uri uri = Uri.parse("https://www.instagram.com/handlocks/");
        Intent instagram = new Intent(Intent.ACTION_VIEW, uri);
        instagram.setPackage("com.instagram.android");
        try {
            startActivity(instagram);
        }
        catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/handlocks/")));
        }
    }

    public void facebook(View view) {
        Uri uri = Uri.parse("https://www.facebook.com/people/HandLocks/100054345528687/");
        Intent facebook = new Intent(Intent.ACTION_VIEW, uri);
        facebook.setPackage("com.facebook.android");
        try {
            startActivity(facebook);
        }
        catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/people/HandLocks/100054345528687/")));
        }
    }
}