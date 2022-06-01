package com.example.yreselrnler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    Button products;
    Button addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Intent i = getIntent();

        products = findViewById(R.id.products);
        addProduct = findViewById(R.id.addProduct);

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, AdminProductsActivity.class);
                startActivity(i);
                Toast.makeText(AdminActivity.this, "Ürünler sayfasına yönlendiriliyorsunuz", Toast.LENGTH_SHORT).show();
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, AdminAddProductActivity.class);
                startActivity(i);
                Toast.makeText(AdminActivity.this, "Ürün Ekleme sayfasına yönlendiriliyorsunuz", Toast.LENGTH_SHORT).show();
            }
        });



    }
}