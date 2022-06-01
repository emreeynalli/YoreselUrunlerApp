package com.example.yreselrnler;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

public class UserBasketList extends ArrayAdapter <Basket> {

    private Context context;
    private int resource;
    private List<Basket> basketList;
    int miktar,real_miktar;
    DatabaseReference databaseReference;


    public UserBasketList(@NonNull Context context, int resource,ArrayList<Basket> basketList) {
        super(context,resource, basketList);

        this.context = context;
        this.basketList = basketList;
        this.resource=resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        ImageView image = convertView.findViewById(R.id.img);
        TextView cost = convertView.findViewById(R.id.cost);
        TextView name = convertView.findViewById(R.id.name);
        Button add = convertView.findViewById(R.id.add);
        Button remove = convertView.findViewById(R.id.remove);
        Button removeProduct = convertView.findViewById(R.id.removeProduct);
        TextView p_count = convertView.findViewById(R.id.p_count);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserBasket");


        Uri uri = Uri.parse(getItem(position).getProduct_image());
        Picasso.with(context).load(uri).into(image);

        name.setText(getItem(position).getProduct_name());
        cost.setText(getItem(position).getProduct_cost());

        String c = (String) p_count.getText();
        String real_c = getItem(position).getProduct_count();
        miktar = Integer.parseInt(String.valueOf(c.charAt(0)));
        real_miktar = Integer.parseInt(String.valueOf(real_c.charAt(0)));
        String count = getItem(position).getAppend_count();
        p_count.setText(count+" adet");
        miktar = Integer.parseInt(count);

        if(miktar == real_miktar){
            add.setClickable(false);
            add.setVisibility(View.INVISIBLE);
        }

        /*sharedPreferences = context.getSharedPreferences("amount", Context.MODE_PRIVATE);
        toplamhesap = sharedPreferences.getFloat("key_amount",0);
        String x = String.valueOf(toplamhesap).replace('.',',');

         */



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(miktar < real_miktar) {
                    miktar++;
                }
                if(miktar == real_miktar){
                    add.setClickable(false);
                }
                p_count.setText(miktar+" adet");
                databaseReference.child(getItem(position).getProduct_name()).child("append_count").setValue(""+miktar);
                clear();


            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(miktar > 0)
                    miktar--;
                if(miktar != 0)
                databaseReference.child(getItem(position).getProduct_name()).child("append_count").setValue(""+miktar);
                p_count.setText(miktar+" adet");

                if(miktar == 0){
                    databaseReference.child(getItem(position).getProduct_name()).removeValue();

                }
                clear();
            }


        });

        removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(getItem(position).getProduct_name()).removeValue();
                clear();


            }
        });



        return convertView;
    }
}


