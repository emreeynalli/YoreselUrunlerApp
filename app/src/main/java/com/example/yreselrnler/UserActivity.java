package com.example.yreselrnler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    Button basket;
    Button products;
    String mail;
    String user;
    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        basket = findViewById(R.id.basket);
        products = findViewById(R.id.products);
        welcome = findViewById( R.id.welcome);
        Intent i = getIntent();
        mail = i.getStringExtra("email");

        user = welcome.getText()+mail;
        welcome.setText(user);


        basket = findViewById(R.id.basket);
        products = findViewById(R.id.products);

        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserActivity.this,UserBasketActivity.class);
                i.putExtra("email",mail);
                startActivity(i);
            }
        });

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserActivity.this,UserProductsActivity.class);
                i.putExtra("email",mail);
                startActivity(i);
            }
        });

        
    }
}