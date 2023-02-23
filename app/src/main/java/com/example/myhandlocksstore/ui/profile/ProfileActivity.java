package com.example.myhandlocksstore.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.ui.aboutUs.AboutUsActivity;
import com.example.myhandlocksstore.ui.brands.BrandsActivity;
import com.example.myhandlocksstore.ui.category.CategoryActivity;
import com.example.myhandlocksstore.ui.home.HomeActivity;
import com.example.myhandlocksstore.R;
import com.example.myhandlocksstore.models.UserModel;
import com.example.myhandlocksstore.ui.myCarts.MyCartsActivity;
import com.example.myhandlocksstore.ui.myOrders.MyOrdersActivity;
import com.example.myhandlocksstore.ui.newProducts.NewProductsActivity;
import com.example.myhandlocksstore.ui.offers.OffersActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    CircleImageView profileImg;
    EditText name, email, number, address;
    Button update;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    ProgressBar progressBar;

    ImageView imageMenu;
    TextView textTitle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        profileImg = findViewById(R.id.profile_img);
        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        number = findViewById(R.id.profile_number);
        address = findViewById(R.id.profile_address);
        update = findViewById(R.id.update);

        imageMenu = findViewById(R.id.imageMenu);
        textTitle = findViewById(R.id.textTitle);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

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
                        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));

                        break;
                    case
                            R.id.menuAboutUs:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(ProfileActivity.this, AboutUsActivity.class));
                        break;

                    case
                            R.id.menuProfile:
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case
                            R.id.menuCategory:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(ProfileActivity.this, CategoryActivity.class));

                        break;
                    case
                            R.id.menuBrands:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(ProfileActivity.this, BrandsActivity.class));

                        break;
                    case
                            R.id.menuOffers:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(ProfileActivity.this, OffersActivity.class));

                        break;
                    case
                            R.id.menuNewProducts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(ProfileActivity.this, NewProductsActivity.class));

                        break;
                    case
                            R.id.menuMyOrders:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(ProfileActivity.this, MyOrdersActivity.class));

                        break;
                    case
                            R.id.menuMyCarts:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        finish();
                        startActivity(new Intent(ProfileActivity.this, MyCartsActivity.class));

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
                Glide.with(ProfileActivity.this).load(userModel.getProfileImg()).into(headerImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);

                Glide.with(getApplicationContext()).load(userModel.getProfileImg()).into(profileImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

    }

    private void updateUserProfile() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null){
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);
            final StorageReference reference = storage.getReference().child("profile_picture").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"Ներբեռնված է", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profileImg").setValue(uri.toString());
                            Toast.makeText(getApplicationContext(),"Անձնական Նկարը Ներբեռնված Է", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }

    }
}