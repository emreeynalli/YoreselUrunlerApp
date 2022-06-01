package com.example.yreselrnler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UserProductsActivity extends AppCompatActivity {
    String mail;
    GridView gridView;
    List<Products> productList;
    DatabaseReference databaseReference;
    TextView userProducts;
    Button goBasket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userproducts);

        Intent i = getIntent();
        mail = i.getStringExtra("email");

        databaseReference = FirebaseDatabase.getInstance().getReference();
        gridView = findViewById(R.id.grid);
        goBasket = findViewById(R.id.goBasket);
        userProducts = findViewById(R.id.userProducts);
        userProducts.setText(mail+"/Ürünler");
        productList = new ArrayList<>();
        retrieveDatas();



        goBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProductsActivity.this,UserBasketActivity.class);
                i.putExtra("email",mail);
                startActivity(i);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        productList.clear();
        retrieveDatas();
    }

    public void retrieveDatas() {

            databaseReference.child("UserProducts").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {

                        Products product = productSnapshot.getValue(Products.class);
                        productList.add(product);

                    }
                    UserProductList adapter = new UserProductList(UserProductsActivity.this, productList);
                    gridView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


    }
