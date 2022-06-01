package com.example.yreselrnler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserBasketActivity extends AppCompatActivity {

    String mail;
    DatabaseReference databaseReference,databaseReference2;
    ListView list;
    ArrayList<Basket> basketList;
    TextView amount;
    float toplamhesap;
    float tutulanhesap;
    Button completeShopping;
    TextView userBasket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userbasket);
        userBasket = findViewById(R.id.userBasket);

        Intent i = getIntent();
        mail = i.getStringExtra("email");
        userBasket.setText(mail+"/Sepetim");



        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserBasket");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("UserProducts");
        list = findViewById(R.id.list);
        amount = findViewById(R.id.amount);
        completeShopping = findViewById(R.id.completeShopping);
        basketList = new ArrayList<>();
        retrieveDatas();

        completeShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPayment(tutulanhesap);
            }
        });



    }


    //bu kısma bi daha bak
        public void retrieveDatas() {

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot basketSnapshot : snapshot.getChildren()) {

                        Basket basket = basketSnapshot.getValue(Basket.class);
                        basketList.add(basket);
                        String h = basket.getProduct_cost() ;               //Ürünün maaliyetini alma
                        String he = h.replace(',' , '.');    //double hesap yapılacağı için virgülü noktaya çevirme
                        String hee = he.replace(" TL","");  //double hesap yapılacağı için TL yazısını silme
                        float hesap = Float.parseFloat(hee) * Integer.parseInt(basket.append_count);
                        toplamhesap += hesap;



                    }
                    UserBasketList adapter = new UserBasketList(UserBasketActivity.this,R.layout.userbasket_list,basketList);
                    list.setAdapter(adapter);
                    amount.setText("Alışverişiniz Tutarı: "+toplamhesap+" TL");
                    tutulanhesap = toplamhesap;
                    toplamhesap = 0;





                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





        }
        public void showPayment(float amount){

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.payment,null);
            EditText address = view.findViewById(R.id.address);
            EditText accountNumber = view.findViewById(R.id.accountNumber);
            TextView count = view.findViewById(R.id.count);
            Button completePayment = view.findViewById(R.id.completepayment);

            count.setText("Ödenecek Tutar : "+amount);

            alert.setView(view);


            AlertDialog dialog = alert.create();
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.show();

            completePayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UserBasketActivity.this, "Ödemeniz Tamamlandı", Toast.LENGTH_SHORT).show();
                    for(int i=0; i<basketList.size(); i++){
                        int count = Integer.parseInt(basketList.get(i).getAppend_count());
                        String c = basketList.get(i).getProduct_count();
                        String cc = c.replace(" Adet","");
                        int rcount = Integer.parseInt(cc);
                        databaseReference2.child(basketList.get(i).getProduct_name()).child("product_count").setValue(String.valueOf(rcount - count)+" Adet");
                    }
                    databaseReference.removeValue();
                    basketList.clear();
                    dialog.dismiss();
                }
            });



        }

}
