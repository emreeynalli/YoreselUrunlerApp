package com.example.yreselrnler;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserProductList extends ArrayAdapter <Products> {

    private Activity context;
    private List<Products> productList;
    DatabaseReference mDatabase;


    public UserProductList(@NonNull Activity context, List<Products> productList) {
        super(context, R.layout.user_list, productList);
        this.context = context;
        this.productList = productList;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View gridViewItem = layoutInflater.inflate(R.layout.user_list, null, true);

        TextView name = gridViewItem.findViewById(R.id.name);
        TextView cost = gridViewItem.findViewById(R.id.cost);
        TextView count = gridViewItem.findViewById(R.id.count);
        ImageView image = gridViewItem.findViewById(R.id.img);
        Button addBasket = gridViewItem.findViewById(R.id.addBasket);
        RelativeLayout relative = gridViewItem.findViewById(R.id.relative);


        Products product = productList.get(position);


        String Name = product.getProduct_name();
        String Cost = productList.get(position).getProduct_cost();
        name.setText(Name);
        cost.setText(Cost);
        count.setText(productList.get(position).getProduct_count());

        Uri uri = Uri.parse(product.getProduct_image());
        Picasso.with(context).load(uri).into(image);

        String c = productList.get(position).getProduct_count();
        String cc = c.replace(" Adet","");
        int ccc = Integer.parseInt(cc);
        if(ccc == 0){
            addBasket.setVisibility(View.INVISIBLE);
            count.setText("Stokta Kalmadı");

        }

        addBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(relative, "", Snackbar.LENGTH_LONG);
                View custom = layoutInflater.inflate(R.layout.snackbar_custom, null);
                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                snackbarLayout.setPadding(0, 0, 0, 0);
                TextView basketText = custom.findViewById(R.id.basketText);


                basketText.setText(product.getProduct_name() + " ürünü başarıyla sepete eklendi");


                snackbarLayout.addView(custom, 0);
                snackbar.show();



                mDatabase = FirebaseDatabase.getInstance().getReference().child("UserBasket");

                mDatabase.child(Name).child("product_image").setValue(product.getProduct_image());
                mDatabase.child(Name).child("product_name").setValue(product.getProduct_name());
                mDatabase.child(Name).child("product_cost").setValue(product.getProduct_cost());
                mDatabase.child(Name).child("product_count").setValue(product.getProduct_count());
                mDatabase.child(Name).child("append_count").setValue("1");




            }
        });

        return gridViewItem;
    }
}


