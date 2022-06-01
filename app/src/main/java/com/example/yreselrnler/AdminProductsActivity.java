package com.example.yreselrnler;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminProductsActivity extends AppCompatActivity {


    GridView gridView;
    List<Products> productList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminproducts);
        Intent intent = getIntent();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserProducts");
        gridView = findViewById(R.id.grid);
        productList = new ArrayList<>();
        retrieveDatas();


    }


    public void retrieveDatas() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {

                    Products product = productSnapshot.getValue(Products.class);
                    productList.add(product);


                }
                AdminProductList adapter = new AdminProductList(AdminProductsActivity.this, productList);
                gridView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}

