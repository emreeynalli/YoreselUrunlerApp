package com.example.yreselrnler;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminProductList extends ArrayAdapter <Products> {

    private Activity context;
    private List<Products> productList;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    public AdminProductList(@NonNull Activity context, List<Products> productList ) {
        super(context, R.layout.admin_list, productList);
        this.context = context;
        this.productList = productList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View gridViewItem = layoutInflater.inflate(R.layout.admin_list,null,true);

        TextView name = gridViewItem.findViewById(R.id.name);
        TextView cost = gridViewItem.findViewById(R.id.cost);
        TextView count = gridViewItem.findViewById(R.id.count);
        ImageView image = gridViewItem.findViewById(R.id.img);

        Products product = productList.get(position);



        String Name = product.getProduct_name();
        String Cost = productList.get(position).getProduct_cost();
        name.setText(Name);
        cost.setText(Cost);
        count.setText(productList.get(position).getProduct_count());

        Uri uri = Uri.parse(product.getProduct_image());
        Picasso.with(context).load(uri).into(image);

        return gridViewItem;
    }
}
