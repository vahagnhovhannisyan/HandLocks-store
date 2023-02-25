package com.example.myhandlocksstore.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myhandlocksstore.activities.LoginActivity;
import com.example.myhandlocksstore.activities.MainActivity;
import com.example.myhandlocksstore.activities.PlacedOrderActivity;
import com.example.myhandlocksstore.activities.RegistrationActivity;
import com.example.myhandlocksstore.models.MyCartModel;
import com.example.myhandlocksstore.models.UpdateUserModel;
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
import com.google.android.gms.internal.firebase_auth.zzff;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.zzy;
import com.google.firebase.auth.zzz;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    CircleImageView profileImg;
    EditText number, address;
    Button update;
    TextView logOut;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseFirestore firestore;

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
        firestore = FirebaseFirestore.getInstance();

        profileImg = findViewById(R.id.profile_img);
        number = findViewById(R.id.profile_number);
        address = findViewById(R.id.profile_address);
        update = findViewById(R.id.update);
        logOut = findViewById(R.id.profile_log_out);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

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

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
                progressBar.setVisibility(View.VISIBLE);
                auth.signOut();
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

                if(userModel.getProfileImg() != null)
                Glide.with(getApplicationContext()).load(userModel.getProfileImg()).into(profileImg);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void updateUserProfile() {

        String userNumber = number.getText().toString();
        String userAddress = address.getText().toString();



        if(TextUtils.isEmpty(userNumber)){
            Toast.makeText(this,"Մուտքագրեք Ձեր Հեռախոսահամարը", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userAddress)){
            Toast.makeText(this,"Մուտքագրեք Ձեր Հասցեն", Toast.LENGTH_SHORT).show();
            return;
        }



        final HashMap<String,Object> cartMap = new HashMap<>();

                cartMap.put("userNumber", userNumber);
                cartMap.put("userAddress", userAddress);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getEmail()).collection("UserNumber").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this,"Հաշիվը Թարմացված Է", Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null ){
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);
            final StorageReference reference = storage.getReference().child("profile_picture").child(FirebaseAuth.getInstance().getUid());
            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"Ներբեռնված է", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

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